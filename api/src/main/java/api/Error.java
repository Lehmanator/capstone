package api;

import org.springframework.http.HttpStatus;

public class Error extends ApiResponse{
  private HttpStatus status;
  private String message;

  public Error(HttpStatus status, String message) {
    super(status);
    this.message = message;
  }
}
