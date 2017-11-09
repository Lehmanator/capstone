package api.controller;

import api.Constants;
import api.controller.query.ApiQuery;
import api.controller.query.ImageRecognitionQuery;
import api.db.logos.LogosManager;
import api.db.users.UsersManager;
import api.response.ApiResponse;
import api.response.Error;
import api.S3Connector;
import api.response.UploadApiResponse;
import api.handler.LogoHandler;
import api.handler.S3Handler;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.util.bcel.Const;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
  private static Logger logger = Logger.getLogger(UploadLogoController.class.getName());
  //TODO: Make this work from AWS
  private @Autowired LogosManager logos;

  @CrossOrigin(origins = "http://localhost:8080")
  @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  @Async
  CompletableFuture<ApiResponse> upload(@RequestBody UploadApiRequest body) {
    String image = body.getImage();
    String name = body.getName();
    String username = body.getUsername();
    try {
      // TODO: Make this work
      S3Handler s3Handler = new S3Handler(S3Connector.getInstance().getAmazonS3());
      if (!image.isEmpty()) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.addUserMetadata("name", name);
        String id = UUID.randomUUID().toString().replace("-", "");
        metadata.addUserMetadata("id", id);

        //Upload image to S3 bucket
	      String imageType = image.split(",")[1];
        InputStream is = new ByteArrayInputStream(Base64.getDecoder().decode(imageType));

        s3Handler.uploadImage(username, name, is, metadata);

        //Run Recognition
        ApiQuery query = new ImageRecognitionQuery(imageType);
        CompletableFuture<ApiResponse> future = new CompletableFuture<>();
        Unirest.post(Constants.getImageRecognitionURI())
                .headers(query.getRequestHeaders())
                .body(new JSONObject(query.getRequestBody()))
                .asJsonAsync(new Callback<JsonNode>() {
                  @Override
                  public void completed(HttpResponse<JsonNode> httpResponse) {
                    Float probability = ((Double) httpResponse.getBody()
                        .getObject().get("P(PSU Logo)")).floatValue();
                    ApiResponse response = new UploadApiResponse(
                        HttpStatus.OK,
                        "Image successfully uploaded",
                        id,
                        probability).getApiResponse();
                    logger.info(response.toString());
                    LogoHandler handler = new LogoHandler(logos);
                    handler.addLogo(
                        s3Handler.getImageUrl(username, name),
                        username,
                        probability.toString());
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
    } catch (AmazonS3Exception e) {
      e.printStackTrace();
      return CompletableFuture.completedFuture(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "{\"message\":\"Image could not be uploaded\"}"));
    } catch (SQLException e) {
      e.printStackTrace();
      return CompletableFuture.completedFuture(Constants.databaseError());
    }
  }
}
