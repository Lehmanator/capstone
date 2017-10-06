package api;

import java.sql.Timestamp;

public class Logo {
  private int id;
  private String piclink;
  private User user;
  private Timestamp time;
  private String result;

  public Logo(int id, String piclink, User user, Timestamp time, String result) {
    this.id = id;
    this.piclink = piclink;
    this.user = user;
    this.time = time;
    this.result = result;
  }

  public int id() {
    return id;
  }

  public void id(int id) {
    this.id = id;
  }

  public String piclink() {
    return piclink;
  }

  public void piclink(String piclink) {
    this.piclink = piclink;
  }

  public User user() {
    return user;
  }

  public void user(User user) {
    this.user = user;
  }

  public Timestamp time() {
    return time;
  }

  public void time(Timestamp time) {
    this.time = time;
  }

  public String result() {
    return result;
  }

  public void result(String result) {
    this.result = result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Logo) {
      Logo that = (Logo) obj;
      return (this.id == that.id() &&
              this.piclink.equals(that.piclink()) &&
              this.user.equals(that.user()) &&
              this.time.equals(that.time()) &&
              this.result.equals(that.result()));
    }
    return false;
  }
}
