namespace TiendaNet2.Models
{
    public class ProductoNegocioViewModel
    {
        public IEnumerable<CategoriaProducto> CategoriaProducto { get; set; }
        public Negocio Negocio { get; set; }
        public Usuario Usuario { get; set; }
        public IEnumerable<Producto> Producto { get; set; }
        public Producto ProductoSelected { get; set; }
        public Pedido Pedido { get; set; }
        public IEnumerable<DetallePedido> DetallePedidosList { get; set; }
    }
}