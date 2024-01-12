using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public enum EstadoPedido
    {
        No_Realizado = 1,
        En_Proceso = 2,
        Esperando_Repartidor = 3,
        Recogido_Repartidor = 4,
        Entregado = 5,
        Cancelado = -1,
    }
}