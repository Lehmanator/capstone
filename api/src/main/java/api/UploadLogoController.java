package api;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.concurrent.Task;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Created by sushrutshringarputale on 10/10/17.
 */
@RestController
public class UploadLogoController {

    private static String recognitionAPIKey = "c2geZf8u9PeAGBiwTlw2hjaT0B6ZGz86";
    private static String imageRecognitionUri = "http://ml-backend-dev.us-east-2.elasticbeanstalk.com/process_image";


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public UploadApiResponse upload(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("user") String username
    ) {
        try {
            DBHandler dbHandler = new DBHandler(DBConnector.getInstance().getConnection(), DBConnector.getInstance().getAmazonS3());
            if (!image.isEmpty()) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.addUserMetadata("name", image.getOriginalFilename());
                String id = UUID.randomUUID().toString().replace("-", "");
                metadata.addUserMetadata("id", id);

                //Upload image to S3 bucket
                String location = dbHandler.uploadImage(username, name, image.getInputStream(), metadata);
                UploadApiResponse response = new UploadApiResponse(HttpStatus.OK, "Image successfully uploaded", location, id);
                return response;
            } else {
                return new UploadApiResponse(HttpStatus.BAD_REQUEST, "Image must be provided");
            }
        } catch (IOException e) {
            return new UploadApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Image could not be read");
        } catch (SQLException e) {
            return new UploadApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Image could not be uploaded");
        }
    }

}
