using Microsoft.AspNetCore.Identity;
using TiendaNet2.Models;

using System;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;
using Microsoft.AspNetCore.Mvc;
using System.Text;
using Newtonsoft.Json;
namespace TiendaNet2.Servicios
{
    public interface IUsuarioService
    {
        Task<AuthResult> Authenticate(string username, string password);
        Task<string> Registrar(string username, string password, string email);
        Task<Usuario> ObtenerUser(string username);
    }

    public class UsuarioService : IUsuarioService
    {
        private readonly HttpClient httpClient;
        private readonly IConfiguration config;
        private readonly IHttpClientFactory _httpClientFactory;


        public UsuarioService(HttpClient httpClient, IConfiguration config, IHttpClientFactory _httpClientFactory)
        {
            this.httpClient = httpClient ?? throw new ArgumentNullException(nameof(httpClient));
            this.config = config;
            this._httpClientFactory = _httpClientFactory;
        }

        public async Task<AuthResult> Authenticate(string username, string password)
        {
            try
            {
                // Crear una solicitud HTTP POST para autenticar al usuario
                var authenticationRequest = new
                {
                    username,
                    password
                };
                string srv = config.GetValue<string>("_authURL");
                var response = await httpClient.PostAsJsonAsync($"{srv}login", authenticationRequest);

                if (response.IsSuccessStatusCode)
                {
                    var token = await response.Content.ReadAsStringAsync();
                    return new AuthResult(true, token); // Autenticación exitosa
                }
                else
                {
                    return new AuthResult(false, $"Error en la autenticación: {response.StatusCode} - {response.ReasonPhrase}");
                }
            }
            catch (Exception ex)
            {
                return new AuthResult(false, $"Error al autenticar al usuario: {ex.Message}");
            }
        }


        public async Task<string> Registrar(string username, string password, string email)
        {
            try
            {
                // Crear una solicitud HTTP POST para autenticar al usuario
                var authenticationRequest = new
                {
                    username,
                    password,
                    email
                };
                string srv = config.GetValue<string>("_authURL");
                var response = await httpClient.PostAsJsonAsync($"{srv}registro", authenticationRequest);

                if (response.IsSuccessStatusCode)
                {
                    return await response.Content.ReadAsStringAsync();
                }
                else
                {
                    throw new InvalidOperationException($"Error en el registro: {response.StatusCode} - {response.ReasonPhrase}");
                }
            }
            catch (Exception ex)
            {
                throw new InvalidOperationException("Error al registrar al usuario", ex);
            }
        }


        public async Task<Usuario> ObtenerUser(string username)
        {
            try
            {
                string srv = config.GetValue<string>("_usuarioURL")+ "nombre/"+username;

                using (var httpClient = new HttpClient())
                {
                    httpClient.BaseAddress = new Uri(srv);
                    var response = await httpClient.GetAsync(srv);

                    if (response.IsSuccessStatusCode)
                    {
                        // Lee y deserializa la respuesta JSON en un objeto Usuario
                        var jsonResponse = await response.Content.ReadAsStringAsync();
                        var usuario = JsonConvert.DeserializeObject<Usuario>(jsonResponse);
                        return usuario; // Retorna el usuario obtenido
                    }
                }
               
                return null;
                
            }
            catch (Exception ex)
            {
                // Ocurrió un error, retorna null
                return null;
            }
        }

    }
}