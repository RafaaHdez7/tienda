using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System.IdentityModel.Tokens.Jwt;
using System.Reflection.Metadata.Ecma335;
using System.Security.Claims;
using TiendaNet2.Models;
using TiendaNet2.Servicios;

namespace TiendaNet2.Controllers
{
    public class UsuarioController : Controller
    {
        private readonly String ERROR = "-1";
        private UsuarioService usuarioService;
        private MonederoService monederoService;

        public UsuarioController(UsuarioService usuarioService, MonederoService monederoService)
        {

            this.usuarioService = usuarioService;
            this.monederoService = monederoService;
        }
        [AllowAnonymous]
        public IActionResult Registro()
        {
            ViewBag.AuthToken = Request.Cookies["AuthToken"];
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

            var resultadoSingIn = await usuarioService.Registrar(modelo.Nombre, modelo.Contrasena, modelo.Email);

            if (!resultadoSingIn.Equals(ERROR))
            {
                ViewBag.AuthToken = Request.Cookies["AuthToken"];
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
            ViewBag.AuthToken = Request.Cookies["AuthToken"];
            return View();
        }

        [HttpPost]
        [AllowAnonymous]
        public async Task<IActionResult> Login(Usuario modelo)
        {
            var authResult = await usuarioService.Authenticate(modelo.Nombre, modelo.Contrasena);

            if (authResult.Success)
            {
                // Autenticación exitosa, obtener el nombre de usuario y el rol del token
                var tokenHandler = new JwtSecurityTokenHandler();
                var token = tokenHandler.ReadJwtToken(authResult.Token);

                // Obtener el nombre de usuario y el rol del token
                var nombreUsuarioClaim = token.Claims.FirstOrDefault(claim => claim.Type == "sub");
                var rolClaim = token.Claims.FirstOrDefault(claim => claim.Type == "role");

                if (nombreUsuarioClaim != null && rolClaim != null)
                {
                    var nombreUsuario = nombreUsuarioClaim.Value;
                    var rol = rolClaim.Value;
                    Monedero monedero = await monederoService.ObtenerMonederosPorUsuario(nombreUsuario);
                    // Autenticación exitosa, guardar el nombre de usuario y el rol en la sesión
                    HttpContext.Session.SetString("NombreUsuario", nombreUsuario);
                    HttpContext.Session.SetString("Rol", rol);
                    HttpContext.Session.SetString("Puntos", monedero.SaldoPuntos.ToString("N2"));

                    // Haz lo que necesites con el nombre de usuario y el rol


                    // Almacena el token en la cookie
                    var cookieOptions = new CookieOptions
                    {
                        HttpOnly = true,
                        Secure = true, // Configúralo según tus necesidades de seguridad
                                       // Otros ajustes de cookie si es necesario
                    };
                    Response.Cookies.Append("AuthToken", authResult.Token, cookieOptions);

                    // Renderizar la vista de éxito
                    ViewBag.AuthToken = Request.Cookies["AuthToken"];

                    return View("_LoginSuccessful", modelo);
                }
                else
                {
                    return View("Login", modelo);
                }
            }
            else
            {
                return View("Login", modelo);
            }
        }

        [AllowAnonymous]
        public IActionResult Logout()
        {
            // Eliminar la cookie del cliente
            Response.Cookies.Delete("AuthToken");
            // Eliminar los valores de sesión
            HttpContext.Session.Remove("NombreUsuario");
            HttpContext.Session.Remove("Rol");

            // Redirigir a la página de inicio
            return RedirectToAction("Index", "Home");
        }


        [AllowAnonymous]
        [HttpGet("Usuario/Perfil")]
        public async Task<IActionResult> Perfil()
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario == null)
            {
                return RedirectToAction("Index", "Home");
            }
            var viewModel = new UsuarioMonederoViewModel
            {
                Usuario = await usuarioService.ObtenerUser(nombreUsuario),
                Monedero = await monederoService.ObtenerMonederosPorUsuario(nombreUsuario)
            };

            return View(viewModel);
        }
    }
}
