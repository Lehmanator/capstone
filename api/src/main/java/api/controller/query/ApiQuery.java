package api.controller.query;

import java.util.HashMap;
import java.util.Map;
import api.Constants;

public abstract class ApiQuery {
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("key", Constants.API_KEY);
        return headers;
    }

    public abstract Map getRequestBody();
}
