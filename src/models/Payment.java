package models;

import enums.PaymentMethod;
import enums.PaymentStatus;
import java.util.UUID;

public class Payment {

    private final String id;
    private final double amount;
    private final PaymentMethod method;
    private PaymentStatus status;

    public Payment(double amount, PaymentMethod method) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.method = method;
        this.status = PaymentStatus.PENDING;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

}
