package api.controller;

import api.FirebaseAuthenticator;
import api.TokenAuthenticator;
import api.db.logos.Logos;
import api.db.logos.LogosManager;
import api.handler.LogoHandler;
import api.response.ApiResponse;
import api.response.HistoryApiResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gets history
 * @author mayank
 */
@RestController
public class GetLogoHistoryController {
  private @Autowired LogosManager logos;

  @RequestMapping(value="/getHistory", method= RequestMethod.GET)
  public ApiResponse createAccount(@RequestParam("token") String token) {
    String userId = TokenAuthenticator.verifyToken(token);
    if (userId == null) {
      return TokenAuthenticator.getAuthenticationErrorResponse();
    }

    LogoHandler handler = new LogoHandler(logos);
    List<Logos> logos = handler.getLogos(userId);
    return new HistoryApiResponse(HttpStatus.OK, logos).getApiResponse();
  }
}
