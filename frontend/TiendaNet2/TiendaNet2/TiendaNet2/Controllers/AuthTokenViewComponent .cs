using Microsoft.AspNetCore.Mvc;

namespace TiendaNet2.Controllers
{
    public class AuthTokenViewComponent : ViewComponent
    {
        public IViewComponentResult Invoke()
        {
            var authToken = HttpContext.Items["AuthToken"] as string;
            ViewBag.AuthToken = authToken;
            return View();
        }
    }
}