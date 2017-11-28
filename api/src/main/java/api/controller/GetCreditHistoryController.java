package api.controller;

import api.FirebaseAuthenticator;
import api.TokenAuthenticator;
import api.db.applications.Applications;
import api.db.applications.ApplicationsManager;
import api.handler.CreditCardDataHandler;
import api.response.ApiResponse;
import api.response.CreditHistoryApiResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gets history
 *
 * @author mayank
 */
@RestController
public class GetCreditHistoryController {
  private static Logger logger = Logger
      .getLogger(GetCreditHistoryController.class.getName());
  private @Autowired
  ApplicationsManager applications;

  @RequestMapping(value = "/getCreditCardHistory", method = RequestMethod.GET)
  public ApiResponse createAccount(@RequestParam("token") String token) {
    String userId = TokenAuthenticator.verifyToken(token);
    if (userId == null) {
      return TokenAuthenticator.getAuthenticationErrorResponse();
    }
    CreditCardDataHandler handler = new CreditCardDataHandler(applications);
    List<Applications> applicationsList = handler.getApplications(userId);
    return new CreditHistoryApiResponse(HttpStatus.OK, applicationsList).getApiResponse();
  }
}