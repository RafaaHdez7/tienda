using Microsoft.AspNetCore.Identity;
using TiendaNet2.Models;

using System;
using System.Net.Http;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;
using Microsoft.AspNetCore.Mvc;
namespace TiendaNet2.Servicios
{
    public interface IUsuarioService
    {
        Task<AuthResult> Authenticate(string username, string password);
        Task<string> Registrar(string username, string password);
    }

    public class UsuarioService : IUsuarioService
    {
        private readonly HttpClient httpClient;
        private readonly IConfiguration config;
        private readonly IHttpClientFactory _httpClientFactory;
        

        public UsuarioService(HttpClient httpClient, IConfiguration config, IHttpClientFactory _httpClientFactory )
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


        public async Task<string> Registrar(string username, string password)
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
    }

  
}