package api.response;

import org.springframework.http.HttpStatus;

public class ApiResponseSuccess extends ApiResponse {

  public ApiResponseSuccess(HttpStatus status, String message) {
    super(message, status);
//    this.message = message;
  }
}
