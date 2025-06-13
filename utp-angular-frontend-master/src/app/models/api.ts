export type ApiResponse<T> = {
  success: boolean;
  data: T;
  message: string | null;
};


export type ApiPageResponse<T> = {
  success: boolean;
  data: T[];
  currentPage: number;
  currentSize: number;
  totalPages: number;
  totalSize: number;
  message: string | null;
};
