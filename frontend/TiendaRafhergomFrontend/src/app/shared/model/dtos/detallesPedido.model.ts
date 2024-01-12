export interface DetallesPedido {
  id: string;
  pedidoId: string;
  productoId: string;
  cantidad: string;
  precioUnitario: string;
}

export interface DetallesPedidoView {
  id: string;
  pedidoId: string;
  productoId: string;
  cantidad: string;
  precioUnitario: string;
  parentDetallesPedido: DetallesPedidoView;
}

export interface DetallesPedidoSimple {
  id: string;
  pedidoId: string;
  productoId: string;
  cantidad: string;
  precioUnitario: string;
}

