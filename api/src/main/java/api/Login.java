package api;

import org.springframework.http.HttpStatus;

public class Login extends ApiResponse {
  private User user;
  public Login(HttpStatus status, User user) {
    super(status);
    this.user = user;
  }
}
