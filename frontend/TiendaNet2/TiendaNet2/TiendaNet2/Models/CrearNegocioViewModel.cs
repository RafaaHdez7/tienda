﻿namespace TiendaNet2.Models
{
    public class CrearNegocioViewModel
    {
        public IEnumerable<CategoriaNegocio> CategoriasNegocio { get; set; }
        public Negocio Negocio { get; set; }
        public Usuario Usuario { get; set; }
    }
}