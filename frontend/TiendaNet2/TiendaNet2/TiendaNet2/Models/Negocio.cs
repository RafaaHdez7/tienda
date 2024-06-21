using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class Negocio
    {
        [JsonProperty("id")]
        public int Id { get; set; }
        [Display(Name = "Nombre de negocio")]
        [Required(ErrorMessage = "El campo {0} es requerido")]
        [StringLength(maximumLength: 100, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        [JsonProperty("nombre")]
        public string Nombre { get; set; }
        [StringLength(maximumLength: 255, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        [JsonProperty("descripcion")]
        public string Descripcion { get; set; }
        [JsonProperty("categoriaNegocioId")]
        [Display(Name = "Categoria del negocio")]
        [Required(ErrorMessage = "El campo {0} es requerido")]
        public int CategoriaNegocioId { get; set; }
        [JsonProperty("usuario")]
        public Usuario? Usuario { get; set; }
        [JsonProperty("imagenURL")]
        public string ImagenURL { get; set; }
        [JsonProperty("link")]
        public string Link { get; set; }
    }
}