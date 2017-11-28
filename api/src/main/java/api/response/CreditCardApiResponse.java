package api.response;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class CreditCardApiResponse extends ApiResponse {
    private Float probability;
    private Object response;

    public CreditCardApiResponse(Object response, HttpStatus status) {
        super(response, status);
        this.response = response;
    }

    private Map<String, Object> getJson(Object response) {
        Map<String, Object> map = new HashMap<>();
        map.put("response", response);
        map.put("probability", getProbability());
        return map;
    }

    public CreditCardApiResponse(HttpStatus status, Object response, Float probability) {
        super(response, status);
        this.response = response;
        this.probability = probability;
    }

    public ApiResponse getApiResponse() {
        return new ApiResponse(getJson(this.response), getStatus());
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
