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
    public class HistoricoTransaccionesController : Controller
    {
        private readonly String ERROR = "-1";
        private readonly ILogger<NegocioController> _logger;
        private readonly INegocioService negocioService;
        private readonly IHistoricoTransaccionesService historicoTransaccionesService ;
        private readonly IMonederoService monederoService;
        private readonly IProductoService productoService;
        private readonly IUsuarioService usuarioService;

        public HistoricoTransaccionesController(
                ILogger<NegocioController> logger,
                INegocioService negocioService,
                IHistoricoTransaccionesService historicoTransaccionesService,
                IUsuarioService usuarioService,
                IMonederoService monederoService,
                IProductoService productoService)
        {
            _logger = logger;
            this.negocioService = negocioService;
            this.historicoTransaccionesService = historicoTransaccionesService;
            this.usuarioService = usuarioService;
            this.monederoService = monederoService;
            this.productoService = productoService;
        }


        [AllowAnonymous]
        [HttpGet("HistoricoTransacciones/VerHistorico")]
        public async Task<IActionResult> VerHistorico(int pageNumber = 1, int pageSize = 10)
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario == null)
            {
                return RedirectToAction("Index", "Home");
            }

            var transacciones = await historicoTransaccionesService.ObtenerHistoricoTransaccionesPorUsuario(nombreUsuario);

            // Ordenar las transacciones por ID de manera descendente (puedes ajustar la columna si es necesario)
            var transaccionesOrdenadas = transacciones.OrderByDescending(t => t.Id)
                                                       .Skip((pageNumber - 1) * pageSize)
                                                       .Take(pageSize)
                                                       .ToList();

            var totalTransacciones = transacciones.Count();
            var totalPages = (int)Math.Ceiling((double)totalTransacciones / pageSize);

            var viewModel = new HistoricoTransaccionesViewModel
            {
                Transacciones = transaccionesOrdenadas,
                NombreUsuario = nombreUsuario,
                PageNumber = pageNumber,
                TotalPages = totalPages
            };

            return View(viewModel);
        }

        [AllowAnonymous]
        [HttpGet("HistoricoTransacciones/GestionarSaldo")]
        public async Task<IActionResult> GestionarSaldo()
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (nombreUsuario == null)
            {
                return RedirectToAction("Index", "Home");

            }
            var model = new UsuarioMonederoViewModel
            {
                Monedero = await monederoService.ObtenerMonederosPorUsuario(nombreUsuario),
                Usuario = await usuarioService.ObtenerUser(nombreUsuario)
            };
            return View(model);
        }

        [HttpPost]
        public async Task<IActionResult> RealizarTransaccion(string tipoTransaccion, decimal cantidad, string descripcion)
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");
            var monedero = await monederoService.ObtenerMonederosPorUsuario(nombreUsuario);

            if (tipoTransaccion == "Ingreso")
            {
                monedero.SaldoPuntos += cantidad;
            }
            else if (tipoTransaccion == "Deducción")
            {
                if (monedero.SaldoPuntos >= cantidad)
                {
                    monedero.SaldoPuntos -= cantidad;
                }
                else
                {
                    ModelState.AddModelError("", "No puedes deducir más del saldo disponible.");
                    return View("GestionarSaldo", new UsuarioMonederoViewModel { Monedero = monedero });
                }
            }

            var transaccion = new HistoricoTransacciones();
            transaccion.TipoTransaccion = tipoTransaccion;
            transaccion.Descripcion = descripcion;
            transaccion.Usuario = await usuarioService.ObtenerUser(nombreUsuario);
            transaccion.Puntos = cantidad;
            // Guardar la transacción en el histórico
            var transaccionCreada = await historicoTransaccionesService.CrearHistoricoTransaccionesAsync(transaccion);

            if (transaccionCreada)
            {
                var monederoActualizado = await monederoService.ObtenerMonederosPorUsuario(nombreUsuario);
                HttpContext.Session.SetString("Puntos", monederoActualizado.SaldoPuntos.ToString("N2"));
                return RedirectToAction("VerHistorico", "HistoricoTransacciones");
            }
            // Guardar los cambios en el monedero
            //await monederoService.ActualizarMonedero(monedero);
            return RedirectToAction("Error", "error");

        }


        /*
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
        */
    }
}
