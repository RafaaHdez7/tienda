using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class Producto
    {
        public int Id { get; set; }
        [Required(ErrorMessage = "El campo {0} es requerido")]
        [StringLength(maximumLength: 100, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        public string Nombre_Producto { get; set; }
        [StringLength(maximumLength: 255, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        public string Descripcion { get; set; }
        
        public string Precio { get; set; }

        public string Stock_Disponible { get; set; }
        [Display(Name = "Categoría Producto")]
        public CategoriaProducto Categoria_Producto_id{ get; set; }
        [Display(Name = "Negocio")]
        public Negocio Negocio_id { get; set; }
        public string ImagenURL { get; set; }
    }
}