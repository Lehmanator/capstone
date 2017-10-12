package api;

import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CreateAccountController {
  @RequestMapping(value="/createAccount", method=RequestMethod.POST)
  public ApiResponse createAccount(
      @RequestParam("username") String username,
      @RequestParam("password") String password,
      @RequestParam("firstname") String firstname,
      @RequestParam("lastname") String lastname,
      @RequestParam("profilepic") MultipartFile image) {
    try {
      String url = uploadProfilePicture(username, image);
      makeUser(username, password, firstname, lastname, url);
      return new ApiResponseSuccess(HttpStatus.OK, "Successfully created account.");
    } catch (SQLException exception) {
      return new Error(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot create account.");
    }
  }

  public void makeUser(
      String username,
      String password,
      String firstname,
      String lastname,
      String profilepicurl) throws SQLException {
    Users users = Users.getInstance();
    users.addNewUser(username, password, firstname, lastname, profilepicurl);
  }

  public String uploadProfilePicture(String username, MultipartFile image) {
    try {
      DBConnector connector = DBConnector.getInstance();
      DBHandler handler = new DBHandler(connector.getConnection(), connector.getAmazonS3());
      byte contents[] = image.getBytes();
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(contents.length);
      metadata.setContentType(image.getContentType());
      InputStream stream = new ByteArrayInputStream(contents);
      String fileName = "profile_pic" + image.getOriginalFilename().split(".")[0];
      handler.uploadImage(username, fileName, stream, metadata);
      return handler.getImageUrl(username, fileName);
    } catch (SQLException|IOException exception) {
      return null;
    }
  }
}
