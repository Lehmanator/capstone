package api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sushrutshringarputale on 10/10/17.
 */
@ResponseBody
public class UploadApiResponse extends ApiResponse {
    private String location;
    private String id;
    private String message;

    public UploadApiResponse(Object response, HttpStatus status) {
        super(response, status);
        this.message = message;
    }

    public UploadApiResponse(HttpStatus status, Object response, String location, String id) {
        this(response, status);
        this.message = message;
        this.location = location;
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
}
