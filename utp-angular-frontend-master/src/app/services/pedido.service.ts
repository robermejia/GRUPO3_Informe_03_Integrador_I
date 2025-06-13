import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { type Observable } from 'rxjs';
import { ApiPageResponse, ApiResponse } from '../models/api';
import {
  ListPedidoAdminDto,
  ListPedidoDto,
  PedidoDetailDto,
  StorePedidoDto,
} from '../models/pedido';

@Injectable({
  providedIn: 'root',
})
export class PedidoService {
  private apiUrl = 'http://localhost:8080/api/v1/pedido'; // Cambia según tu backend

  constructor(private http: HttpClient) {}

  getPedidos(
    pageNumber: number,
    pageSize: number,
    sortBy?: string,
    sortOrder?: string
  ): Observable<ApiPageResponse<ListPedidoDto>> {
    let params = new HttpParams()
      .set('page_number', pageNumber)
      .set('page_size', pageSize);
    if (!!sortBy) params = params.set('sort_by', sortBy);
    if (!!sortOrder) params = params.set('sort_order', sortOrder);
    return this.http.get<ApiPageResponse<ListPedidoDto>>(this.apiUrl, {
      params,
    });
  }

  getPedidosAdmin(
    pageNumber: number,
    pageSize: number,
    sortBy?: string,
    sortOrder?: string
  ): Observable<ApiPageResponse<ListPedidoAdminDto>> {
    let params = new HttpParams()
      .set('page_number', pageNumber)
      .set('page_size', pageSize);
    if (!!sortBy) params = params.set('sort_by', sortBy);
    if (!!sortOrder) params = params.set('sort_order', sortOrder);
    return this.http.get<ApiPageResponse<ListPedidoAdminDto>>(`${this.apiUrl}/admin`, {
      params,
    });
  }

  generarPedido(pedido: StorePedidoDto): Observable<ApiResponse<null>> {
    return this.http.post<ApiResponse<null>>(this.apiUrl, pedido);
  }

  verDetalles(idPedido: number): Observable<ApiResponse<PedidoDetailDto[]>> {
    return this.http.get<ApiResponse<PedidoDetailDto[]>>(
      `${this.apiUrl}/${idPedido}`
    );
  }

  pagar(idPedido: number): Observable<ApiResponse<null>> {
    return this.http.put<ApiResponse<null>>(
      `${this.apiUrl}/${idPedido}/pagar`,
      undefined
    );
  }

  completar(idPedido: number): Observable<ApiResponse<null>> {
    return this.http.put<ApiResponse<null>>(
      `${this.apiUrl}/${idPedido}/completar`,
      undefined
    );
  }

  anular(idPedido: number): Observable<ApiResponse<null>> {
    return this.http.delete<ApiResponse<null>>(
      `${this.apiUrl}/${idPedido}/anular`,
      undefined
    );
  }
}
