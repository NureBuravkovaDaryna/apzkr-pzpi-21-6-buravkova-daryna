using BeanBlissAPI.Data;
using BeanBlissAPI.Interfaces;
using BeanBlissAPI.Models;
using Microsoft.EntityFrameworkCore;

namespace BeanBlissAPI.Repository
{
    public class TechnicianRepository : ITechnicianRepository
    {
        private readonly DataContext _context;

        public TechnicianRepository(DataContext context)
        {
            _context = context;
        }
        public bool DeleteTechnician(Technician Technician)
        {
            _context.Remove(Technician);
            return Save();
        }

        public bool TechnicianExists(int TechnicianId)
        {
            return _context.Technician.Any(o => o.Id == TechnicianId);
        }

        public ICollection<Machine> GetMachinesByTechnician(int ownerId)
        {
            return _context.Machine.Include(m => m.Region).Where(p => p.Technician.Id == ownerId).ToList();
        }

        public Technician GetTechnician(int TechnicianId)
        {
            return _context.Technician.Where(o => o.Id == TechnicianId).FirstOrDefault();
        }

        public ICollection<Technician> GetTechnicians()
        {
            return _context.Technician.ToList();
        }

        public bool Save()
        {
            var saved = _context.SaveChanges();
            return saved > 0 ? true : false;
        }

        public bool UpdateTechnician(Technician Technician)
        {
            _context.Update(Technician);
            return Save();
        }

        public bool CreateTechnician(Technician tech)
        {
            _context.Technician.Add(tech);
            return Save();
        }

        public Technician GetTechnicianByMachineId(int machineId)
        {
            return _context.Machine.Include(m => m.Technician)
            .Where(m => m.Id == machineId).Select(m => m.Technician)
            .FirstOrDefault();
        }

        public bool ChangeMachineTechnician(int machineId, int newTechnicianId)
        {
            var machine = _context.Machine.Include(m => m.Technician).FirstOrDefault(m => m.Id == machineId);
            if (machine == null)
                return false;

            var newTechnician = _context.Technician.FirstOrDefault(t => t.Id == newTechnicianId);
            if (newTechnician == null)
                return false;

            machine.Technician = newTechnician;
            return Save();
        }
    }
}
