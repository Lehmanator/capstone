package api;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logos {
  private volatile static Logos instance = null;
  private static DBHandler handler;

  private Map<Integer, Logo> logos;


  private Logos() throws SQLException {
    logos = new HashMap<>();
    DBConnector connector = DBConnector.getInstance();
    handler = new DBHandler(connector.getConnection(), connector.getAmazonS3());
  }

  public static Logos getInstance() throws SQLException {
    if (instance == null) {
      synchronized (Logos.class) {
        if (instance == null) {
          instance = new Logos();
        }
      }
    }
    return instance;
  }

  public Map<Integer, Logo> getLogos() {
    return logos;
  }

  public Logo getLogo(Integer id) throws SQLException {
    if (logos.containsKey(id)) {
      return logos.get(id);
    } else {
      List<String> params = new ArrayList<>();
      params.add("id");
      params.add("piclink");
      params.add("username");
      params.add("time");
      params.add("result");
      String query =
          handler.createSelectLogosTableQuery(params, "id", Integer.toString(id));
      List<Map<String, Object>> responses = handler.executeQueryWithResults(query);
      for (Map<String, Object> response : responses) {
        Logo curr = new Logo(Integer.parseInt((String) response.get("id")),
            (String) response.get("piclink"),
            Users.getInstance().getUser((String) response.get("username")),
            Timestamp.valueOf((String) response.get("time")),
            (String) response.get("result"));
        logos.put(Integer.parseInt((String) response.get("id")), curr);
      }
    }
    return logos.get(id);
  }

  public void changeLogo(Logo logo) {
    logos.put(logo.id(), logo);
  }

  public void save() throws SQLException {
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
    logos.put(id, new Logo(id, piclink, user, time, result));
  }
}
