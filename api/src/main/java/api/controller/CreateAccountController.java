package api.controller;

import api.db.users.UsersManager;
import api.response.ApiResponse;
import api.response.ApiResponseSuccess;
import api.S3Connector;
import api.handler.S3Handler;
import api.handler.UserHandler;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Creates account
 * @author mayank
 */
@RestController
public class CreateAccountController {
  private static Logger logger = Logger.getLogger(CreateAccountController.class.getName());
  private @Autowired UsersManager users;

  @RequestMapping(value="/createAccount", method=RequestMethod.POST)
  public ApiResponse createAccount(
      @RequestParam("username") String username,
      @RequestParam("password") String password,
      @RequestParam("firstname") String firstname,
      @RequestParam("lastname") String lastname,
      @RequestParam("profilepic") MultipartFile image) {
      String url = uploadProfilePicture(username, image);
      UserHandler handler = new UserHandler(users);
      handler.addNewUser(username, password, firstname, lastname, url);
      return new ApiResponseSuccess(HttpStatus.OK, "Successfully created account.");
  }

  private String uploadProfilePicture(String username, MultipartFile image) {
    try {
      S3Connector connector = S3Connector.getInstance();
      S3Handler handler = new S3Handler(connector.getAmazonS3());
      byte contents[] = image.getBytes();
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(contents.length);
      metadata.setContentType(image.getContentType());
      InputStream stream = new ByteArrayInputStream(contents);
      String fileName = "profile_pic." + image.getOriginalFilename().split("\\.")[1];
      handler.uploadImage(username, fileName, stream, metadata);
      return handler.getImageUrl(username, fileName);
    } catch (SQLException|IOException exception) {
      logger.severe("GOT EXCEPTION: " + exception);
      return null;
    }
  }
}
