export interface Pedido {
  id: string;
  usuarioId: string;
  usuarioNombre: string;
  estadoPedido: string;
  fechaHora: string;
}

export interface PedidoView {
  id: string;
  usuarioId: string;
  usuarioNombre: string;
  estadoPedido: string;
  fechaHora: string;
  parentPedido: PedidoView;
}

export interface PedidoSimple {
  id: string;
  usuarioId: string;
  usuarioNombre: string;
  estadoPedido: string;
  fechaHora: string;
}

