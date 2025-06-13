import {
  provideHttpClient,
  withFetch,
  withInterceptors,
} from '@angular/common/http';
import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
  provideZonelessChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { MatPaginatorIntl } from '@angular/material/paginator';
import { routes } from './app.routes';
import { tokenExpiredInterceptor } from './interceptors/token-expired-interceptor';
import { tokenInterceptor } from './interceptors/token-interceptor';
import { getSpanishPaginatorIntl } from './intl/spanish-paginator-intl';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZonelessChangeDetection(),
    provideRouter(routes),
    provideHttpClient(
      withFetch(),
      withInterceptors([tokenInterceptor, tokenExpiredInterceptor])
    ),
    { provide: MatPaginatorIntl, useValue: getSpanishPaginatorIntl() },
  ],
};
