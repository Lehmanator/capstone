package api;

import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
  @RequestMapping(value="/login", method= RequestMethod.POST)
  public ApiResponse login(
      @RequestParam("username") String username,
      @RequestParam("password") String password) {
    try {
      Users users = Users.getInstance();
      User user = users.getUser(username);
      if (user != null) {
        if (user.password().equals(password)) {
          String key = users.createSessionKey(user.username());
          return new Login(HttpStatus.OK, user, key);
        }
      }
    } catch (SQLException exception) {
      return new Error(HttpStatus.FORBIDDEN, "Not authorized.");
    }
    return new Error(HttpStatus.FORBIDDEN, "Not authorized.");
  }
}
