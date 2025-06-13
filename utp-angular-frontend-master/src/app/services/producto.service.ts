import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiPageResponse, ApiResponse } from '../models/api';
import {
  ProductoListDto,
  ProductoStoreDto,
  ProductoUpdateDto
} from '../models/producto.model';

@Injectable({
  providedIn: 'root',
})
export class ProductoService {
  private apiUrl = 'http://localhost:8080/api/v1/producto'; // Cambia según tu backend

  constructor(private http: HttpClient) {}

  getProductos(
    nombre: string,
    pageNumber: number,
    pageSize: number,
    sortBy?: string,
    sortOrder?: string
  ): Observable<ApiPageResponse<ProductoListDto>> {
    let params = new HttpParams()
      .set('page_number', pageNumber)
      .set('page_size', pageSize);
    if (nombre.length > 0) params = params.set('nombre', nombre.trim());
    if (!!sortBy) params = params.set('sort_by', sortBy);
    if (!!sortOrder) params = params.set('sort_order', sortOrder);

    return this.http.get<ApiPageResponse<ProductoListDto>>(this.apiUrl, {
      params,
    });
  }

  addProducto(producto: ProductoStoreDto): Observable<ApiResponse<null>> {
    return this.http.post<ApiResponse<null>>(this.apiUrl, producto);
  }

  deleteProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateProducto(
    id: number,
    producto: ProductoUpdateDto
  ): Observable<ApiResponse<null>> {
    return this.http.put<ApiResponse<null>>(
      `${this.apiUrl}/${id}`,
      producto
    );
  }
}
