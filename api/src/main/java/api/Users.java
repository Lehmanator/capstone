package api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Users {
    private volatile static Users instance = null;
    private static DBHandler handler;

    private Map<String, User> users;
    private Map<String, String> sessionKeys;

    private Users() throws SQLException {
        users = new HashMap<>();
        sessionKeys = new HashMap<>();
        DBConnector connector = DBConnector.getInstance();
        handler = new DBHandler(connector.getConnection(), connector.getAmazonS3());
    }

    public static Users getInstance() throws SQLException {
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

    public User getUser(String username) throws SQLException {
        if (users.containsKey(username)) {
            return users.get(username);
        } else {
            List<String> params = new ArrayList<>();
            params.add("username");
            params.add("password");
            params.add("firstname");
            params.add("lastname");
            params.add("profpicurl");
            params.add("id");
            String query = handler.createSelectUsersTableQuery(params, "username", username);
            List<Map<String, Object>> responses = handler.executeQueryWithResults(query);
            for (Map<String, Object> response : responses) {
                User curr = new User((String) response.get("username"),
                        (String) response.get("password"),
                        (String) response.get("firstname"),
                        (String) response.get("lastname"),
                        (String) response.get("profpicurl"),
                        (Integer) response.get("id"));
                users.put((String) response.get("username"), curr);
            }
        }
        return users.get(username);
    }

    public void changeUser(User user) {
        users.put(user.username(), user);
    }

    public void save() throws SQLException {
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

    public void addNewUser(
            String username,
            String password,
            String firstname,
            String lastname,
            String profpicurl) throws SQLException {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("profilepic", profpicurl);
        String query = handler.createInsertUsersTableQuery(params);
        handler.executeQuery(query);
        getUser(username);
    }

    public String createSessionKey(String username) {
        String key = UUID.randomUUID().toString().replace("-", "");
        sessionKeys.put(username, key);
        return key;
    }

    public String getSessionKey(String username) {
        return sessionKeys.get(username);
    }
}
