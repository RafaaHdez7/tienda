export interface Producto {
  id: string;
  nombreProducto: string;
  descripcion: string;
  precio: string;
  stockDisponible: string;
  categoriaId: string;
}

export interface ProductoView {
  id: string;
  nombreProducto: string;
  descripcion: string;
  precio: string;
  stockDisponible: string;
  categoriaId: string;
  parentProducto: ProductoView;
}

export interface ProductoSimple {
  id: string;
  nombreProducto: string;
  descripcion: string;
  precio: string;
  stockDisponible: string;
  categoriaId: string;
}

