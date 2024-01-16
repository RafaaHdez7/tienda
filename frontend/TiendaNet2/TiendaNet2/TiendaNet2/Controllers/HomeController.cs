using Microsoft.AspNetCore.Mvc;
using TiendaNet2.Servicios;
using System.Diagnostics;
using TiendaNet2.Models;
using System.Reflection;
using Microsoft.AspNetCore.Authorization;

namespace TiendaNet2.Controllers
{
    [AllowAnonymous]
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly INegocioService negocioService;

        public HomeController(ILogger<HomeController> logger, INegocioService negocioService )
        {
            this.negocioService = negocioService;
            _logger = logger;
        }
        [AllowAnonymous]
        public async Task<IActionResult> Index()
        {
            var negocios = await negocioService.ObtenerNegociosAsync();
            var modelo = new HomeIndexViewModel();
            // Asegurarse de que negocios no sea null
            if (negocios != null)
            {
                // Tomar los primeros 3 negocios
                var negociosTomados = negocios.Take(3).ToList();

                modelo = new HomeIndexViewModel()
                {
                    Negocios = negociosTomados
                };
            }
            return View(modelo);
        }

        [AllowAnonymous]
        public async Task<IActionResult> Negocios()
        {
            var modelo = new HomeIndexViewModel();
            var negocios = await negocioService.ObtenerNegociosAsync();
            // Asegurarse de que negocios no sea null
            if (negocios != null)
            {
                // Tomar los primeros 3 negocios
                var negociosTomados = negocios.Take(3).ToList();

                modelo = new HomeIndexViewModel()
                {
                    Negocios = negociosTomados
                };
            }
            return View(modelo);
        }
        [AllowAnonymous]
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