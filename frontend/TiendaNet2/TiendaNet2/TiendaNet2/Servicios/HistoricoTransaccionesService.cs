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
    public interface IHistoricoTransaccionesService
    {
        Task<List<HistoricoTransacciones>> ObtenerHistoricoTransaccionesPorUsuario(string nombreUsuario);
        Task<bool> CrearHistoricoTransaccionesAsync(HistoricoTransacciones transaccion);
    }

    public class HistoricoTransaccionesService : IHistoricoTransaccionesService
    {
        private readonly HttpClient httpClient;
        private readonly IConfiguration _config;
        private readonly IHttpClientFactory _httpClientFactory;
        

        public HistoricoTransaccionesService(HttpClient httpClient, IConfiguration config, IHttpClientFactory _httpClientFactory )
        {
            this.httpClient = httpClient ?? throw new ArgumentNullException(nameof(httpClient));
            this._config = config;
            this._httpClientFactory = _httpClientFactory;
        }

        public async Task<List<HistoricoTransacciones>> ObtenerHistoricoTransaccionesPorUsuario(string nombreUsuario)
        {
            List<HistoricoTransacciones> transacciones = new List<HistoricoTransacciones>();

            string srv = _config.GetValue<string>("_historicoTransaccionesURL") +"usuario/nombre/" + nombreUsuario;

            using (var httpClient = new HttpClient())
            {
                httpClient.BaseAddress = new Uri(srv);
                var response = await httpClient.GetAsync(srv);

                if (response.IsSuccessStatusCode)
                {
                    var json = await response.Content.ReadAsStringAsync();
                    transacciones = Newtonsoft.Json.JsonConvert.DeserializeObject < List<HistoricoTransacciones>>(json);
                }
            }

            return transacciones;
        }



        public async Task<bool> CrearHistoricoTransaccionesAsync(HistoricoTransacciones transaccion)
        {
            bool creadoExitosamente = false;
            try
            {
                string srv = _config.GetValue<string>("_historicoTransaccionesURL");

                using (var httpClient = new HttpClient())
                {
                    httpClient.BaseAddress = new Uri(srv);

                    // Convierte el objeto 'nuevoNegocio' a formato JSON
                    string jsonTransaccion = Newtonsoft.Json.JsonConvert.SerializeObject(transaccion);

                    // Configura el contenido del request con el JSON del nuevo negocio
                    var content = new StringContent(jsonTransaccion, Encoding.UTF8, "application/json");

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
                Console.WriteLine($"Error al crear el producto: {ex.Message}");
            }

            return creadoExitosamente;
        }

    }

  
}