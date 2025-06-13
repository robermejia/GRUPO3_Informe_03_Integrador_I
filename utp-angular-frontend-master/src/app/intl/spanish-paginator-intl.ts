import { MatPaginatorIntl } from '@angular/material/paginator';

const spanishRangeLabel = (
  page: number,
  pageSize: number,
  length: number
): string => {
  if (length === 0 || pageSize === 0) {
    return `0 de ${length}`;
  }

  length = Math.max(length, 0);
  const startIndex = page * pageSize;
  let endIndex: number;
  if (startIndex < length) {
    endIndex = Math.min(startIndex + pageSize, length);
  } else {
    endIndex = startIndex + pageSize;
  }
  return `${startIndex + 1} - ${endIndex} de ${length}`;
};

export function getSpanishPaginatorIntl() {
  const paginatorInt = new MatPaginatorIntl();
  paginatorInt.itemsPerPageLabel = 'Registros por página:';
  paginatorInt.nextPageLabel = 'Siguiente página';
  paginatorInt.previousPageLabel = 'Página anterior';
  paginatorInt.firstPageLabel = 'Primera página';
  paginatorInt.lastPageLabel = 'Última página';
  paginatorInt.getRangeLabel = spanishRangeLabel;

  return paginatorInt;
}
