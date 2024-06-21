using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class Pedido
    {
        [JsonProperty("id")]
        public int Id { get; set; }
        [JsonProperty("usuarioDTO")]
        public Usuario Usuario { get; set; }
        [StringLength(maximumLength: 255, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        [JsonProperty("negocioDTO")]
        public Negocio Negocio { get; set; }
        [JsonProperty("fechaHora")]
        public DateTime FechaHora { get; set; } = DateTime.Today;
        [JsonProperty("estadoPedidoDTO")]
        public EstadoPedido Estado { get; set; } = EstadoPedido.No_Realizado;

    }
}