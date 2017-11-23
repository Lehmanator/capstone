package api.response;

import api.db.logos.Logos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * History reponse
 */
@ResponseBody
public class HistoryApiResponse extends ApiResponse {
  private List<Logos> logos;

  public HistoryApiResponse(HttpStatus status, List<Logos> logos) {
    super("", status);
    this.logos = logos;
  }

  private Map<String, Object> getJson() {
    Map<String, Object> json = new HashMap<>();
    List<Map<String, Object>> modified = new ArrayList<>();

    for (Logos logo : logos) {
      Map<String, Object> map = new HashMap<>();
      map.put("link", logo.getPiclink().orElse(null));
      map.put("time", logo.getTime().orElse(null));
      map.put("result", logo.getResult().orElse(null));
      modified.add(map);
    }
    json.put("response", modified);
    return json;
  }

  public ApiResponse getApiResponse() {
    return new ApiResponse(getJson(), getStatus());
  }
}
