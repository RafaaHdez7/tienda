using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class Producto
    {
        public int Id { get; set; }
        [Required(ErrorMessage = "El campo {0} es requerido")]
        [StringLength(maximumLength: 100, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        [JsonProperty("nombreProducto")]
        public string Nombre_Producto { get; set; }
        [StringLength(maximumLength: 255, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        [JsonProperty("descripcion")]
        public string Descripcion { get; set; }
        [JsonProperty("precio")]
        public string Precio { get; set; }
        [JsonProperty("stockDisponible")]
        public string Stock_Disponible { get; set; }
        [Display(Name = "Categoría Producto")]
        [JsonProperty("categoriaProductoDTO")]
        public CategoriaProducto Categoria_Producto{ get; set; }
        [Display(Name = "Negocio")]
        [JsonProperty("negocioDTO")]
        public Negocio Negocio { get; set; }
        [JsonProperty("negocioDTO.id")]
        public string Negocio_id { get; set; }
        [JsonProperty("categoriaProductoDTO.id")]
        public string Categoria_Producto_Id { get; set; }
        [JsonProperty("imagenURL")]
        public string ImagenURL { get; set; }
    }
}