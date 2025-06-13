import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetallePedidoModal } from './detalle-pedido-modal';

describe('DetallePedidoModal', () => {
  let component: DetallePedidoModal;
  let fixture: ComponentFixture<DetallePedidoModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetallePedidoModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetallePedidoModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
