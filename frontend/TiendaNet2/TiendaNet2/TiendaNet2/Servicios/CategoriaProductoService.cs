using Microsoft.AspNetCore.Identity;
using TiendaNet2.Models;

using System;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;
using Microsoft.AspNetCore.Mvc;
namespace TiendaNet2.Servicios
{
    public interface ICategoriaProductoService
    {
        Task<List<CategoriaProducto>> obtenerCategoriaProductoList();
    }

    public class CategoriaProductoService : ICategoriaProductoService
    {
        private readonly HttpClient httpClient;
        private readonly IConfiguration config;
        private readonly IHttpClientFactory _httpClientFactory;


        public CategoriaProductoService(HttpClient httpClient, IConfiguration config, IHttpClientFactory _httpClientFactory)
        {
            this.httpClient = httpClient ?? throw new ArgumentNullException(nameof(httpClient));
            this.config = config;
            this._httpClientFactory = _httpClientFactory;
        }

        public async Task<List<CategoriaProducto>> obtenerCategoriaProductoList()
        {
            List<CategoriaProducto> categoriaProductoList = null;

            string srv = config.GetValue<string>("_categoriaProductoURL");

            using (var httpClient = new HttpClient())
            {
                httpClient.BaseAddress = new Uri(srv);
                var response = await httpClient.GetAsync(srv);

                if (response.IsSuccessStatusCode)
                {
                    var json = await response.Content.ReadAsStringAsync();
                    categoriaProductoList = Newtonsoft.Json.JsonConvert.DeserializeObject<List<CategoriaProducto>>(json);
                }
            }

            return categoriaProductoList;
        }

    }

}