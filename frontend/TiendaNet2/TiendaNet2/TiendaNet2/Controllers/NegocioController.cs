using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Net.NetworkInformation;
using System.Reflection;
using System.Reflection.Metadata.Ecma335;
using System.Security.Claims;
using TiendaNet2.Models;
using TiendaNet2.Servicios;

namespace TiendaNet2.Controllers
{
    [AllowAnonymous]
    public class NegocioController : Controller
    {
        private readonly String ERROR = "-1";
        private readonly ILogger<NegocioController> _logger;
        private readonly INegocioService negocioService;
        private readonly ICategoriaNegocioService categoriaNegocioService ;
        private readonly IUsuarioService usuarioService;

        public NegocioController(
                ILogger<NegocioController> logger,
                INegocioService negocioService,
                ICategoriaNegocioService categoriaNegocioService,
                IUsuarioService usuarioService)
        {
            _logger = logger;
            this.negocioService = negocioService;
            this.categoriaNegocioService = categoriaNegocioService;
            this.usuarioService = usuarioService;
        }


        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> VerNegocio()
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario == null)
            {
                return View();

            }
            else
            {
                List<Negocio> negocios = await negocioService.ObtenerNegocioPorUsuario(nombreUsuario);
                return View(negocios);
            }

        }
        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> DarAlta()
        {

            return View();

        }

        [AllowAnonymous]
        public async Task<IActionResult> DarAltaNegocio()
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario == null)
            {
                return View();

            }
            else
            {
                if (await negocioService.DarAltaUsuarioComoNegocio(nombreUsuario))
                {
                    return RedirectToAction("Logout", "Usuario");
                }

            }

            return View();

        }

        [HttpGet]
        [AllowAnonymous]
        public async Task<IActionResult> CrearNegocio()
        {
            var viewModel = new CrearNegocioViewModel
            {
                CategoriasNegocio = await categoriaNegocioService.obtenerCategoriaNegocioList()
            };
            return View(viewModel);
        }

        [AllowAnonymous]
        [HttpPost]
        public async Task<IActionResult > CrearNegocio(Negocio negocio)
        {
            if (negocio == null)
            {
                return View("Error");
            }
            else{
                string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

                if (nombreUsuario == null)
                {
                    return View();

                }
                else
                {
                    negocio.Usuario= await usuarioService.ObtenerUser(nombreUsuario);
                    if (await negocioService.CrearNegocioAsync(negocio))
                    {
                        return View("_NegocioSuccessful", negocio);
                    }
                    else
                    {
                        return View("Error");
                    }
                }

            }
        }
    }
}
