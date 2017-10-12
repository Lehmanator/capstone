package api;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
          @RequestBody UploadApiRequest body
  ) {
    String image = body.getImage();
    String name = body.getName();
    String username = body.getUsername();
    try {
      DBHandler dbHandler = new DBHandler(DBConnector.getInstance().getConnection(), DBConnector.getInstance().getAmazonS3());
      if (!image.isEmpty()) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.addUserMetadata("name", name);
        String id = UUID.randomUUID().toString().replace("-", "");
        metadata.addUserMetadata("id", id);

        //Upload image to S3 bucket
        InputStream is = new ByteArrayInputStream(Base64.getDecoder().decode(image));

        dbHandler.uploadImage(username, name, is, metadata);

        //Run Recognition
        Map<String, String> json = new HashMap<>();
        json.put("image", image);
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("key", API_KEY);
        CompletableFuture<ApiResponse> future = new CompletableFuture<>();
        Unirest.post(RECOGNITION_URI)
                .headers(headers)
                .body(new JSONObject(json))
                .asJsonAsync(new Callback<JsonNode>() {
                  @Override
                  public void completed(HttpResponse<JsonNode> httpResponse) {
                    ApiResponse response = new UploadApiResponse(HttpStatus.OK, "Image successfully uploaded", id, ((Double) httpResponse.getBody().getObject().get("P(PSU Logo)")).floatValue()).getApiResponse();
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
//    } catch (IOException e) {
//      e.printStackTrace();
//      return CompletableFuture.completedFuture(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "{\"message\":\"Image could not be read\"}"));
    } catch (SQLException e) {
      e.printStackTrace();
      return CompletableFuture.completedFuture(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "{\"message\": \"Image could not be uploaded\"}"));
    }
  }

}
