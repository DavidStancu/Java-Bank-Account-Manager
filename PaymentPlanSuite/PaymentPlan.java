package org.poo.PaymentPlanSuite;

/**
 * Represents a payment plan with specific attributes such as type, transaction fee,
 * and minimum transaction amount. This class provides methods to retrieve the plan details
 * and determine if a transaction fee is applicable based on the given amount.
 */
public class PaymentPlan {
    private String type;
    private double transactionFee;
    private double minTransactionAmount;

    public PaymentPlan(final String type, final double transactionFee,
                       final double minTransactionAmount) {
        this.type = type;
        this.transactionFee = transactionFee;
        this.minTransactionAmount = minTransactionAmount;
    }

    /**
     * Retrieves the type of the payment plan.
     *
     * @return the type of the payment plan as a String.
     */
    public String getType() {
        return type;
    }

    /**
     * Retrieves the transaction fee associated with the payment plan.
     *
     * @return the transaction fee as a double value.
     */
    public double getTransactionFee() {
        return transactionFee;
    }

    /**
     * Determines if a transaction fee is applicable for the specified transaction amount.
     *
     * @param amount the transaction amount to be evaluated.
     * @return true if the transaction fee is applicable, false otherwise.
     */
    public boolean isFeeApplicable(final double amount) {
        return transactionFee > 0
                && (minTransactionAmount == 0
                || amount >= minTransactionAmount);
    }
}
