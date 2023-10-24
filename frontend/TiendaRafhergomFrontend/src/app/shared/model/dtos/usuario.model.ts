export interface Usuario {
  id: string;
  nombre: string;
  contrasena: string;
  rol: string;
}

export interface UsuarioView {
  id: string;
  nombre: string;
  contrasena: string;
  rol: string;
  parentUsuario: UsuarioView;
}

export interface UsuarioSimple {
  id: string;
  nombre: string;
  contrasena: string;
  rol: string;
}

