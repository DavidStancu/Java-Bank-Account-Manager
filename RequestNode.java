package org.poo;

/**
 * Represents a request node used to store and manage transaction details
 * in a queue-based system. Each RequestNode contains the amount, IBAN,
 * timestamp, and a buffer for additional calculations.
 */
public class RequestNode {
    private double amount;
    private double amountBuffer;
    private int timestamp;
    private String iban;

    public RequestNode(final double amount, final String iban) {
        this.amount = amount;
        this.amountBuffer = 0.0;
        this.timestamp = 0;
        this.iban = iban;
    }

    /**
     * Retrieves the amount associated with this request node.
     *
     * @return the amount of the current transaction
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Retrieves the buffer amount associated with this request node.
     *
     * @return the buffer amount used for additional transaction calculations
     */
    public double getAmountBuffer() {
        return amountBuffer;
    }

    /**
     * Retrieves the timestamp associated with this request node.
     *
     * @return the timestamp of the current transaction
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the buffer amount for additional calculations associated with this request node.
     *
     * @param amountBuffer the buffer amount to be set
     */
    public void setAmountBuffer(final double amountBuffer) {
        this.amountBuffer = amountBuffer;
    }

    /**
     * Sets the timestamp for the current transaction.
     *
     * @param timestamp the timestamp to be set, representing the time of the transaction
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }
}
