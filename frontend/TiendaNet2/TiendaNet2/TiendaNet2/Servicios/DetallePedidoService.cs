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
    public interface IDetallePedidoService
    {
        Task<bool> CrearDetallePedidoAsync(DetallePedido DetallePedido);
        Task<bool> ActualizarDetallePedidoAsync(DetallePedido detallePedido);
        Task<List<DetallePedido>> ObtenerDetallePedidosPorIdPedido(string idPedido);
    }

    public class DetallePedidoService : IDetallePedidoService
    {
        private readonly HttpClient httpClient;
        private readonly IConfiguration _config;
        private readonly IHttpClientFactory _httpClientFactory;
        

        public DetallePedidoService(HttpClient httpClient, IConfiguration config, IHttpClientFactory _httpClientFactory )
        {
            this.httpClient = httpClient ?? throw new ArgumentNullException(nameof(httpClient));
            this._config = config;
            this._httpClientFactory = _httpClientFactory;
        }

        public async Task<List<DetallePedido>> ObtenerDetallePedidosPorIdPedido(string idPedido)
        {
            List<DetallePedido> detallePedidos = new List<DetallePedido>();

            string srv = _config.GetValue<string>("_detallePedidoURL") + "pedido/" + idPedido;

            using (var httpClient = new HttpClient())
            {
                httpClient.BaseAddress = new Uri(srv);
                var response = await httpClient.GetAsync(srv);

                if (response.IsSuccessStatusCode)
                {
                    var json = await response.Content.ReadAsStringAsync();
                    detallePedidos = Newtonsoft.Json.JsonConvert.DeserializeObject<List<DetallePedido>>(json);
                }
            }

            return detallePedidos;
        }


        public async Task<bool> CrearDetallePedidoAsync(DetallePedido detallePedido)
        {
            bool creadoExitosamente = false;
            try
            {
                string srv = _config.GetValue<string>("_detallePedidoURL");

                using (var httpClient = new HttpClient())
                {
                    httpClient.BaseAddress = new Uri(srv);

                    // Convierte el objeto 'nuevoNegocio' a formato JSON
                    string jsonNegocio = Newtonsoft.Json.JsonConvert.SerializeObject(detallePedido);

                    // Configura el contenido del request con el JSON del nuevo negocio
                    var content = new StringContent(jsonNegocio, Encoding.UTF8, "application/json");

                    // Envía la solicitud HTTP POST para crear el nuevo negocio
                    var response = await httpClient.PostAsync(srv, content);

                    if (response.IsSuccessStatusCode)
                    {
                        // Si la respuesta indica éxito, marca la creación como exitosa
                        creadoExitosamente = true;
                    }
                }
            }
            catch (Exception ex)
            {
                // Maneja cualquier excepción que pueda ocurrir durante la creación del negocio
                Console.WriteLine($"Error al crear el detallePedido: {ex.Message}");
            }

            return creadoExitosamente;
        }


        public async Task<bool> ActualizarDetallePedidoAsync(DetallePedido detallePedido)
        {
            bool creadoExitosamente = false;
            try
            {
                string srv = _config.GetValue<string>("_detallePedidoURL") + detallePedido.Id;

                using (var httpClient = new HttpClient())
                {
                    httpClient.BaseAddress = new Uri(srv);

                    // Convierte el objeto 'nuevoNegocio' a formato JSON
                    string jsonNegocio = Newtonsoft.Json.JsonConvert.SerializeObject(detallePedido);

                    // Configura el contenido del request con el JSON del nuevo negocio
                    var content = new StringContent(jsonNegocio, Encoding.UTF8, "application/json");

                    // Envía la solicitud HTTP POST para crear el nuevo negocio
                    var response = await httpClient.PostAsync(srv, content);

                    if (response.IsSuccessStatusCode)
                    {
                        // Si la respuesta indica éxito, marca la creación como exitosa
                        creadoExitosamente = true;
                    }
                }
            }
            catch (Exception ex)
            {
                // Maneja cualquier excepción que pueda ocurrir durante la creación del negocio
                Console.WriteLine($"Error al actualizar el detallePedido: {ex.Message}");
            }

            return creadoExitosamente;
        }

    }

  
}