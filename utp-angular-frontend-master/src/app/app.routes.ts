import { provideRouter, Routes } from '@angular/router';
import { adminGuard } from './guards/admin-guard';
import { Layout } from './pages/shared/layout/layout';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'productos',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login').then((m) => m.Login),
  },
  {
    path: 'admin',
    component: Layout,
    loadChildren: () =>
      import('./pages/admin/admin.routes').then((m) => m.adminRoutes),
    canActivate: [adminGuard],
  },
  {
    path: 'productos',
    loadComponent: () =>
      import('./pages/store/productos/productos').then((m) => m.Productos),
  },
  {
    path: 'pedidos',
    loadComponent: () =>
      import('./pages/store/pedidos/pedidos').then((m) => m.Pedidos),
  },
  {
    path: 'carrito-compras',
    loadComponent: () =>
      import('./pages/store/carrito-compras/carrito-compras').then(
        (m) => m.CarritoCompras
      ),
  },
];

export const appRouter = provideRouter(routes);
