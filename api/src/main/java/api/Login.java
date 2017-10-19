package api;

import org.springframework.http.HttpStatus;

public class Login extends ApiResponse {
  private String firstname;
  private String lastname;
  private String username;
  private String profilePicUrl;
  private String sessionKey;

  public Login(HttpStatus status, User user, String sessionKey) {
    super(user, status);
    this.firstname = user.firstName();
    this.lastname = user.lastName();
    this.username = user.username();
    this.profilePicUrl = user.profilePicUrl();
    this.sessionKey = sessionKey;
  }
}
