using Microsoft.AspNetCore.Mvc;
using TiendaNet2.Servicios;
using System.Diagnostics;
using TiendaNet2.Models;

namespace TiendaNet2.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly INegociosRepository negociosRepository;

        public HomeController(ILogger<HomeController> logger, INegociosRepository negociosRepository )
        {
            this.negociosRepository = negociosRepository;
            _logger = logger;
        }

        public IActionResult Index()
        {
            var negocios = negociosRepository.ObtenerNegocios().Take(3).ToList();

            var modelo = new HomeIndexViewModel()
            {
                Negocios = negocios
            };
            return View(modelo);
        }

        public IActionResult Negocios()
        {
            var negocios = negociosRepository.ObtenerNegocios();
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