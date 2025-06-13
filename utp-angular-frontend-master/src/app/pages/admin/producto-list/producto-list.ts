import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import {
  MatPaginator,
  MatPaginatorModule,
  PageEvent,
} from '@angular/material/paginator';
import { MatSortModule, Sort } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import {
  debounceTime,
  distinctUntilChanged,
  startWith,
  Subject,
  Subscription,
  switchMap,
} from 'rxjs';
import { ProductoListDto } from '../../../models/producto.model';
import { ProductoService } from '../../../services/producto.service';
import {
  ProductoModal,
  ProductoModalData,
} from './producto-modal/producto-modal';

@Component({
  standalone: true,
  selector: 'app-producto-list',
  imports: [
    CommonModule,
    MatInputModule,
    MatFormFieldModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatPaginatorModule,
    MatSortModule,
  ],
  templateUrl: './producto-list.html',
  styleUrls: ['./producto-list.css'],
})
export class ProductoListComponent implements OnInit, AfterViewInit, OnDestroy {
  private readonly subscription = new Subscription();
  isLoading = true;
  resultsLength = 0;
  sortOrder?: string;
  sortBy?: string;

  private searchValue = '';
  private readonly searchNombre = new Subject<string>();

  columns = ['id', 'imagen', 'nombre', 'precio', 'stock', 'acciones'];

  readonly dataSource = new MatTableDataSource<ProductoListDto>();

  @ViewChild(MatPaginator)
  readonly paginator!: MatPaginator;

  constructor(
    private dialog: MatDialog,
    private productoService: ProductoService
  ) {}

  ngOnInit(): void {
    this.subscription.add(
      this.searchNombre
        .pipe(debounceTime(400), distinctUntilChanged())
        .subscribe((_text) => {
          this.emitPageEvent();
        })
    );
  }

  ngAfterViewInit(): void {
    this.subscription.add(
      this.paginator.page
        .pipe(
          startWith({} as PageEvent),
          switchMap(() => {
            this.isLoading = true;
            return this.cargarProductos();
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

  onSearch(e: Event): void {
    this.searchValue = (e.target as HTMLInputElement).value;
    this.searchNombre.next(this.searchValue);
  }

  cargarProductos() {
    return this.productoService.getProductos(
      this.searchValue,
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

  eliminarProducto(id: number): void {
    if (confirm('¿Estás seguro de eliminar este producto?')) {
      this.productoService.deleteProducto(id).subscribe(() => {
        this.emitPageEvent();
      });
    }
  }

  editarProducto(producto: ProductoListDto) {
    const dialogRef = this.dialog.open<ProductoModal, ProductoModalData>(
      ProductoModal,
      {
        data: {
          producto,
        },
      }
    );

    dialogRef.afterClosed().subscribe({
      next: (res) => {
        if (res === undefined) return;
        this.productoService.updateProducto(producto.id, res).subscribe({
          next: (res) => {
            if (res.success) {
              window.alert(res.message);
            }
          },
          error: (err) => {
            if (err instanceof HttpErrorResponse) {
              window.alert(`Error: ${err.error.message}`);
            }
          },
        });
      },
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
