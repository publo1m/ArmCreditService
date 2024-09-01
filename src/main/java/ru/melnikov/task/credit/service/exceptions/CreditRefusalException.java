package ru.melnikov.task.credit.service.exceptions;

public class CreditRefusalException extends RuntimeException{
    private static final String CREDIT_REFUSAL = "Was denied credit";

    public CreditRefusalException() {
        super(CREDIT_REFUSAL);
    }

    public CreditRefusalException(String message) {
        super(message);
    }
}
