using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class Pedido
    {
        public int Id { get; set; }
        public Usuario Usuario_Id { get; set; }
        [StringLength(maximumLength: 255, ErrorMessage = "No puede ser mayor a {1} caracteres")]
        public Negocio Negocio_Id { get; set; }

        public DateTime FechaHora { get; set; } = DateTime.Today;

        public EstadoPedido Estado { get; set; } = EstadoPedido.No_Realizado;

    }
}