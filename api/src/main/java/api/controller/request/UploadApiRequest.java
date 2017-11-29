package api.controller.request;

/**
 * Created by sushrutshringarputale on 10/12/17.
 */
public class UploadApiRequest {
  private String token;
  private String image;
  private String name;

  public UploadApiRequest() {
  }

  public UploadApiRequest(
      String token,
      String image,
      String name) {
    this.token = token;
    this.image = image;
    this.name = name;
  }

  public String getToken() {
    return token;
  }

  public String getImage() {
    return image;
  }

  public String getName() {
    return name;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public void setName(String name) {
    this.name = name;
  }
}
