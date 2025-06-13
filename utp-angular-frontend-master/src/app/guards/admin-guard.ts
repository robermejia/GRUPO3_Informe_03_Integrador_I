import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const adminGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const user = authService.getUser();
  if (authService.getToken() === null || user === null) {
    router.navigate(['/login']);
    authService.logout();
    return false;
  }

  // Admin, vendedor
  if ([1, 2].includes(user.id_rol)) {
    return true;
  }

  router.navigate(['/login']);
  return false;
};
