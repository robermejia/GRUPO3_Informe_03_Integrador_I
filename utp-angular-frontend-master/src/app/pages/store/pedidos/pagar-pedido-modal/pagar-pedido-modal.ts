import { DialogRef } from '@angular/cdk/dialog';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { ListPedidoDto, PedidoDetailDto } from '../../../../models/pedido';
import { PedidoService } from '../../../../services/pedido.service';

export type PagarPedidoModalData = {
  pedido: ListPedidoDto;
  detalles: PedidoDetailDto[];
};

@Component({
  selector: 'app-pagar-pedido-modal',
  imports: [CurrencyPipe, DatePipe, MatButtonModule, MatDialogModule],
  templateUrl: './pagar-pedido-modal.html',
  styleUrl: './pagar-pedido-modal.css',
})
export class PagarPedidoModal {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: PagarPedidoModalData,
    private dialogRed: DialogRef,
    private pedidoService: PedidoService
  ) {}

  pagar() {
    this.pedidoService.pagar(this.data.pedido.id).subscribe({
      next: (res) => {
        window.alert(res.message);
        this.dialogRed.close(true);
      },
    });
  }
}
