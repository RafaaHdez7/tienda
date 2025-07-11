using Newtonsoft.Json;
using System;
using System.ComponentModel.DataAnnotations;
using System.Data.SqlTypes;
using System.Xml.Linq;

namespace TiendaNet2.Models
{
    public class HistoricoTransacciones
    {
        [JsonProperty("id")]
        public long Id { get; set; }

        [JsonProperty("usuarioDTO")]
        public Usuario Usuario { get; set; }

        [JsonProperty("puntos")]
        [Display(Name = "Puntos")]
        [Range(0, double.MaxValue, ErrorMessage = "Los puntos deben ser positivo.")]
        public decimal Puntos { get; set; }

        [JsonProperty("tipoTransaccion")]
        public String TipoTransaccion { get; set; }

        [JsonProperty("descripcion")]
        public String Descripcion { get; set; }

        [JsonProperty("fechaCreacion")]
        public String FechaCreacion { get; set; }


    }
}
