using Microsoft.AspNetCore.Identity;
using TiendaNet2.Models;

using System;
using System.Net.Http;
using System.Threading.Tasks;
namespace TiendaNet2.Servicios
{
    public interface IUsuarioService
    {
        Task<string> Authenticate(string username, string password);
        Task<string> Registrar(string username, string password);
    }

    public class UsuarioService : IUsuarioService
    {
        private readonly HttpClient httpClient;
        private readonly IConfiguration _config;

        public UsuarioService(HttpClient httpClient, IConfiguration config)
        {
            this.httpClient = httpClient ?? throw new ArgumentNullException(nameof(httpClient));
            _config = config;  
        }

        public async Task<string> Authenticate(string username, string password)
        {
            try
            {
                // Crear una solicitud HTTP POST para autenticar al usuario
                var authenticationRequest = new
                {
                    username,
                    password
                };
                string srv = _config.GetValue<string>("_authURL");
                var response = await httpClient.PostAsJsonAsync($"{srv}login", authenticationRequest);

                if (response.IsSuccessStatusCode)
                {
                    // La autenticación fue exitosa, se espera que el servicio de autenticación devuelva el token JWT
                    return await response.Content.ReadAsStringAsync();
                }
                else
                {
                    // Manejar errores de autenticación aquí
                    throw new InvalidOperationException($"Error en la autenticación: {response.StatusCode} - {response.ReasonPhrase}");
                }
            }
            catch (Exception ex)
            {
                // Manejar otros errores aquí
                throw new InvalidOperationException("Error al autenticar al usuario", ex);
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
                string srv = _config.GetValue<string>("_authURL");
                var response = await httpClient.PostAsJsonAsync($"{srv}registro", authenticationRequest);

                if (response.IsSuccessStatusCode)
                {
                    // La autenticación fue exitosa, se espera que el servicio de autenticación devuelva el token JWT
                    return await response.Content.ReadAsStringAsync();
                }
                else
                {
                    // Manejar errores de autenticación aquí
                    throw new InvalidOperationException($"Error en la autenticación: {response.StatusCode} - {response.ReasonPhrase}");
                }
            }
            catch (Exception ex)
            {
                // Manejar otros errores aquí
                throw new InvalidOperationException("Error al autenticar al usuario", ex);
            }
        }
    }
}