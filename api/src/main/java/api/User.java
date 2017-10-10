package api;

public class User {
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String profilePicUrl;
  private int id;

  public User(String username,
      String password,
      String firstName,
      String lastName,
      String profilePicUrl,
      int id) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.profilePicUrl = profilePicUrl;
    this.id = id;
  }

  public String username() {
    return this.username;
  }

  public void username(String username) {
    this.username = username;
  }

  public String password() {
    return this.password;
  }

  public void password(String password) {
    this.password = password;
  }

  public String firstName() {
    return this.firstName;
  }

  public void firstName(String firstName) {
    this.firstName = firstName;
  }

  public String lastName() {
    return this.lastName;
  }

  public void lastName(String lastName) {
    this.lastName = lastName;
  }

  public String profilePicUrl() {
    return this.profilePicUrl;
  }

  public void profilePicUrl(String profilePicUrl) {
    this.profilePicUrl = profilePicUrl;
  }

  public int id() {
    return id;
  }

  public void id(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof User) {
      User that = (User) obj;
      return (this.username.equals(that.username()) &&
              this.password.equals(that.password()) &&
              this.firstName.equals(that.firstName()) &&
              this.lastName.equals(that.lastName()) &&
              this.profilePicUrl.equals(that.profilePicUrl()) &&
              this.id == that.id);
    }
    return false;
  }
}
