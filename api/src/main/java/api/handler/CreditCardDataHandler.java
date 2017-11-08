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

    public List<Applications> getApplications(String username) {
        return applicationsManager.stream().filter(Applications.USERNAME.equal(username)).collect(Collectors.toList());
    }

    public Applications addApplication(String systemUser, String applicantName, String applicantID,
                                       int age, int income, int creditScore, int expenses){
        Applications application = new ApplicationsImpl();
        application.setUsername(systemUser);
        application.setApplicantName(applicantName);
        application.setApplicantId(applicantID);
        application.setAge(age);
        application.setIncome(income);
        application.setCreditScore(creditScore);
        application.setExpenses(expenses);
        return applicationsManager.persist(application);
    }
}
