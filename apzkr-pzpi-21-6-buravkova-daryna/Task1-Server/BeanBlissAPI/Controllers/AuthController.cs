using BeanBlissAPI.Data;
using BeanBlissAPI.DTO;
using BeanBlissAPI.Interfaces;
using BeanBlissAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;

namespace BeanBlissAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly DataContext _context;
        private readonly AppSettings _appSettings;
        private readonly IUserRepository _userRepository;
        private readonly ITechnicianRepository _technicianRepository;

        public AuthController(DataContext context, IOptions<AppSettings> appSettings, 
            IUserRepository userRepository, ITechnicianRepository technicianRepository)
        {
            _context = context;
            _appSettings = appSettings.Value;
            _userRepository = userRepository;
            _technicianRepository = technicianRepository;
        }

        [HttpPost("Login")]
        [ProducesResponseType(204)]
        [ProducesResponseType(400)]
        public IActionResult Login([FromBody] LoginDto model)
        {
            var user = _userRepository.GetUsers().FirstOrDefault(x => x.Email == model.Email);
            var technician = _technicianRepository.GetTechnicians().FirstOrDefault(x => x.Email == model.Email);

            if (user == null && technician == null)
            {
                return BadRequest("Invalid email or password");
            }

            dynamic entity = user ?? (dynamic)technician;
            string role = user != null ? user.Role : "Technician";

            var match = CheckPassword(model.Password, entity);

            if (!match)
            {
                return BadRequest("Invalid email or password");
            }

            // Generate JWT token
            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.ASCII.GetBytes(_appSettings.Secret);
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new[]
                {
                    new Claim("id", entity.Id.ToString()),
                    new Claim("email", entity.Email),
                    new Claim(ClaimTypes.Role, role)
                }),
                Expires = DateTime.UtcNow.AddDays(7),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha512Signature)
            };
            var token = tokenHandler.CreateToken(tokenDescriptor);
            var encryptedToken = tokenHandler.WriteToken(token);

            return Ok(new { token = encryptedToken, userId = entity.Id, username = entity.Email, role });
        }

        private bool CheckPassword(string password, dynamic entity)
        {
            using (var hmac = new System.Security.Cryptography.HMACSHA512(entity.PasswordSalt))
            {
                var computedHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
                for (int i = 0; i < computedHash.Length; i++)
                {
                    if (computedHash[i] != entity.PasswordHash[i]) return false;
                }
            }
            return true;
        }

        [HttpPost("Register")]
        [ProducesResponseType(204)]
        [ProducesResponseType(400)]
        public IActionResult Register([FromBody] RegistrationDto model)
        {
            var existingUser = _context.User.FirstOrDefault(u => u.Email == model.Email);
            if (existingUser != null)
            {
                return BadRequest("Email is already registered");
            }

            var user = new User { Email = model.Email, Role = "User" };

            if (model.ConfirmPassword == model.Password)
            {
            using (HMACSHA512 hmac = new HMACSHA512())
            {
                user.PasswordSalt = hmac.Key;
                user.PasswordHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(model.Password));
            }
            }
            else
            {
                return BadRequest("Passwords do not match");
            }

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            if (!_userRepository.CreateUser(user))
            {
                ModelState.AddModelError("", "Something went wrong while savin");
                return StatusCode(500, ModelState);
            }

            return Ok("User registered successfully");            
        }

        [HttpPost("RegisterTechnician")]
        [ProducesResponseType(204)]
        [ProducesResponseType(400)]
        public IActionResult RegisterTechnician([FromBody] RegisterTechnicianDto model)
        {
            var existingUser = _context.Technician.FirstOrDefault(u => u.Email == model.Email);
            if (existingUser != null)
            {
                return BadRequest("Email is already registered");
            }

            var user = new Technician { Email = model.Email };

            if (model.ConfirmPassword == model.Password)
            {
            using (HMACSHA512 hmac = new HMACSHA512())
            {
                user.PasswordSalt = hmac.Key;
                user.PasswordHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(model.Password));
            }
            }
            else
            {
                return BadRequest("Passwords do not match");
            }
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            if (!_technicianRepository.CreateTechnician(user))
            {
                ModelState.AddModelError("", "Something went wrong while savin");
                return StatusCode(500, ModelState);
            }

            return Ok("Tech registered successfully");
        }

        [HttpPost("RegisterAdmin")]
        [ProducesResponseType(204)]
        [ProducesResponseType(400)]
        public IActionResult RegisterManager([FromBody] RegistrationDto model)
        {
            var existingUser = _context.User.FirstOrDefault(u => u.Email == model.Email);
            if (existingUser != null)
            {
                return BadRequest("Email is already registered");
            }

            var user = new User { Email = model.Email, Role = "Admin" };

            if (model.ConfirmPassword == model.Password)
            {
                using (HMACSHA512 hmac = new HMACSHA512())
                {
                    user.PasswordSalt = hmac.Key;
                    user.PasswordHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(model.Password));
                }
            }
            else
            {
                return BadRequest("Passwords do not match");
            }

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            if (!_userRepository.CreateUser(user))
            {
                ModelState.AddModelError("", "Something went wrong while savin");
                return StatusCode(500, ModelState);
            }

            return Ok("Admin registered successfully");
        }
    }
}
