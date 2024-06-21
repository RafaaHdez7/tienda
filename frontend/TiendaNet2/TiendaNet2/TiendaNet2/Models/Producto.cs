using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;
using System.Globalization;
using System.Numerics;

namespace TiendaNet2.Models
{
    public class Producto
    {
        [JsonProperty("id")]
        public int Id { get; set; }

        [Required(ErrorMessage = "El campo Nombre del Producto es requerido")]
        [StringLength(100, ErrorMessage = "No puede ser mayor a 100 caracteres")]
        [JsonProperty("nombreProducto")]
        public string Nombre_Producto { get; set; }
        [Required(ErrorMessage = "El campo descripcion es requerido")]
        [StringLength(255, ErrorMessage = "No puede ser mayor a 255 caracteres")]
        [JsonProperty("descripcion")]
        public string Descripcion { get; set; }

        [Required(ErrorMessage = "El campo Precio es requerido")]
        [DataType(DataType.Currency, ErrorMessage = "El campo Precio debe ser un valor de moneda")]
        [Range(0, int.MaxValue, ErrorMessage = "El campo precio debe ser un entero no negativo")]
        [JsonProperty("precio")]
        public string Precio { get; set; }

        [Required(ErrorMessage = "El campo Stock Disponible es requerido")]
        [Range(0, int.MaxValue, ErrorMessage = "El campo Stock Disponible debe ser un entero no negativo")]
        [JsonProperty("stockDisponible")]
        public int Stock_Disponible { get; set; }

        [Display(Name = "Categoría Producto")]
        [Required(ErrorMessage = "El campo Categoría Producto es requerido")]
        [JsonProperty("categoriaProductoDTO")]
        public CategoriaProducto Categoria_Producto { get; set; }

        [Display(Name = "Negocio")]
        [Required(ErrorMessage = "El campo Negocio es requerido")]
        [JsonProperty("negocioDTO")]
        public Negocio Negocio { get; set; }
        [Required(ErrorMessage = "El campo imagenURL es requerido")]
        [JsonProperty("imagenURL")]
        [DataType(DataType.ImageUrl)]
        public string ImagenURL { get; set; }

        // Constructor para inicializar valores por defecto
        public Producto()
        {
            Stock_Disponible = 0;
        }
    }
}
