namespace TiendaNet2.Models
{
    public class ListaPedidosViewModel
    {
        public List<Pedido> ListaPedidos { get; set; }
        public string NombreUsuario { get; set; }
        public int PageNumber { get; set; }
        public int TotalPages { get; set; }
    }

}