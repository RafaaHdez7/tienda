using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System.IdentityModel.Tokens.Jwt;
using System.Reflection;
using System.Reflection.Metadata.Ecma335;
using System.Security.Claims;
using TiendaNet2.Models;
using TiendaNet2.Servicios;

namespace TiendaNet2.Controllers
{
    [AllowAnonymous]
    public class ProductoController : Controller
    {
        private readonly String ERROR = "-1";
        private ProductoService productoService;
        private readonly ILogger<ProductoController> _logger;

        public ProductoController(ILogger<ProductoController> logger, ProductoService productoService)
        {
            _logger = logger;
            this.productoService = productoService;
        }

        
        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> VerProducto()
        {

            return View();
            
        }

        [AllowAnonymous]
        [HttpPost]
        public async Task<IActionResult> AgregarProducto(Producto producto)
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario == null)
            {
                return RedirectToAction("Index", "Home");
            }
            else
            {
                if (await productoService.CrearProductoAsync(producto) != null)
                {
                    return RedirectToAction("EditarCarta", "Negocio", new { id = producto.Negocio_id });
                }
                return RedirectToAction("Index", "Home");

            }
               
        }


    }
}
