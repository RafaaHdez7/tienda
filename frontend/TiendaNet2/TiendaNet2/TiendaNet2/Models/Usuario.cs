using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class Usuario
    {
        public int Id { get; set; }
        public string Nombre { get; set; }
        public string Rol { get; set; }
        public string Contrasena { get; set; }
    }
}