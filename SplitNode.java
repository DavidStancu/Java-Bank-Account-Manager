package org.poo;

public class SplitNode {
    private final String iban;
    private double amount;
    private int timestamp;

    /**
     * Creates an instance of a SplitNode representing a node in a financial split transaction.
     *
     * @param iban the IBAN of the account associated with this node
     * @param amount the amount associated with this node
     * @param timestamp the timestamp when this node was created
     */
    public SplitNode(final String iban, final double amount,
                     final int timestamp) {
        this.iban = iban;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    /**
     * Returns the IBAN associated with this SplitNode.
     *
     * @return the IBAN of the account
     */
    public String getIban() {
        return iban;
    }

    /**
     * Retrieves the amount associated with this SplitNode instance.
     *
     * @return the amount associated with this node
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Updates the amount associated with this node.
     *
     * @param amount the new amount to be set
     */
    public void setAmount(final double amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the timestamp associated with this SplitNode instance.
     *
     * @return the timestamp of the node
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Updates the timestamp associated with this SplitNode instance.
     *
     * @param timestamp the new timestamp to be set
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }
}
