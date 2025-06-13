import { Injectable } from '@angular/core';
import { CarritoComprasType } from '../models/carrito-compras';
import { ProductoListDto } from '../models/producto.model';

@Injectable({
  providedIn: 'root',
})
export class CarritoComprasService {
  constructor() {}

  private setCarrito(carrito: CarritoComprasType) {
    localStorage.setItem('carrito', JSON.stringify(carrito));
  }

  limpiarCarrito(): void {
    localStorage.removeItem('carrito');
  }

  getCarrito(): CarritoComprasType {
    let carrito: CarritoComprasType;
    const carritoJsonString = localStorage.getItem('carrito');
    if (carritoJsonString != null) {
      carrito = JSON.parse(carritoJsonString);
    } else {
      carrito = { products: [] };
      this.setCarrito(carrito);
    }
    return carrito;
  }

  addProduct(
    product: ProductoListDto,
    quantity: number,
    addProduct: boolean = false
  ): void {
    let carrito: CarritoComprasType = this.getCarrito();

    let saved = false;
    if (carrito.products.length > 0) {
      const index = carrito.products.findIndex(
        (p) => p.product.id === product.id
      );
      if (index !== -1) {
        if (addProduct) {
          carrito.products[index].quantity += quantity;
        } else {
          carrito.products[index].quantity = quantity;
        }

        saved = true;
      }
    }
    if (!saved) {
      carrito.products.push({ product, quantity });
    }

    this.setCarrito(carrito);
  }

  removeProduct(product: ProductoListDto): void {
    let carrito: CarritoComprasType = this.getCarrito();
    if (carrito.products.length === 0) return;
    const index = carrito.products.findIndex(
      (p) => p.product.id === product.id
    );
    if (index !== -1) {
      carrito.products.splice(index, 1);
    }
    this.setCarrito(carrito);
  }
}
