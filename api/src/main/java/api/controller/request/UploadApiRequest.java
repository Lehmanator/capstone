package api.controller.request;

/**
 * Created by sushrutshringarputale on 10/12/17.
 */
public class UploadApiRequest {
  private String image;
  private String name;
  private String token;

  public UploadApiRequest() {
    image = "";
    name = "";
    token = "";
  }

  public UploadApiRequest(String image, String name, String token) {
    this.image = image;
    this.name = name;
    this.token = token;
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

  public String getToken() {
    return token;
  }

  public void setToken(String userId) {
    this.token = token;
  }
}
