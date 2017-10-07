package api;

import org.springframework.http.HttpStatus;

public class ApiResponseSuccess extends ApiResponse {
  private String message;

  public ApiResponseSuccess(HttpStatus status, String message) {
    super(status);
    this.message = message;
  }
}
