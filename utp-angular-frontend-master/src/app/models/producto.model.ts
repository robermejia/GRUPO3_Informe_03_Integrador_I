export interface Producto {
  id: number;
  nombre: string;
  imagen: string;
  precio: number;
  stock: number;
  categoria: {
    id: number;
    nombre: string;
  };
}

export interface ProductoListDto {
  id: number;
  nombre: string;
  imagen: string;
  precio: number;
  stock: number;
}

export interface ProductoStoreDto {
  nombre: string;
  imagen: string;
  precio: number;
  stock: number;
}

export interface ProductoUpdateDto {
  nombre: string | null;
  imagen: string | null;
  precio: number | null;
  stock: number | null;
}
