package api;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Users {
  private volatile static Users instance = null;
  private Map<String, User> users;

  private Users() {
    users = new HashMap<>();
  }

  public static Users getInstance() {
    if (instance == null) {
      synchronized (Users.class) {
        if (instance == null) {
          instance = new Users();
        }
      }
    }
    return instance;
  }

  public Map<String, User> getAllUsers() {
    return users;
  }

  public User getUser(String username) {
    return users.get(username);
  }

  public void changeUser(User user) {
    users.put(user.username(), user);
  }

  public void save() throws SQLException {
    DBConnector connector = DBConnector.getInstance();
    DBHandler handler = new DBHandler(connector.getConnection(), connector.getAmazonS3());
    for (User user : users.values()) {
      Map<String, String> params = new HashMap<>();
      params.put("password", user.password());
      params.put("firstname", user.firstName());
      params.put("lastname", user.lastName());
      params.put("profilepic", user.profilePicUrl());
      String query =
          handler.createUpdateUsersTableQuery(params, "username", user.username());
      handler.executeQuery(query);
    }
  }

  public void addUser(String username,
      String password,
      String firstname,
      String lastname,
      String profpicurl,
      int id) {
    users.put(username, new User(username, password, firstname, lastname, profpicurl, id));
  }
}
