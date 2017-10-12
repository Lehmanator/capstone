package api;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.javafx.util.Logging;
import javafx.concurrent.Task;
import org.apache.commons.logging.impl.Log4JLogger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sushrutshringarputale on 10/10/17.
 */
@RestController
@EnableAsync
public class UploadLogoController {

  private static String API_KEY = "c2geZf8u9PeAGBiwTlw2hjaT0B6ZGz86";
  private static String RECOGNITION_URI = "http://ml-backend-dev.us-east-2.elasticbeanstalk.com/process_image";

  @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  @Async
  CompletableFuture<ApiResponse> upload(
          @RequestParam("image") MultipartFile image,
          @RequestParam("name") String name,
          @RequestParam("user") String username
  ) {
    try {
      DBHandler dbHandler = new DBHandler(DBConnector.getInstance().getConnection(), DBConnector.getInstance().getAmazonS3());
      if (!image.isEmpty()) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.addUserMetadata("name", name);
        String id = UUID.randomUUID().toString().replace("-", "");
        metadata.addUserMetadata("id", id);

        //Upload image to S3 bucket
        dbHandler.uploadImage(username, name, image.getInputStream(), metadata);

        //Run Recognition
        String b64 = Base64.getEncoder().encodeToString(image.getBytes());
        Map<String, String> json = new HashMap<>();
        json.put("image", b64);
        CompletableFuture<ApiResponse> future = new CompletableFuture<>();
        Unirest.post(RECOGNITION_URI)
                .body(json)
                .asJsonAsync(new Callback<JsonNode>() {
                  @Override
                  public void completed(HttpResponse<JsonNode> httpResponse) {
                    UploadApiResponse response = new UploadApiResponse(HttpStatus.OK, "Image successfully uploaded", id, Float.parseFloat((String) httpResponse.getBody().getObject().get("P(PSU Logo)")));
                    //TODO: Upload to SQL
//                    dbHandler.createUpdateLogosTableQuery()
                    future.complete(response);
                  }

                  @Override
                  public void failed(UnirestException e) {
                    e.printStackTrace();
                    future.complete(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "{\"message\":\"The request failed. See server logs for details\"}"));
                  }

                  @Override
                  public void cancelled() {
                    future.complete(new Error(HttpStatus.REQUEST_TIMEOUT, "{\"message\":\"The request was cancelled\"}"));
                  }
                });
        return future;
      } else {
        return CompletableFuture.completedFuture(new Error(HttpStatus.BAD_REQUEST, "{\"message\":\"Image must be provided\"}"));
      }
    } catch (IOException e) {
      e.printStackTrace();
      return CompletableFuture.completedFuture(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "{\"message\":\"Image could not be read\"}"));
    } catch (SQLException e) {
      e.printStackTrace();
      return CompletableFuture.completedFuture(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "{\"message\": \"Image could not be uploaded\"}"));
    }
  }

}
