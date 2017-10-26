package api.response;

import org.springframework.http.HttpStatus;

public class Error extends ApiResponse{
  private HttpStatus status;
  private String message;

  public Error(HttpStatus status, String message) {
    super(message, status);
    this.message = message;
  }

  @Override
  public HttpStatus getStatus() {
    return status;
  }

  @Override
  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
