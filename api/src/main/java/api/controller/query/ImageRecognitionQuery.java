package api.controller.query;

import java.util.HashMap;
import java.util.Map;

public class ImageRecognitionQuery extends ApiQuery {

    private String image;
    private final String imageKey = "image";

    public ImageRecognitionQuery(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public Map<String, String> getRequestBody() {
        Map<String, String> body = new HashMap<>();
        body.put("image", this.getImage());
        return body;
    }
}
