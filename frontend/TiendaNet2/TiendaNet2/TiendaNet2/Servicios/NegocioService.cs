using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Threading.Tasks;
using Microsoft.Extensions.Configuration;
using TiendaNet2.Models;

namespace TiendaNet2.Servicios
{
    public interface INegocioService
    {
        Task<List<Negocio>> ObtenerNegociosAsync();
    }

    public class NegocioService : INegocioService
    {
        private readonly IConfiguration _config;

        public NegocioService(IConfiguration config)
        {
            _config = config;
        }

        public async Task<List<Negocio>> ObtenerNegociosAsync()
        {
            List<Negocio> negocioList = null;

            string srv = _config.GetValue<string>("_negocioURL");

            using (var httpClient = new HttpClient())
            {
                httpClient.BaseAddress = new Uri(srv);
                var response = await httpClient.GetAsync(srv);

                if (response.IsSuccessStatusCode)
                {
                    var json = await response.Content.ReadAsStringAsync();
                    negocioList = Newtonsoft.Json.JsonConvert.DeserializeObject<List<Negocio>>(json);
                }
            }

            return negocioList;
        }
    }
}
