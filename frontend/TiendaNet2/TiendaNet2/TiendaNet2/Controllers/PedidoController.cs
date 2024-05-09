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
    [AllowAnonymous]
    public class PedidoController : Controller
    {
        private readonly String ERROR = "-1";
        private PedidoService pedidoService;
        private readonly ILogger<PedidoController> _logger;

        public PedidoController(ILogger<PedidoController> logger, PedidoService pedidoService)
        {
            _logger = logger;
            this.pedidoService = pedidoService;
        }

        
        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> VerPedido()
        {

            return View();
            
        }


    }
}
