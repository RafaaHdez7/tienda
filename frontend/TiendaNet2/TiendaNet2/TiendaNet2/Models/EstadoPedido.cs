using System;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Reflection;

namespace TiendaNet2.Models
{
    public enum EstadoPedido
    {
        [Display(Name = "No Realizado")]
        No_Realizado = 1,

        [Display(Name = "En Proceso")]
        En_Proceso = 2,

        [Display(Name = "Esperando Repartidor")]
        Esperando_Repartidor = 3,

        [Display(Name = "Recogido por Repartidor")]
        Recogido_Repartidor = 4,

        [Display(Name = "Entregado")]
        Entregado = 5,

        [Display(Name = "Cancelado")]
        Cancelado = -1,
    }

    public static class EstadoPedidoExtensions
    {
        public static string GetDisplayName(this EstadoPedido estadoPedido)
        {
            var type = typeof(EstadoPedido);
            var memberInfo = type.GetMember(estadoPedido.ToString());
            var attributes = memberInfo[0].GetCustomAttributes(typeof(DisplayAttribute), false);
            return (attributes.Length > 0) ? ((DisplayAttribute)attributes[0]).Name : estadoPedido.ToString();
        }

        public static EstadoPedido GetByDescripcion(string descripcion)
        {
            var type = typeof(EstadoPedido);
            foreach (var field in type.GetFields(BindingFlags.Public | BindingFlags.Static))
            {
                var attribute = (DisplayAttribute)field.GetCustomAttribute(typeof(DisplayAttribute));
                if (attribute != null && attribute.Name.Equals(descripcion, StringComparison.OrdinalIgnoreCase))
                {
                    return (EstadoPedido)field.GetValue(null);
                }
            }

            throw new ArgumentException($"Descripción de estado desconocida: {descripcion}");
        }
    }
}
