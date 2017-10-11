package api;

import org.springframework.http.HttpStatus;

/**
 * Created by sushrutshringarputale on 10/10/17.
 */
public class UploadApiResponse extends ApiResponseSuccess {
    private String location;
    private String id;

    public UploadApiResponse(HttpStatus status, String message) {
        super(status, message);
    }

    public UploadApiResponse(HttpStatus status, String message, String location, String id) {
        this(status, message);
        this.location = location;
        this.id = id;
    }
}
