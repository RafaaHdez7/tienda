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
        private readonly INegocioService negocioService;
        private readonly IProductoService productoService;
        private readonly IUsuarioService usuarioService;

        public PedidoController(ILogger<PedidoController> logger, PedidoService pedidoService,  INegocioService negocioService,
                IProductoService productoService,
                IUsuarioService usuarioService)
        {
            _logger = logger;
            this.pedidoService = pedidoService;
            this.negocioService = negocioService;
            this.productoService = productoService;
            this.usuarioService = usuarioService;
    }

        
        [AllowAnonymous]
        [HttpGet]
        public async Task<IActionResult> VerPedido()
        {

            return View();
            
        }

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
                    dp.Precio_unitario = producto.Precio_unitario;
                    dp.Pedido_Id = new Pedido();
                    dp.Pedido_Id.Usuario = new Usuario();
                    dp.Pedido_Id.Usuario.Nombre = nombreUsuario;
                    detallePedidoList.Add(dp);
                }
                var pedido = await pedidoService.CrearPedidoAsync(detallePedidoList);
                // Puedes devolver un objeto JSON con la información que necesites
                var response = new { mensaje = "Pedido confirmado con éxito", pedidoId = pedido.Id };
                return Ok(response); // Devuelve un HTTP 200 OK con el objeto JSON
            }
            return null;
        }
    }

    public class ProductoPedido
    {
        public int IdProducto { get; set; }
        public int Cantidad { get; set; }
        public int Precio_unitario { get; set; }
    }

}
