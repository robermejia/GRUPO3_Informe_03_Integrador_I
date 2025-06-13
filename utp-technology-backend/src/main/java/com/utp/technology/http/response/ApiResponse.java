package com.utp.technology.http.response;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

  private boolean success;

  @Nullable
  private T data;

  @Nullable
  private String message;

  public static <T> ApiResponse<T> notFound() {
    return new ApiResponse<T>(false, null, "No se encontró el recurso");
  }

  public static <T> ApiResponse<T> notFound(String message) {
    return new ApiResponse<T>(false, null, message);
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<T>(true, data, null);
  }

  public static <T> ApiResponse<T> success(T data, String message) {
    return new ApiResponse<T>(true, data, message);
  }

  public static <T> ApiResponse<T> badRequest(T data, String message) {
    return new ApiResponse<T>(false, data, message);
  }

}
