package org.poo.TransactionsSuite;

/**
 * Represents an online payment transaction, inheriting from the {@link Transaction} class.
 * This class captures the details of a card payment made to a commerciant.
 */
public class OnlinePayment extends Transaction {
    private final double amount;
    private final String commerciant;
    private final String description;

    /**
     * Constructs an OnlinePayment transaction with the specified timestamp,
     * description, amount, and commerciant.
     *
     * @param timestamp the timestamp when the online payment occurred
     * @param description a description of the online payment event
     * @param amount the amount paid in the transaction
     * @param commerciant the name of the commerciant receiving the payment
     */
    public OnlinePayment(final int timestamp, final String description,
                         final double amount, final String commerciant) {
        super(TransactionTag.ONLN_PAYMENT.name(), timestamp);
        this.amount = amount;
        this.commerciant = commerciant;
        this.description = "Card payment";
    }

    /**
     * Returns the description of the online payment.
     *
     * @return the description of the online payment
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns the amount paid in the online payment transaction.
     *
     * @return the payment amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the name of the commerciant who received the payment.
     *
     * @return the commerciant's name
     */
    public String getCommerciant() {
        return commerciant;
    }
}
