using Microsoft.AspNetCore.Identity;
using TiendaNet2.Models;

using System;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;
using Microsoft.AspNetCore.Mvc;
namespace TiendaNet2.Servicios
{
    public interface IPedidoService
    {
    }

    public class PedidoService : IPedidoService
    {
        private readonly HttpClient httpClient;
        private readonly IConfiguration config;
        private readonly IHttpClientFactory _httpClientFactory;
        

        public PedidoService(HttpClient httpClient, IConfiguration config, IHttpClientFactory _httpClientFactory )
        {
            this.httpClient = httpClient ?? throw new ArgumentNullException(nameof(httpClient));
            this.config = config;
            this._httpClientFactory = _httpClientFactory;
        }

    }

  
}