using Microsoft.AspNetCore.Identity;
using TiendaNet2.Models;

using System;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;
using Microsoft.AspNetCore.Mvc;
namespace TiendaNet2.Servicios
{
    public interface ICategoriaNegocioService
    {
        Task<List<CategoriaNegocio>> obtenerCategoriaNegocioList();
    }

    public class CategoriaNegocioService : ICategoriaNegocioService
    {
        private readonly HttpClient httpClient;
        private readonly IConfiguration config;
        private readonly IHttpClientFactory _httpClientFactory;


        public CategoriaNegocioService(HttpClient httpClient, IConfiguration config, IHttpClientFactory _httpClientFactory)
        {
            this.httpClient = httpClient ?? throw new ArgumentNullException(nameof(httpClient));
            this.config = config;
            this._httpClientFactory = _httpClientFactory;
        }

        public async Task<List<CategoriaNegocio>> obtenerCategoriaNegocioList()
        {
            List<CategoriaNegocio> categoriaNegocioList = null;

            string srv = config.GetValue<string>("_categoriaNegocioURL");

            using (var httpClient = new HttpClient())
            {
                httpClient.BaseAddress = new Uri(srv);
                var response = await httpClient.GetAsync(srv);

                if (response.IsSuccessStatusCode)
                {
                    var json = await response.Content.ReadAsStringAsync();
                    categoriaNegocioList = Newtonsoft.Json.JsonConvert.DeserializeObject<List<CategoriaNegocio>>(json);
                }
            }

            return categoriaNegocioList;
        }

    }

}