using Newtonsoft.Json;
using System;
using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class Monedero
    {
        [JsonProperty("id")]
        public long Id { get; set; }

        [JsonProperty("usuario")]
        public Usuario Usuario { get; set; }

        [JsonProperty("saldoPuntos")]
        [Display(Name = "Saldo de Puntos")]
        [Range(0, double.MaxValue, ErrorMessage = "El saldo debe ser positivo.")]
        public decimal SaldoPuntos { get; set; }
    }
}
