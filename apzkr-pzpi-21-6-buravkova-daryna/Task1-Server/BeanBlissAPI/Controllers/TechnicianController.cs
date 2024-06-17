using AutoMapper;
using BeanBlissAPI.DTO;
using BeanBlissAPI.Interfaces;
using BeanBlissAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace BeanBlissAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TechnicianController : ControllerBase
    {
        private readonly ITechnicianRepository _technicianRepository;
        private readonly IMapper _mapper;

        public TechnicianController(ITechnicianRepository technicianRepository,
            IMapper mapper)
        {
            _technicianRepository = technicianRepository;
            _mapper = mapper;
        }

        [HttpGet]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Technician>))]
        public IActionResult GetTechnicians()
        {
            var Technicians = _mapper.Map<List<TechnicianDto>>(_technicianRepository.GetTechnicians());

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(Technicians);
        }

        [HttpGet("{id}")]
        [ProducesResponseType(200, Type = typeof(Technician))]
        [ProducesResponseType(400)]
        public IActionResult GetTechnician(int id)
        {
            if (!_technicianRepository.TechnicianExists(id))
                return NotFound();

            var Technician = _mapper.Map<TechnicianDto>(_technicianRepository.GetTechnician(id));

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(Technician);
        }

        [HttpGet("machines")]
        [ProducesResponseType(200, Type = typeof(IEnumerable<Machine>))]
        [ProducesResponseType(400)]
        public IActionResult GetMachinesForTechnician([FromQuery] int technicianId)
        {
            if (!_technicianRepository.TechnicianExists(technicianId))
                return NotFound();

            var machines = _mapper.Map<List<MachineDto>>(_technicianRepository.GetMachinesByTechnician(technicianId));

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            return Ok(machines);
        }

        [HttpPut("{id}")]
        [ProducesResponseType(400)]
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        public IActionResult UpdateTechnician(int id, [FromBody] TechnicianDto updatedTechnician)
        {
            if (updatedTechnician == null)
                return BadRequest(ModelState);

            if (id != updatedTechnician.Id)
                return BadRequest(ModelState);

            if (!_technicianRepository.TechnicianExists(id))
                return NotFound();

            if (!ModelState.IsValid)
                return BadRequest();

            var existingTechnician = _technicianRepository.GetTechnician(id);

            if (existingTechnician == null)
                return NotFound();

            existingTechnician.FirstName = updatedTechnician.FirstName ?? existingTechnician.FirstName;
            existingTechnician.LastName = updatedTechnician.LastName ?? existingTechnician.LastName;
            existingTechnician.Phone = updatedTechnician.Phone ?? existingTechnician.Phone;

            if (!_technicianRepository.UpdateTechnician(existingTechnician))
            {
                ModelState.AddModelError("", "Something went wrong updating owner");
                return StatusCode(500, ModelState);
            }

            return Ok("Changed");
        }

        [HttpPut("ChangeTechnician")]
        [ProducesResponseType(400)]
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        public IActionResult ChangeMachineTechnician([FromQuery] int machineId, [FromQuery] int newTechnicianId)
        {
            if (!_technicianRepository.TechnicianExists(newTechnicianId))
                return NotFound("Technician not found");

            if (!_technicianRepository.ChangeMachineTechnician(machineId, newTechnicianId))
            {
                ModelState.AddModelError("", "Something went wrong changing the technician");
                return StatusCode(500, ModelState);
            }

            return Ok("Changed");
        }

        [HttpDelete("{id}")]
        [ProducesResponseType(400)]
        [ProducesResponseType(204)]
        [ProducesResponseType(404)]
        public IActionResult DeleteTechnician(int id)
        {
            if (!_technicianRepository.TechnicianExists(id))
            {
                return NotFound();
            }

            var TechnicianToDelete = _technicianRepository.GetTechnician(id);

            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            if (!_technicianRepository.DeleteTechnician(TechnicianToDelete))
            {
                ModelState.AddModelError("", "Something went wrong deleting owner");
            }

            return Ok("Deleted");
        }
    }
}
