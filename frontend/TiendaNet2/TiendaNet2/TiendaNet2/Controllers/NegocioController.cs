using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Net.NetworkInformation;
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

        public NegocioController(ILogger<NegocioController> logger, INegocioService negocioService)
        {
            _logger = logger;
            this.negocioService = negocioService;
        }


        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> VerNegocio()
        {

            return View();

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
    }
}
