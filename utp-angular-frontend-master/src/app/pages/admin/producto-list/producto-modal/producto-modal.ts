import { Component, Inject, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {
  ProductoListDto,
  ProductoStoreDto,
  ProductoUpdateDto,
} from '../../../../models/producto.model';

export type ProductoModalData = {
  producto?: ProductoListDto;
};

export type ProductoModalDataClosed = {
  producto: ProductoStoreDto | ProductoUpdateDto;
};

@Component({
  selector: 'app-producto-modal',
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
  ],
  templateUrl: './producto-modal.html',
  styleUrl: './producto-modal.css',
})
export class ProductoModal implements OnInit {
  title = '';
  formGroup = new FormGroup({
    nombre: new FormControl('', Validators.required),
    imagen: new FormControl('', Validators.required),
    precio: new FormControl<number>(0, Validators.required),
    stock: new FormControl<number>(0, Validators.required),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) private data: ProductoModalData,
    private dialogRef: MatDialogRef<ProductoModal, ProductoModalDataClosed>
  ) {}

  ngOnInit(): void {
    if (this.data.producto !== undefined) {
      const producto = this.data.producto;
      this.title = `Editando Producto N°${producto.id}`;
      this.formGroup.controls['nombre'].setValue(producto.nombre);
      this.formGroup.controls['imagen'].setValue(producto.imagen);
      this.formGroup.controls['precio'].setValue(producto.precio);
      this.formGroup.controls['stock'].setValue(producto.stock);
    } else {
      this.title = 'Nuevo Producto';
    }
  }

  guardar() {
    if (this.formGroup.invalid) return;
    let producto: ProductoStoreDto | ProductoUpdateDto;
    if (!!this.data.producto) {
      producto = {
        nombre: this.formGroup.value.nombre!,
        imagen: this.formGroup.value.imagen!,
        precio: this.formGroup.value.precio!,
        stock: this.formGroup.value.stock!,
      };
    } else {
      producto = {
        nombre: this.formGroup.value.nombre ?? null,
        imagen: this.formGroup.value.imagen ?? null,
        precio: this.formGroup.value.precio ?? null,
        stock: this.formGroup.value.stock ?? null,
      };
    }
    this.dialogRef.close({
      producto,
    });
  }
}
