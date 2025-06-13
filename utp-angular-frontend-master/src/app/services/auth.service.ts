import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from '../models/api';
import { LoggedUser, LoginResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth'; // Cambia según tu backend
  private tokenKey = 'token';

  constructor(private http: HttpClient) {}

  login(
    email: string,
    password: string
  ): Observable<ApiResponse<LoginResponse>> {
    return this.http.post<ApiResponse<LoginResponse>>(`${this.apiUrl}/login`, {
      email,
      password,
    });
  }

  loginExitoso(data: LoginResponse) {
    localStorage.setItem(this.tokenKey, data.access_token);
    localStorage.setItem('user', JSON.stringify(data.user));
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getUser(): LoggedUser | null {
    let userJsonString = localStorage.getItem('user');
    if (userJsonString === null) return null;
    return JSON.parse(userJsonString) as LoggedUser;
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem('user');
  }
}
