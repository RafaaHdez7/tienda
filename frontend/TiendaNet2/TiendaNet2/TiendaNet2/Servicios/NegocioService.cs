using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Extensions.Configuration;
using TiendaNet2.Models;

namespace TiendaNet2.Servicios
{
    public interface INegocioService
    {
        Task<List<Negocio>> ObtenerNegociosAsync();
        Task<bool> CrearNegocioAsync(Negocio nuevoNegocio);
        Task<bool> DarAltaUsuarioComoNegocio(String nombreUsuario);
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

        public async Task<bool> CrearNegocioAsync(Negocio nuevoNegocio)
        {
            bool creadoExitosamente = false;

            try
            {
                string srv = _config.GetValue<string>("_negocioURL");

                using (var httpClient = new HttpClient())
                {
                    httpClient.BaseAddress = new Uri(srv);

                    // Convierte el objeto 'nuevoNegocio' a formato JSON
                    string jsonNegocio = Newtonsoft.Json.JsonConvert.SerializeObject(nuevoNegocio);

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
                Console.WriteLine($"Error al crear el negocio: {ex.Message}");
            }

            return creadoExitosamente;
        }

        public async Task<bool> DarAltaUsuarioComoNegocio(String nombreUsuario)
        {
            bool creadoExitosamente = false;

            try
            {

                string srv = _config.GetValue<string>("_usuarioURL")+ "darAltaNegocio";

                using (var httpClient = new HttpClient())
                {
                    httpClient.BaseAddress = new Uri(srv);

                    // Configura el contenido del request con el JSON del nuevo negocio
                    var content = new StringContent(nombreUsuario, Encoding.UTF8, "application/json");

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
                Console.WriteLine($"Error al crear el negocio: {ex.Message}");
            }

            return creadoExitosamente;
        }


    }
}
