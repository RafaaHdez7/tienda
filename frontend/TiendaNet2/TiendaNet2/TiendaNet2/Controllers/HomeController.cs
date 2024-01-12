using Microsoft.AspNetCore.Mvc;
using TiendaNet2.Servicios;
using System.Diagnostics;
using TiendaNet2.Models;

namespace TiendaNet2.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly INegocioService negocioService;

        public HomeController(ILogger<HomeController> logger, INegocioService negocioService )
        {
            this.negocioService = negocioService;
            _logger = logger;
        }

        public async Task<IActionResult> Index()
        {
            var negocios = await negocioService.ObtenerNegociosAsync();

            // Tomar los primeros 3 negocios
            var negociosTomados = negocios.Take(3).ToList();

            var modelo = new HomeIndexViewModel()
            {
                Negocios = negociosTomados
            };

            return View(modelo);
        }

        public async Task<IActionResult> Negocios()
        {

            var negocios = await negocioService.ObtenerNegociosAsync();

            // Tomar los primeros 3 negocios
            var negociosTomados = negocios.Take(3).ToList();
            return View(negocios);
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}