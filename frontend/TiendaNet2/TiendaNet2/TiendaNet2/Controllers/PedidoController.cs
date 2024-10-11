using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System;
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
        private readonly INegocioService negocioService;
        private readonly IProductoService productoService;
        private readonly IUsuarioService usuarioService;
        private readonly IDetallePedidoService detallesPedidoService;

        public PedidoController(ILogger<PedidoController> logger, PedidoService pedidoService,  INegocioService negocioService,
                IProductoService productoService,
                IUsuarioService usuarioService, IDetallePedidoService detallesPedidoService)
        {
            _logger = logger;
            this.pedidoService = pedidoService;
            this.negocioService = negocioService;
            this.productoService = productoService;
            this.usuarioService = usuarioService;
            this.usuarioService = usuarioService;
            this.detallesPedidoService = detallesPedidoService;
        }



            [AllowAnonymous]
            [HttpGet("Pedido/ResumenPedido/{pedidoId}")]
            public async Task<IActionResult> ResumenPedido(string pedidoId)
            {
                string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

                var pedido = await pedidoService.ObtenerPedidoPorPedidoId(pedidoId);
                if (pedido == null)
                {
                    return RedirectToAction("Index", "Home");
                }
                else
                {
                    var viewModel = new ProductoNegocioViewModel
                    {
                        Pedido = pedido,
                        Producto = await productoService.ObtenerProductosPorIdNegocio(pedido.Negocio.Id.ToString()),
                        DetallePedidosList = await detallesPedidoService.ObtenerDetallePedidosPorIdPedido(pedidoId)
                    };
                    return View(viewModel);
                }
            }

        [AllowAnonymous]
        [HttpGet("Pedido/ResumenPedidos")]
        public async Task<IActionResult> ResumenPedidos(int pageNumber = 1, int pageSize = 10)
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");
            var pedidos = await pedidoService.ObtenerPedidosPorUsuario(nombreUsuario);

            // Ordenar por fecha (descendente)
            var pedidosOrdenados = pedidos.OrderByDescending(p => p.FechaHora)
                                           .Skip((pageNumber - 1) * pageSize)
                                           .Take(pageSize)
                                           .ToList();

            var totalPedidos = pedidos.Count();
            var totalPages = (int)Math.Ceiling((double)totalPedidos / pageSize);

            var viewModel = new ListaPedidosViewModel
            {
                ListaPedidos = pedidosOrdenados,
                NombreUsuario = nombreUsuario,
                PageNumber = pageNumber,
                TotalPages = totalPages
            };

            return View(viewModel);
        }


       /* [AllowAnonymous]
        [HttpGet("Pedido/ResumenPedidos")]
        public async Task<IActionResult> ResumenPedidos()
        {
            // Obtener el nombre del usuario logueado desde la sesión
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            if (string.IsNullOrEmpty(nombreUsuario))
            {
                // Si el usuario no está logueado, redirigir a la página de inicio
                return RedirectToAction("Index", "Home");
            }

            // Obtener los pedidos del usuario logueado
            var pedidos = await pedidoService.ObtenerPedidosPorUsuario(nombreUsuario);
            if (pedidos == null || !pedidos.Any())
            {
                // Si no hay pedidos, redirigir a otra página o mostrar un mensaje
                return RedirectToAction("Index", "Home");
            }
            else
            {
                // Crear un ViewModel para enviar a la vista
                var viewModel = new ListaPedidosViewModel
                {
                    ListaPedidos = pedidos,
                    NombreUsuario = nombreUsuario
                    // Aquí podrías agregar más datos si es necesario, como detalles adicionales
                };

                // Retornar la vista con el ViewModel
                return View(viewModel);
            }
        }*/


        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> HacerPedido(int id)
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");

            
                var negocio = await negocioService.ObtenerNegocioPorIdNegocio(id.ToString());
                if (negocio == null)
                {
                    return RedirectToAction("Index", "Home");
            }
                else
                {
                    var viewModel = new ProductoNegocioViewModel
                    {
                        Negocio = negocio,
                        Producto = await productoService.ObtenerProductosPorIdNegocio(id.ToString())
                    };
                    return View(viewModel);
                }

        }
        [HttpPost]
        public async Task<IActionResult> HacerPedido([FromBody] List<ProductoPedido> productos)
        {
            string nombreUsuario = HttpContext.Session.GetString("NombreUsuario");
            if (productos != null && nombreUsuario != null)
            {
                List<DetallePedido> detallePedidoList = new List<DetallePedido>();
                foreach (var producto in productos)
                {
                    DetallePedido dp = new DetallePedido();
                    dp.Cantidad = producto.Cantidad;
                    dp.Producto_Id = new Producto();
                    dp.Producto_Id.Id = producto.IdProducto;
                    dp.Precio_unitario = producto.Precio_unitario.ToString();
                    dp.Pedido_Id = new Pedido();
                    dp.Pedido_Id.Usuario = new Usuario();
                    dp.Pedido_Id.Usuario.Nombre = nombreUsuario;
                    detallePedidoList.Add(dp);
                }
                var pedido = await pedidoService.CrearPedidoAsync(detallePedidoList);
                var response = new { mensaje = "Pedido confirmado con éxito", pedidoId = pedido.Id };
                if (pedido.Id == 0)
                {
                    return BadRequest();
                }
                return Ok(response);
            }
            return BadRequest();
        }
    }
    public class ProductoPedido
    {
        public int IdProducto { get; set; }
        public int Cantidad { get; set; }
        public decimal Precio_unitario { get; set; }  // Cambiado a decimal
    }

}
