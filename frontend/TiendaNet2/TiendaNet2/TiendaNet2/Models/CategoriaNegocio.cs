using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class CategoriaNegocio
    {
        public int Id { get; set; }
        [Required(ErrorMessage = "El campo {0} es requerido")]
        [StringLength(maximumLength: 50, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        public string Nombre_categoria { get; set; }
        [StringLength(maximumLength: 255, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        public string Descripcion { get; set; }
    }
}