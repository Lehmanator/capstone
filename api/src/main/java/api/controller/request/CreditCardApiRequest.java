package api.controller.request;

public class CreditCardApiRequest {
    private String token;
    private String applicantName;
    private String applicantID;
    private int age;
    private int income;
    private int creditScore;
    private int expenses;

    public CreditCardApiRequest() {
    }

    public CreditCardApiRequest(
            String token,
            String applicantName,
            String applicantID,
            Integer age,
            Integer income,
            Integer creditScore,
            Integer expenses) {
        this.token = token;
        this.applicantName = applicantName;
        this.applicantID = applicantID;
        this.age = age;
        this.income = income;
        this.creditScore = creditScore;
        this.expenses = expenses;
    }

    public String getToken() {
        return token;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public int getAge() {
        return age;
    }

    public int getIncome() {
        return income;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public int getExpenses() {
        return expenses;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }
}
