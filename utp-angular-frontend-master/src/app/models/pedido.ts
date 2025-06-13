export type ListPedidoDto = {
  id: number;
  fecha: string;
  estado: string;
  total: number;
};

export type ListPedidoAdminDto = {
  id: number;
  cliente_id: number;
  nombre_cliente: string;
  dni: string;
  usuario: string;
  fecha: string;
  estado: string;
  total: number;
};

export type PedidoDetailDto = {
  id: number;
  pedido_id: number;
  producto_id: number;
  producto: string;
  imagen_producto: string;
  cantidad: number;
  precio_unitario: number;
  subtotal: number;
};

export type StorePedidoDto = {
  detalles: StorePedidoProductDto[];
};

export type StorePedidoProductDto = {
  id_producto: number;
  cantidad: number;
};
