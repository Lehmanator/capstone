package api.handler;

import api.db.users.Users;
import api.db.users.UsersImpl;
import api.db.users.UsersManager;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handles Users queries
 * @author mayank
 */
@Component
public class UserHandler {
  private UsersManager users;

  public UserHandler(UsersManager users) {
    this.users = users;
  }

  public Users getUser(String username) {
    Optional<Users> user = users.stream().filter(Users.USERNAME.equal(username)).findAny();
    return user.orElse(null);
  }

  public Users addNewUser(
      String username,
      String password,
      String firstname,
      String lastname,
      String profpicurl) {
    Users newUser = new UsersImpl();
    newUser.setUsername(username);
    newUser.setPassword(password);
    newUser.setFirstname(firstname);
    newUser.setLastname(lastname);
    newUser.setProfpicurl(profpicurl);
    newUser.setRole("USER");
    return users.persist(newUser);
  }
}
