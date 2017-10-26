package api.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sushrutshringarputale on 10/10/17.
 */
@ResponseBody
public class UploadApiResponse extends ApiResponse {
  private String id;
  private Float probability;
  private Object response;

  public UploadApiResponse(Object response, HttpStatus status) {
    super(response, status);
    this.response = response;
  }

  private Map<String, Object> getJson(Object response) {
    Map<String, Object> map = new HashMap<>();
    map.put("response", response);
    map.put("probability", getProbability());
    map.put("id", getId());
    return map;
  }

  public UploadApiResponse(HttpStatus status, Object response, String id, Float probability) {
    super(response, status);
    this.response = response;
    this.id = id;
    this.probability = probability;
  }

  public ApiResponse getApiResponse() {
    return new ApiResponse(getJson(this.response), getStatus());
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Float getProbability() {
    return probability;
  }

  public void setProbability(Float probability) {
    this.probability = probability;
  }

  public Object getResponse() {
    return response;
  }

  public void setResponse(Object response) {
    this.response = response;
  }
}
