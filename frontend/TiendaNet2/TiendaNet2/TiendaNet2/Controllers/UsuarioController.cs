using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System.Reflection.Metadata.Ecma335;
using TiendaNet2.Models;
using TiendaNet2.Servicios;

namespace TiendaNet2.Controllers
{
    public class UsuarioController : Controller
    {
        private readonly String ERROR = "-1";
        private UsuarioService usuarioService;

        public UsuarioController(UsuarioService usuarioService)
        {
 
            this.usuarioService = usuarioService;
        }
        [AllowAnonymous]
        public IActionResult Registro()
        {
            return View();
        }

        [HttpPost]
        [AllowAnonymous]
        public async Task<IActionResult> Registro(Usuario modelo)
        {
            /*if (!ModelState.IsValid)
            {
                return View(modelo);
            } TODO REVISAR ESTO*/

            var usuario = new Usuario() { Nombre = modelo.Nombre };

            var resultadoSingIn = await usuarioService.Registrar(modelo.Nombre, modelo.Contrasena);

            if (!resultadoSingIn.Equals(ERROR))
            {
                return RedirectToAction("Index", "Home");
            }
            else
            {
                ModelState.AddModelError(string.Empty, "Error en la autenticación");
                return View(modelo);
            }
        }


        [HttpGet]
        [AllowAnonymous]
        public IActionResult Login()
        {
            return View();
        }

        [HttpPost]
        [AllowAnonymous]
        public async Task<IActionResult> Login(Usuario modelo)
        {
        /*    modelo.Rol = "user";//TODO TENGO QUE QUITAR ESTO
            if (!ModelState.IsValid)
            {
                // Imprime o registra los errores del ModelState
                foreach (var error in ModelState.Values.SelectMany(v => v.Errors))
                {
                    Console.WriteLine($"Error: {error.ErrorMessage}");
                }
                return View(modelo);
            }*/

            var resultado = await usuarioService.Authenticate(modelo.Nombre, modelo.Contrasena);

            if (resultado != null) //&& resultado.Succeeded)
            {
                return RedirectToAction("Index", "Home");
            }
            else
            {
                ModelState.AddModelError(string.Empty, "Nombre de usuario o contraseña incorrectos.");
                return View(modelo);
            }
        }


        [HttpPost]
        public async Task<IActionResult> Logout()
        {
            await HttpContext.SignOutAsync(IdentityConstants.ApplicationScheme);
            return RedirectToAction("Index", "Home");
        }
    }
}
