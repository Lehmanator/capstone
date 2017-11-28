package api.controller;

import api.Constants;
import api.TokenAuthenticator;
import api.controller.query.CreditCardApiQuery;
import api.controller.request.CreditCardApiRequest;
import api.db.applications.ApplicationsManager;
import api.handler.CreditCardDataHandler;
import api.response.ApiResponse;
import api.response.CreditCardApiResponse;
import api.response.Error;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@EnableAsync
public class UploadCreditCardHistoryController {

  private static Logger logger = Logger
      .getLogger(UploadCreditCardHistoryController.class.getName());
  private @Autowired
  ApplicationsManager applications;
  private String userId = null;

  @CrossOrigin(origins = "http://localhost:8080")
  @RequestMapping(value = "/creditCheck", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  @Async
  CompletableFuture<ApiResponse> upload(@RequestBody CreditCardApiRequest body) {
    userId = TokenAuthenticator.verifyToken(body.getToken());
    if (userId == null) {
      CompletableFuture<ApiResponse> errorFuture = new CompletableFuture<>();
      errorFuture.complete(TokenAuthenticator.getAuthenticationErrorResponse());
      return errorFuture;
    }

    //Run Recognition
    CreditCardApiQuery query = new CreditCardApiQuery(body.getCreditScore(), body.getIncome(),
        body.getExpenses(), body.getAge());
    CompletableFuture<ApiResponse> future = new CompletableFuture<>();
    Unirest.post(Constants.getCreditReportRecognitionURI())
        .headers(query.getRequestHeaders())
        .body(new JSONObject(query.getRequestBody()))
        .asJsonAsync(new Callback<JsonNode>() {
          @Override
          public void completed(HttpResponse<JsonNode> httpResponse) {
            Float probability = ((Number) httpResponse.getBody()
                .getObject().get("P(Accepted)")).floatValue();
            ApiResponse response = new CreditCardApiResponse(
                HttpStatus.OK,
                "CreditCardDataUploaded",
                probability).getApiResponse();
            logger.info(response.toString());
            CreditCardDataHandler handler = new CreditCardDataHandler(applications);
            handler.addApplication(
                userId,
                body.getApplicantName(),
                body.getApplicantID(),
                body.getAge(),
                body.getIncome(),
                body.getCreditScore(),
                body.getExpenses(),
                probability.doubleValue());
            future.complete(response);
          }

          @Override
          public void failed(UnirestException e) {
            e.printStackTrace();
            future.complete(new Error(HttpStatus.INTERNAL_SERVER_ERROR,
                "{\"message\":\"The request failed. See server logs for details\"}"));
          }

          @Override
          public void cancelled() {
            future.complete(new Error(HttpStatus.REQUEST_TIMEOUT,
                "{\"message\":\"The request was cancelled\"}"));
          }
        });
    return future;
  }
}