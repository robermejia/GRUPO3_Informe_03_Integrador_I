import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PagarPedidoModal } from './pagar-pedido-modal';

describe('PagarPedidoModal', () => {
  let component: PagarPedidoModal;
  let fixture: ComponentFixture<PagarPedidoModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PagarPedidoModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PagarPedidoModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
