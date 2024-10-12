using Microsoft.AspNetCore.Identity;
using TiendaNet2.Models;

using System;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;
using Microsoft.AspNetCore.Mvc;
using System.Text;
namespace TiendaNet2.Servicios
{
    public interface IMonederoService
    {
        Task<Monedero> ObtenerMonederosPorUsuario(string nombreUsuario);
    }

    public class MonederoService : IMonederoService
    {
        private readonly HttpClient httpClient;
        private readonly IConfiguration _config;
        private readonly IHttpClientFactory _httpClientFactory;
        

        public MonederoService(HttpClient httpClient, IConfiguration config, IHttpClientFactory _httpClientFactory )
        {
            this.httpClient = httpClient ?? throw new ArgumentNullException(nameof(httpClient));
            this._config = config;
            this._httpClientFactory = _httpClientFactory;
        }

        public async Task<Monedero> ObtenerMonederosPorUsuario(string nombreUsuario)
        {
            Monedero monedero = new Monedero();

            string srv = _config.GetValue<string>("_monederoURL") + "usuario/nombre/" + nombreUsuario;

            using (var httpClient = new HttpClient())
            {
                httpClient.BaseAddress = new Uri(srv);
                var response = await httpClient.GetAsync(srv);

                if (response.IsSuccessStatusCode)
                {
                    var json = await response.Content.ReadAsStringAsync();
                    monedero = Newtonsoft.Json.JsonConvert.DeserializeObject < Monedero>(json);
                }
            }

            return monedero;
        }

    }

  
}