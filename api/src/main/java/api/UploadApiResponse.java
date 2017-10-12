package api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sushrutshringarputale on 10/10/17.
 */
@ResponseBody
public class UploadApiResponse extends ApiResponse {
  private String id;
  private String message;
  private Float probability;

  public UploadApiResponse(Object response, HttpStatus status) {
    super(response, status);
    this.message = message;
  }

  public UploadApiResponse(HttpStatus status, Object response, String id, Float probability) {
    this(response, status);
    this.message = message;
    this.id = id;
    this.probability = probability;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Float getProbability() {
    return probability;
  }

  public void setProbability(Float probability) {
    this.probability = probability;
  }
}
