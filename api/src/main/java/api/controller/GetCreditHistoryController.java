package api.controller;

import api.db.applications.Applications;
import api.db.applications.ApplicationsManager;
import api.handler.CreditCardDataHandler;
import api.response.ApiResponse;
import api.response.CreditHistoryApiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gets history
 * @author mayank
 */
@RestController
public class GetCreditHistoryController {
    private @Autowired ApplicationsManager applications;

    @RequestMapping(value="/getCreditCardHistory", method= RequestMethod.GET)
    public ApiResponse createAccount(@RequestParam("username") String username) {
        CreditCardDataHandler handler = new CreditCardDataHandler(applications);
        List<Applications> applicationsList = handler.getApplications(username);
        return new CreditHistoryApiResponse(HttpStatus.OK, applicationsList).getApiResponse();
    }
}