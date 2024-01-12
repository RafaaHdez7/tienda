export interface Categoria {
  id: string;
  nombreCategoria: string;
  descripcion: string;
}

export interface CategoriaView {
  id: string;
  nombreCategoria: string;
  descripcion: string;
  parentCategoria: CategoriaView;
}

export interface CategoriaSimple {
  id: string;
  nombreCategoria: string;
  descripcion: string;
}

