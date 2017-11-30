package api.handler;

import api.db.applications.Applications;
import api.db.applications.ApplicationsImpl;
import api.db.applications.ApplicationsManager;

import java.util.List;
import java.util.stream.Collectors;

public class CreditCardDataHandler {

    private ApplicationsManager applicationsManager;

    public CreditCardDataHandler(ApplicationsManager applicationsManager) {
        this.applicationsManager = applicationsManager;
    }

    public List<Applications> getApplications(String userId) {
        return applicationsManager.stream().filter(
            Applications.USER_ID.equal(userId)).collect(Collectors.toList());
    }

    public void addApplication(String userId, String applicantName, String applicantID,
                                       int age, int income, int creditScore, int expenses, double result){
        Applications application = new ApplicationsImpl();
        application.setUserId(userId);
        application.setApplicantName(applicantName);
        application.setApplicantId(applicantID);
        application.setAge(age);
        application.setIncome(income);
        application.setCreditScore(creditScore);
        application.setExpenses(expenses);
        application.setResult(result);
        applicationsManager.persist(application);
    }
}
