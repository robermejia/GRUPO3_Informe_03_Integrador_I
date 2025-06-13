import { CurrencyPipe, DatePipe } from '@angular/common';
import { AfterViewInit, Component, OnDestroy, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import {
  MatPaginator,
  MatPaginatorModule,
  PageEvent,
} from '@angular/material/paginator';
import { MatSortModule, Sort } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatTooltip } from '@angular/material/tooltip';
import { startWith, Subscription, switchMap } from 'rxjs';
import { ListPedidoDto } from '../../../models/pedido';
import { PedidoService } from '../../../services/pedido.service';
import { HeaderToolbar } from '../../shared/header-toolbar/header-toolbar';
import {
  DetallePedidoModal,
  DetallePedidoModalData,
} from './detalle-pedido-modal/detalle-pedido-modal';
import { PagarPedidoModal } from './pagar-pedido-modal/pagar-pedido-modal';

@Component({
  selector: 'app-pedidos',
  imports: [
    CurrencyPipe,
    DatePipe,
    MatIconModule,
    MatTooltip,
    MatButtonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    HeaderToolbar,
  ],
  templateUrl: './pedidos.html',
  styleUrl: './pedidos.css',
})
export class Pedidos implements AfterViewInit, OnDestroy {
  private readonly subscription = new Subscription();
  isLoading = true;
  resultsLength = 0;
  sortOrder?: string;
  sortBy?: string;

  columns = ['id', 'fecha', 'estado', 'total', 'acciones'];

  readonly dataSource = new MatTableDataSource<ListPedidoDto>();

  @ViewChild(MatPaginator)
  readonly paginator!: MatPaginator;

  constructor(
    private pedidoService: PedidoService,
    private dialog: MatDialog
  ) {}

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
    return this.pedidoService.getPedidos(
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

  verDetallesPedido(pedido: ListPedidoDto): void {
    this.pedidoService.verDetalles(pedido.id).subscribe({
      next: (res) => {
        this.dialog.open<DetallePedidoModal, DetallePedidoModalData>(
          DetallePedidoModal,
          { data: { pedido, detalles: res.data } }
        );
      },
    });
  }

  pagarPedido(pedido: ListPedidoDto): void {
    this.pedidoService.verDetalles(pedido.id).subscribe({
      next: (res) => {
        this.dialog
          .open<PagarPedidoModal>(PagarPedidoModal, {
            data: { pedido, detalles: res.data },
          })
          .afterClosed()
          .subscribe((res) => {
            this.paginator.page.emit();
          });
      },
    });
  }

  anularPedido(pedido: ListPedidoDto): void {
    let confirm = window.confirm('¿Seguro de anular el pedido?');
    if (!confirm) return;
    this.pedidoService.anular(pedido.id).subscribe({
      next: (res) => {
        window.alert(res.message);
        this.paginator.page.emit();
      },
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
