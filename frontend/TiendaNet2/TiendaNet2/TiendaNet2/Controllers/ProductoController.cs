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
        private IProductoService productoService;
        private INegocioService negocioService;
        private readonly ILogger<ProductoController> _logger;
        private readonly ICategoriaProductoService categoriaProductoService;

        public ProductoController(ILogger<ProductoController> logger, IProductoService productoService, ICategoriaProductoService categoriaProductoService, INegocioService negocioService)
        {
            _logger = logger;
            this.productoService = productoService;
            this.categoriaProductoService = categoriaProductoService;
            this.negocioService = negocioService;
        }

        
        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> VerProducto()
        {

            return View();
            
        }

        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> AgregarProducto(long id)
        {

            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario == null)
            {
                return RedirectToAction("Index", "Home");
            }
            else
            {
                Negocio negocio = await negocioService.ObtenerNegocioPorIdNegocio(id.ToString());
                var viewModel = new ProductoNegocioViewModel
                {
                    Negocio = negocio,
                    CategoriaProducto = await categoriaProductoService.obtenerCategoriaProductoList(),
                };
                return View(viewModel);
            }
               
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
                if (await productoService.CrearProductoAsync(producto))
                {
                    return RedirectToAction("EditarCarta", "Negocio", new { id = producto.Negocio.Id });
                }
                return RedirectToAction("Index", "Home"); //TODO A ERROR
            }

        }

        [AllowAnonymous]
        [HttpPost]
        public async Task<IActionResult> EditarProducto(Producto producto)
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario == null)
            {
                return RedirectToAction("Index", "Home");
            }
            else
            {
                var viewModel = new ProductoNegocioViewModel
                {
                    CategoriaProducto = await categoriaProductoService.obtenerCategoriaProductoList(),
                    ProductoSelected = producto,
                    Negocio = await negocioService.ObtenerNegocioPorIdNegocio(producto.Negocio.Id.ToString())
                };
                return View(viewModel); // Renderiza la vista de edición con el modelo recibido
            }
        }
        [AllowAnonymous]
        [HttpPost]
        public async Task<IActionResult> EditarProductoBack(Producto producto)
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario == null)
            {
                return RedirectToAction("Index", "Home");
            }
            else
            {
                if (await productoService.ActualizarProductoAsync(producto))
                {
                    return RedirectToAction("EditarCarta", "Negocio", new { id = producto.Negocio.Id });
                }
                var viewModel = new ProductoNegocioViewModel
                {
                    CategoriaProducto = await categoriaProductoService.obtenerCategoriaProductoList(),
                    ProductoSelected = producto,
                    Negocio = await negocioService.ObtenerNegocioPorIdNegocio(producto.Negocio.Id.ToString())
                };
                return RedirectToAction("Index", "Home");
            }
        }

    }
}
