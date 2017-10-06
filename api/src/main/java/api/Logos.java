package api;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Logos {
  private volatile static Logos instance = null;
  private Map<User, Logo> logos;

  private Logos() {
    logos = new HashMap<>();
  }

  public static Logos getInstance() {
    if (instance == null) {
      synchronized (Logos.class) {
        if (instance == null) {
          instance = new Logos();
        }
      }
    }
    return instance;
  }

  public Map<User, Logo> getLogos() {
    return logos;
  }

  public Logo getLogo(User user) {
    return logos.get(user);
  }

  public void changeLogo(Logo logo) {
    logos.put(logo.user(), logo);
  }

  public void save() throws SQLException {
    DBConnector connector = DBConnector.getInstance();
    DBHandler handler = new DBHandler(connector.getConnection(), connector.getAmazonS3());
    for (Logo logo : logos.values()) {
      Map<String, String> params = new HashMap<>();
      params.put("piclink", logo.piclink());
      params.put("username", logo.user().username());
      params.put("time", logo.time().toString());
      params.put("result", logo.result());
      String query = handler.createUpdateUsersTableQuery(
          params,
          "id",
          Integer.toString(logo.id()));
      handler.executeQuery(query);
    }
  }

  public void addLogo(int id, String piclink, User user, Timestamp time, String result) {
    logos.put(user, new Logo(id, piclink, user, time, result));
  }
}
