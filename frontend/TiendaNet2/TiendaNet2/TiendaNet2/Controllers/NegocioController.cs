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
        private readonly ICategoriaProductoService categoriaProductoService;
        private readonly IProductoService productoService;
        private readonly IUsuarioService usuarioService;

        public NegocioController(
                ILogger<NegocioController> logger,
                INegocioService negocioService,
                ICategoriaNegocioService categoriaNegocioService,
                IUsuarioService usuarioService,
                ICategoriaProductoService categoriaProductoService,
                IProductoService productoService)
        {
            _logger = logger;
            this.negocioService = negocioService;
            this.categoriaNegocioService = categoriaNegocioService;
            this.usuarioService = usuarioService;
            this.categoriaProductoService = categoriaProductoService;
            this.productoService = productoService;
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
            if (HttpContext.Session.GetString("NombreUsuario") != null)
            {
                var viewModel = new CrearNegocioViewModel
                {
                    CategoriasNegocio = await categoriaNegocioService.obtenerCategoriaNegocioList()
                };
                return View(viewModel);
            }
            else
            {
                return RedirectToAction("Index", "Home");
            }
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

        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> EditarNegocio(int id)
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario != null)
            {
                var negocio = await  negocioService.ObtenerNegocioPorIdNegocio(id.ToString()); 
                if (negocio == null)
                {
                    return View();
                }
                else
                {
                    var viewModel = new CrearNegocioViewModel
                    {
                        CategoriasNegocio = await categoriaNegocioService.obtenerCategoriaNegocioList(),
                        Negocio = negocio
                    };
                    return View(viewModel);
                }
            }
            return View();
        }


        [AllowAnonymous]
        [HttpPost]
        public async Task<IActionResult> EditarNegocio(Negocio negocio)
        {
            if (negocio == null)
            {
                return View("Error");
            }
            else
            {
                string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

                if (nombreUsuario == null)
                {
                    return View();

                }
                else
                {
                    negocio.Usuario = await usuarioService.ObtenerUser(nombreUsuario);
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

        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> MenuNegocio(int id)
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario != null)
            {
                var negocio = await negocioService.ObtenerNegocioPorIdNegocio(id.ToString());
                if (negocio == null)
                {
                    return View();
                }
                else
                {
                    var viewModel = new CrearNegocioViewModel
                    {
                        Negocio = negocio
                    };
                    return View(viewModel);
                }
            }
            return View();
        }


        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> EditarCarta(int id)
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario != null)
            {
                var negocio = await negocioService.ObtenerNegocioPorIdNegocio(id.ToString()); 
                if (negocio == null)
                {
                    return View();
                }
                else
                {
                    var viewModel = new ProductoNegocioViewModel
                    {
                        CategoriaProducto = await categoriaProductoService.obtenerCategoriaProductoList(),
                        Negocio = negocio,
                        Producto = await productoService.ObtenerProductosPorIdNegocio(id.ToString())
                    };
                    return View(viewModel);
                }
            }
            return View();
        }
    }
}
