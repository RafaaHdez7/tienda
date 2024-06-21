using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class DetallePedido
    {
        [JsonProperty("id")]
        public int Id { get; set; }
        [JsonProperty("pedido")]
        public Pedido Pedido_Id { get; set; }
        [StringLength(maximumLength: 255, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        [JsonProperty("producto")]
        public Producto Producto_Id { get; set; }
        [JsonProperty("cantidad")]
        public int Cantidad { get; set; }
        [JsonProperty("precioUnitario")]
        public int Precio_unitario { get; set; }

    }
}