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
    public interface IPedidoService
    {
        Task<Pedido> CrearPedidoAsync(List<DetallePedido> dp);
        Task<Pedido> ObtenerPedidoPorPedidoId(string idPedido);
    }

    public class PedidoService : IPedidoService
    {
        private readonly HttpClient httpClient;
        private readonly IConfiguration _config;
        private readonly IHttpClientFactory _httpClientFactory;

        

        public PedidoService(HttpClient httpClient, IConfiguration _config, IHttpClientFactory _httpClientFactory )
        {
            this.httpClient = httpClient ?? throw new ArgumentNullException(nameof(httpClient));
            this._config = _config;
            this._httpClientFactory = _httpClientFactory;
        }
        public async Task<Pedido> CrearPedidoAsync(List<DetallePedido> dp)
        {
            Pedido pedido = new Pedido();
            bool creadoExitosamente = false;
            // Crear un objeto anónimo solo con las propiedades Usuario y Negocio
            try
            {
                string srv = _config.GetValue<string>("_pedidoURL");

                using (var httpClient = new HttpClient())
                {
                    httpClient.BaseAddress = new Uri(srv);

                    // Convierte el objeto 'nuevoNegocio' a formato JSON
                    string jsonNegocio = Newtonsoft.Json.JsonConvert.SerializeObject(dp);

                    // Configura el contenido del request con el JSON del nuevo negocio
                    var content = new StringContent(jsonNegocio, Encoding.UTF8, "application/json");

                    // Envía la solicitud HTTP POST para crear el nuevo negocio
                    var response = await httpClient.PostAsync(srv, content);

                    if (response.IsSuccessStatusCode)
                    {
                        var json = await response.Content.ReadAsStringAsync();
                        pedido = Newtonsoft.Json.JsonConvert.DeserializeObject<Pedido>(json);
                    }
                }
            }
            catch (Exception ex)
            {
                // Maneja cualquier excepción que pueda ocurrir durante la creación del negocio
                Console.WriteLine($"Error al crear el pedido: {ex.Message}");
            }

            return pedido;
        }

        public async Task<Pedido> ObtenerPedidoPorPedidoId(string idPedido)
        {
            Pedido pedido = new Pedido();

            string srv = _config.GetValue<string>("_pedidoURL") + idPedido;

            using (var httpClient = new HttpClient())
            {
                httpClient.BaseAddress = new Uri(srv);
                var response = await httpClient.GetAsync(srv);

                if (response.IsSuccessStatusCode)
                {
                    var json = await response.Content.ReadAsStringAsync();
                    pedido = Newtonsoft.Json.JsonConvert.DeserializeObject<Pedido>(json);
                }
            }

            return pedido;
        }

        public async Task<List<Pedido>> ObtenerPedidosPorUsuario(string nombreUsuario)
        {

            List<Pedido> pedido = new List<Pedido>();

            string srv = _config.GetValue<string>("_pedidoURL")+ "usuario/nombre/" + nombreUsuario;

            using (var httpClient = new HttpClient())
            {
                httpClient.BaseAddress = new Uri(srv);
                var response = await httpClient.GetAsync(srv);

                if (response.IsSuccessStatusCode)
                {
                    var json = await response.Content.ReadAsStringAsync();
                    pedido = Newtonsoft.Json.JsonConvert.DeserializeObject<List<Pedido>>(json);
                }
            }

            return pedido;
        }

    }


}