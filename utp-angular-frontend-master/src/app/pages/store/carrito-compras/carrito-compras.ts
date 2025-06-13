import { CurrencyPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Router, RouterLink } from '@angular/router';
import {
  CarritoComprasType,
  CarritoProduct,
} from '../../../models/carrito-compras';
import { AuthService } from '../../../services/auth.service';
import { CarritoComprasService } from '../../../services/carrito-compras.service';
import { HeaderToolbar } from '../../shared/header-toolbar/header-toolbar';
import { StorePedidoDto } from '../../../models/pedido';
import { PedidoService } from '../../../services/pedido.service';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-carrito-compras',
  imports: [
    CurrencyPipe,
    RouterLink,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    HeaderToolbar,
  ],
  templateUrl: './carrito-compras.html',
  styleUrl: './carrito-compras.css',
})
export class CarritoCompras implements OnInit {
  carritoCompras!: CarritoComprasType;
  isLogged = false;

  columns = ['id', 'imagen', 'nombre', 'cantidad', 'precio', 'subtotal'];

  readonly dataSource = new MatTableDataSource<CarritoProduct>();

  constructor(
    private router: Router,
    private carritoComprasService: CarritoComprasService,
    private pedidoService: PedidoService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadData();
    this.isLogged = this.authService.getToken() !== null;
  }

  loadData(): void {
    this.carritoCompras = this.carritoComprasService.getCarrito();
    this.dataSource.data = this.carritoCompras.products;
  }

  getTotal() {
    let total = 0;
    for (let product of this.carritoCompras.products) {
      total += this.getSubTotal(product);
    }
    return total;
  }

  getSubTotal(product: CarritoProduct) {
    return product.product.precio * product.quantity;
  }

  quantityAdd(product: CarritoProduct) {
    const value = product.quantity + 1;
    if (value <= 0) {
      this.carritoComprasService.removeProduct(product.product);
    } else {
      this.carritoComprasService.addProduct(product.product, value);
    }
    this.loadData();
  }

  quantitySub(product: CarritoProduct) {
    const value = product.quantity - 1;
    if (value <= 0) {
      this.carritoComprasService.removeProduct(product.product);
    } else {
      this.carritoComprasService.addProduct(product.product, value);
    }
    this.loadData();
  }

  comprar() {
    let dataPedido: StorePedidoDto = {
      detalles: [],
    };

    dataPedido.detalles = this.carritoCompras.products.map((item) => {
      return {
        id_producto: item.product.id,
        cantidad: item.quantity,
      };
    });

    this.pedidoService.generarPedido(dataPedido).subscribe({
      next: (res) => {
        if (res.success) {
          alert(res.message);
          this.carritoComprasService.limpiarCarrito();
          this.router.navigate(['/pedidos']);
        }
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
}
