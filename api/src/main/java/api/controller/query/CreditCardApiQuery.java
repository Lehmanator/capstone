package api.controller.query;

import java.util.Map;
import java.util.HashMap;

public class CreditCardApiQuery extends ApiQuery {

    private final String creditScoreKey = "credit_score";
    private final String ageKey = "age";
    private final String expensesKey = "expenses";
    private final String incomeKey = "income";

    private int creditScore;
    private int age;
    private int expenses;
    private int income;

    // TODO: Range validation for creditScore, income, expenses, age
    public CreditCardApiQuery(int creditScore, int income, int expenses, int age) {
        this.creditScore = creditScore;
        this.income = income;
        this.expenses = expenses;
        this.age = age;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public int getAge() {
        return age;
    }

    public int getExpenses() {
        return expenses;
    }

    public int getIncome() {
        return income;
    }

    @Override
    public Map<String, Integer> getRequestBody() {
        Map<String, Integer> body = new HashMap<>();
        body.put(creditScoreKey, this.getCreditScore());
        body.put(ageKey, this.getAge());
        body.put(expensesKey, this.getExpenses());
        body.put(incomeKey, this.getIncome());
        return body;
    }
}
