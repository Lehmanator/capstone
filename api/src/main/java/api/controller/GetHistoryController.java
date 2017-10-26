package api.controller;

import api.db.logos.Logos;
import api.db.logos.LogosManager;
import api.handler.LogoHandler;
import api.handler.UserHandler;
import api.response.ApiResponse;
import api.response.ApiResponseSuccess;
import api.response.HistoryApiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Gets history
 * @author mayank
 */
@RestController
public class GetHistoryController {
  private @Autowired LogosManager logos;

  @RequestMapping(value="/getHistory", method= RequestMethod.GET)
  public ApiResponse createAccount(@RequestParam("username") String username) {
    LogoHandler handler = new LogoHandler(logos);
    List<Logos> logos = handler.getLogos(username);
    return new HistoryApiResponse(HttpStatus.OK, logos).getApiResponse();
  }
}
