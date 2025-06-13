import { Routes } from '@angular/router';

export const adminRoutes: Routes = [
  {
    path: 'productos',
    loadComponent: () =>
      import('./producto-list/producto-list').then(
        (m) => m.ProductoListComponent
      ),
  },
  {
    path: 'pedidos',
    loadComponent: () =>
      import('./pedidos-list/pedidos-list').then((m) => m.PedidosList),
  },
  {
    path: 'clientes',
    loadComponent: () =>
      import('./clientes-list/clientes-list').then((m) => m.ClientesList),
  },
];
