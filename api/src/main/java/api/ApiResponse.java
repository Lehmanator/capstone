package api;

import org.springframework.http.HttpStatus;

public class ApiResponse {
  private HttpStatus status;

  public ApiResponse(HttpStatus status) {
    this.status = status;
  }
}
