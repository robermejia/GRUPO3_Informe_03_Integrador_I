import { CommonModule, CurrencyPipe } from '@angular/common';
import {
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {
  MatPaginator,
  MatPaginatorModule,
  PageEvent,
} from '@angular/material/paginator';
import { RouterLink } from '@angular/router';
import { debounceTime, distinctUntilChanged, Subscription } from 'rxjs';
import { ProductoListDto } from '../../../models/producto.model';
import { CarritoComprasService } from '../../../services/carrito-compras.service';
import { ProductoService } from '../../../services/producto.service';
import { HeaderToolbar } from "../../shared/header-toolbar/header-toolbar";

@Component({
  selector: 'app-productos',
  imports: [
    CommonModule,
    RouterLink,
    CurrencyPipe,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    ReactiveFormsModule,
    HeaderToolbar
],
  templateUrl: './productos.html',
  styleUrl: './productos.css',
})
export class Productos implements OnInit, OnDestroy {
  private readonly subscription = new Subscription();

  isLoading = true;
  busqueda: string = '';
  resultsLength = 0;
  pageSize = 16;
  pageIndex = 0;

  products: ProductoListDto[] = [];

  busquedaControl = new FormControl('');

  @ViewChild(MatPaginator)
  readonly paginator!: MatPaginator;

  constructor(
    private productoService: ProductoService,
    private carritoComprasService: CarritoComprasService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.listProducts();
    this.subscription.add(
      this.busquedaControl.valueChanges
        .pipe(debounceTime(400), distinctUntilChanged())
        .subscribe((text) => {
          this.busqueda = text || '';
          this.listProducts();
        })
    );
  }

  handlePageEvent(e: PageEvent) {
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.listProducts();
  }

  listProducts() {
    this.subscription.add(
      this.productoService
        .getProductos(
          this.busqueda,
          this.pageIndex,
          this.pageSize,
          undefined,
          undefined
        )
        .subscribe({
          next: (res) => {
            this.isLoading = false;
            this.resultsLength = res.totalSize;
            this.products = res.data;
            this.cdr.detectChanges();
          },
        })
    );
  }

  addProduct(producto: ProductoListDto) {
    this.carritoComprasService.addProduct(producto, 1, true);
    window.alert('Producto agregado al carrito');
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
