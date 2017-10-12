package api;

import org.json.JSONObject;
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
  private String message;
  private Float probability;
  private Object response;

  public UploadApiResponse(Object response, HttpStatus status) {
    super(response, status);
    this.response = response;
  }

  private JSONObject getJson(Object response) {
    Map<String, Object> map = new HashMap<>();
    map.put("response", response);
    map.put("message", getMessage());
    map.put("probability", getProbability());
    map.put("id", getId());
    return new JSONObject(map);
  }


  public UploadApiResponse(HttpStatus status, Object response, String id, Float probability) {
    this(response, status);
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

  public Object getResponse() {
    return response;
  }

  public void setResponse(Object response) {
    this.response = response;
  }
}
