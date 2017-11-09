package api.controller;

import api.Constants;
import api.controller.query.CreditCardApiQuery;
import api.db.applications.ApplicationsManager;
import api.db.logos.LogosManager;
import api.db.users.UsersManager;
import api.handler.CreditCardDataHandler;
import api.response.ApiResponse;
import api.response.CreditCardApiResponse;
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

@RestController
@EnableAsync
public class UploadCreditCardHistoryController {
    private static Logger logger = Logger.getLogger(UploadCreditCardHistoryController.class.getName());
    private @Autowired ApplicationsManager applications;

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/creditCheck", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    @Async
    CompletableFuture<ApiResponse> upload(@RequestBody CreditCardApiRequest body) {
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
                        handler.addApplication(body.getSystemUser(), body.getApplicantName(), body.getApplicantID(),
                                body.getAge(), body.getIncome(), body.getCreditScore(), body.getExpenses());
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
    }
}