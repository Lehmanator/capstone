package api;

import api.response.Error;
import org.springframework.http.HttpStatus;

public class Constants {

    public final static String API_KEY = "c2geZf8u9PeAGBiwTlw2hjaT0B6ZGz86";

    private final static String BASE_URI = "http://ml-backend-dev.us-east-2.elasticbeanstalk.com/";
    private final static String IMAGE_RECOGNITION_URI = "process_image";
    private final static String CREDIT_REPORT_RECOGNITION_URI = "ccdata";

    public static String getImageRecognitionURI() {
        return BASE_URI + IMAGE_RECOGNITION_URI;
    }

    public static String getCreditReportRecognitionURI() {
        return BASE_URI + CREDIT_REPORT_RECOGNITION_URI;
    }

    public static Error databaseError() {
        return new Error(HttpStatus.INTERNAL_SERVER_ERROR, "{\"message\": \"Database error\"}");
    }
}