package api.controller.request;

/**
 * Created by sushrutshringarputale on 10/12/17.
 */
public class UploadApiRequest {
  public String image;
  public String name;
  public String userId;

  public UploadApiRequest() {
    image = "";
    name = "";
    userId = "";
  }

  public UploadApiRequest(String image, String name, String userId) {
    this.image = image;
    this.name = name;
    this.userId = userId;
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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
