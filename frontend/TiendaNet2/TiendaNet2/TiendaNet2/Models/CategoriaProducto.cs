using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class CategoriaProducto
    {
        public int Id { get; set; }
        [Required(ErrorMessage = "El campo {0} es requerido")]
        [StringLength(maximumLength: 50, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        public string NombreCategoria { get; set; }
        [StringLength(maximumLength: 255, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        public string Descripcion { get; set; }
    }
}