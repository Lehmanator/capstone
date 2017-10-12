package api;

/**
 * Created by sushrutshringarputale on 10/12/17.
 */
public class UploadApiRequest {
  public String image;
  public String name;
  public String username;

  public UploadApiRequest() {
    image = "";
    name = "";
    username = "";
  }

  public UploadApiRequest(String image, String name, String username) {
    this.image = image;
    this.name = name;
    this.username = username;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
