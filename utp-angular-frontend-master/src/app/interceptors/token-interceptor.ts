import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  if (req.url.includes('/api/v1/auth/login')) {
    return next(req);
  }

  let authService = inject(AuthService);
  const token = authService.getToken();
  if (token !== null) {
    return next(
      req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`),
      })
    );
  }

  return next(req);
};
