package com.utp.technology.http.response;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import lombok.Data;

@Data
public class ApiPageResponse<T> {
  private boolean success;

  @Nullable
  private List<T> data;

  @Nullable
  private String message;

  private Integer currentPage;
  private Integer currentSize;

  private Long totalSize;
  private Integer totalPages;

  public ApiPageResponse(boolean success, Page<T> data, String message) {
    this.success = success;
    if (data != null) {
      this.data = data.getContent();
      this.currentPage = data.getNumber();
      this.currentSize = data.getNumberOfElements();
      this.totalPages = data.getTotalPages();
      this.totalSize = data.getTotalElements();
    }
    this.message = message;
  }

  public static <T> ApiPageResponse<T> success(Page<T> data) {
    return new ApiPageResponse<T>(true, data, null);
  }

  public static <T> ApiPageResponse<T> badRequest(String message) {
    return new ApiPageResponse<T>(true, null, message);
  }
}
