import { type ProductoListDto } from './producto.model';

export type CarritoComprasType = {
  products: CarritoProduct[];
};

export type CarritoProduct = {
  product: ProductoListDto;
  quantity: number;
};
