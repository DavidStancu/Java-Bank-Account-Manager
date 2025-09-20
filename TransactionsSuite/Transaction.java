package org.poo.TransactionsSuite;

/**
 * Represents a financial transaction in the system.
 * This class serves as a base for various types of transactions,
 * each providing a specific description of the transaction.
 */
public abstract class Transaction {
    private final String transactionTag;
    private final int timestamp;

    /**
     * Constructs a Transaction with the specified transaction tag and timestamp.
     *
     * @param transactionTag the tag representing the type of the transaction
     * @param timestamp the timestamp when the transaction occurred
     */
    public Transaction(final String transactionTag, final int timestamp) {
        this.transactionTag = transactionTag;
        this.timestamp = timestamp;
    }

    /**
     * Returns the tag representing the type of the transaction.
     *
     * @return the transaction tag
     */
    public String getTransactionTag() {
        return transactionTag;
    }

    /**
     * Returns the timestamp when the transaction occurred.
     *
     * @return the timestamp of the transaction
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a description of the transaction.
     * This method must be implemented by subclasses to provide specific descriptions.
     *
     * @return a description of the transaction
     */
    public abstract String getDescription();
}
