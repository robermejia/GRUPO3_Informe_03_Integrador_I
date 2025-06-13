import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { type Observable } from 'rxjs';
import { ApiPageResponse } from '../models/api';
import { ListClienteDto } from '../models/cliente';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  private apiUrl = 'http://localhost:8080/api/v1/clientes'; // Cambia según tu backend

  constructor(private http: HttpClient) {}

  getClientes(
    busqueda: string | undefined,
    pageNumber: number,
    pageSize: number,
    sortBy?: string,
    sortOrder?: string
  ): Observable<ApiPageResponse<ListClienteDto>> {
    let params = new HttpParams()
      .set('page_number', pageNumber)
      .set('page_size', pageSize);
    if (!!sortBy) params = params.set('sort_by', sortBy);
    if (!!sortOrder) params = params.set('sort_order', sortOrder);
    if (!!busqueda) params = params.set('busqueda', busqueda);
    return this.http.get<ApiPageResponse<ListClienteDto>>(this.apiUrl, {
      params,
    });
  }
}
