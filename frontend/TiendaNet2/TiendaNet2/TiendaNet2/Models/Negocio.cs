using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class Negocio
    {
        public int Id { get; set; }
        [Required(ErrorMessage = "El campo {0} es requerido")]
        [StringLength(maximumLength: 100, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        public string Nombre { get; set; }
        [StringLength(maximumLength: 255, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        public string Descripcion { get; set; }
        public string ImagenURL { get; set; }
        public string Link { get; set; }
    }
}