using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class DetallePedido
    {
        public int Id { get; set; }
        public Pedido Pedido_Id { get; set; }
        [StringLength(maximumLength: 255, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        public Producto Producto_Id { get; set; }

        public int Cantidad { get; set; }

        public int Precio_unitario { get; set; }

    }
}