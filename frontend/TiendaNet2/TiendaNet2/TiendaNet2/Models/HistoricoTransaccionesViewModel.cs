namespace TiendaNet2.Models
{
    public class HistoricoTransaccionesViewModel
    {
        public List<HistoricoTransacciones> Transacciones { get; set; }
        public string NombreUsuario { get; set; }
        public int PageNumber { get; set; }
        public int TotalPages { get; set; }
    }

}