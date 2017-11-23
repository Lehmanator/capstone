package api.response;

import api.db.applications.Applications;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditHistoryApiResponse extends ApiResponse {
    private List<Applications> applications;

    public CreditHistoryApiResponse(HttpStatus status, List<Applications> applications) {
        super("", status);
        this.applications = applications;
    }

    private Map<String, Object> getJson() {
        Map<String, Object> json = new HashMap<>();
        List<Map<String, Object>> modified = new ArrayList<>();

        for (Applications application : applications) {
            Map<String, Object> map = new HashMap<>();
            map.put("applicantName", application.getApplicantName());
            map.put("applicantID", application.getApplicantId());
            map.put("age", application.getAge());
            map.put("income", application.getIncome());
            map.put("creditScore", application.getCreditScore());
            map.put("expenses", application.getExpenses());
            map.put("result", application.getResult().orElse(-1));
            modified.add(map);
        }
        json.put("response", modified);
        return json;
    }

    public ApiResponse getApiResponse() {
        return new ApiResponse(getJson(), getStatus());
    }
}
