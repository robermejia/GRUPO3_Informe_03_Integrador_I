import { AfterViewInit, Component, OnDestroy, ViewChild } from '@angular/core';
import {
  MatPaginator,
  MatPaginatorModule,
  PageEvent,
} from '@angular/material/paginator';
import { MatSortModule, Sort } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { startWith, Subscription, switchMap } from 'rxjs';
import { ListPedidoAdminDto } from '../../../models/pedido';
import { PedidoService } from '../../../services/pedido.service';
import { CurrencyPipe, DatePipe } from '@angular/common';

@Component({
  selector: 'app-pedidos-list',
  imports: [
    DatePipe,
    CurrencyPipe,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
  ],
  templateUrl: './pedidos-list.html',
  styleUrl: './pedidos-list.css',
})
export class PedidosList implements AfterViewInit, OnDestroy {
  private readonly subscription = new Subscription();
  isLoading = true;
  resultsLength = 0;
  sortOrder?: string;
  sortBy?: string;

  columns = [
    'id',
    'nombre_cliente',
    'dni',
    'fecha',
    'estado',
    'total',
    // 'acciones',
  ];

  readonly dataSource = new MatTableDataSource<ListPedidoAdminDto>();

  @ViewChild(MatPaginator)
  readonly paginator!: MatPaginator;

  constructor(private pedidoService: PedidoService) {}

  ngAfterViewInit(): void {
    this.subscription.add(
      this.paginator.page
        .pipe(
          startWith({} as PageEvent),
          switchMap(() => {
            this.isLoading = true;
            return this.cargarPedidos();
          })
        )
        .subscribe({
          next: (res) => {
            this.isLoading = false;
            this.resultsLength = res.totalSize;
            this.dataSource.data = res.data;
          },
        })
    );
  }

  cargarPedidos() {
    return this.pedidoService.getPedidosAdmin(
      this.paginator.pageIndex,
      this.paginator.pageSize,
      this.sortBy,
      this.sortOrder
    );
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction === '' || sortState.active === 'id') {
      this.sortOrder =
        sortState.direction === '' ? undefined : sortState.direction;
      this.sortBy = undefined;
    } else {
      this.sortOrder = sortState.direction;
      this.sortBy = sortState.active;
    }
    this.paginator.page.emit();
  }

  emitPageEvent() {
    this.paginator.pageIndex = 0;
    this.paginator.page.emit();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
