package api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

public class ApiResponse extends ResponseEntity{
  private HttpStatus status;

  public ApiResponse(Object body, HttpStatus status) {
    super(body, status);
  }

//  public ApiResponse(HttpStatus status) {
//    this.status = status;
//  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }
}
