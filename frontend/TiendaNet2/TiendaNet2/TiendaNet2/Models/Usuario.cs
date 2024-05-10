using System.ComponentModel.DataAnnotations;

namespace TiendaNet2.Models
{
    public class Usuario
    {
        public int Id { get; set; }
        [Display(Name = "Nombre de usuario")]
        [Required(ErrorMessage = "El campo {0} es requerido")]
        [StringLength(12, MinimumLength = 4, ErrorMessage = "La longitud del campo {0} debe estar entre {2} y {1} caracteres")]
        public string Nombre { get; set; }
        public string Rol { get; set; }
        [Display(Name = "Contraseña")]
        [Required(ErrorMessage = "El campo {0} es requerido")]
        [DataType(DataType.Password)]
        public string Contrasena { get; set; }
        [Display(Name = "Email")]
        [Required(ErrorMessage = "El campo {0} es requerido")]
        [DataType(DataType.EmailAddress)]
        public string Email { get; set; }
    }
}