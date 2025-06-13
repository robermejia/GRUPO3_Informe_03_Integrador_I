import { CurrencyPipe, DatePipe } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { ListPedidoDto, PedidoDetailDto } from '../../../../models/pedido';

export type DetallePedidoModalData = {
  pedido: ListPedidoDto;
  detalles: PedidoDetailDto[];
};

@Component({
  selector: 'app-detalle-pedido-modal',
  imports: [CurrencyPipe, DatePipe, MatButtonModule, MatDialogModule],
  templateUrl: './detalle-pedido-modal.html',
  styleUrl: './detalle-pedido-modal.css',
})
export class DetallePedidoModal {
  constructor(@Inject(MAT_DIALOG_DATA) public data: DetallePedidoModalData) {}
}
