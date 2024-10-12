using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using System.Text;
using TiendaNet2.Controllers;
using TiendaNet2.Servicios;

var builder = WebApplication.CreateBuilder(args);

// Obtener configuración
var configuration = builder.Configuration;

// Add services to the container.
builder.Services.AddControllersWithViews();
// Agregar servicio de sesión
builder.Services.AddSession(options =>
{
    // Configura las opciones de la sesión si es necesario
});
builder.Services.AddTransient<INegocioService, NegocioService>();
builder.Services.AddTransient<IUsuarioService, UsuarioService>();
builder.Services.AddTransient<IPedidoService, PedidoService>();
builder.Services.AddTransient<ICategoriaNegocioService, CategoriaNegocioService>();
builder.Services.AddTransient<ICategoriaProductoService, CategoriaProductoService>();
builder.Services.AddTransient<IProductoService, ProductoService>();
builder.Services.AddTransient<IDetallePedidoService, DetallePedidoService>();
builder.Services.AddTransient<IMonederoService, MonederoService>();
builder.Services.AddTransient<IHistoricoTransaccionesService, HistoricoTransaccionesService>();
builder.Services.AddHttpClient();
builder.Services.AddHttpClient<UsuarioService>();
builder.Services.AddHttpClient<NegocioService>();
builder.Services.AddHttpClient<PedidoService>();
builder.Services.AddHttpClient<ProductoService>();
builder.Services.AddHttpClient<DetallePedidoService>();
builder.Services.AddHttpClient<CategoriaProductoService>();
builder.Services.AddHttpClient<CategoriaNegocioService>();
builder.Services.AddHttpClient<MonederoService>();
builder.Services.AddHttpClient<HistoricoTransaccionesService>();
builder.Services.AddScoped<AuthTokenViewComponent>();

builder.Services.AddAuthorization(options =>
{
    options.AddPolicy("RequireAdminRole", policy =>
    {
        policy.RequireAuthenticatedUser();
        policy.RequireRole("admin");
    });
    options.AddPolicy("RequireUserRole", policy =>
    {
        policy.RequireAuthenticatedUser();
        policy.RequireRole("user");
    });
    options.AddPolicy("RequireNegocioRole", policy =>
    {
        policy.RequireAuthenticatedUser();
        policy.RequireRole("negocio");
    });
});

builder.Services.AddAuthentication(options =>
{
    options.DefaultScheme = JwtBearerDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
})
.AddJwtBearer(options =>
{
    options.TokenValidationParameters = new TokenValidationParameters
    {
        ValidateIssuer = true,
        ValidateAudience = true,
        ValidateIssuerSigningKey = true,
        ValidIssuer = configuration["Jwt:Issuer"],
        ValidAudience = configuration["Jwt:Audience"],
        IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(configuration["Jwt:SecretKey"])),
        ClockSkew = TimeSpan.Zero
    };
});

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();
app.UseAuthentication();
app.UseAuthorization();
app.UseSession();

app.UseEndpoints(endpoints =>
{
    endpoints.MapControllerRoute(
        name: "default",
        pattern: "{controller=Home}/{action=Index}/{id?}");

    endpoints.MapControllerRoute(
        name: "resumenPedido",
        pattern: "Pedido/ResumenPedido/{pedidoId}",
        defaults: new { controller = "Pedido", action = "ResumenPedido" });
});

app.Run();
