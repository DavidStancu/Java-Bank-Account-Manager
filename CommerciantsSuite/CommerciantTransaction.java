package org.poo.CommerciantsSuite;


/**
 * Represents a transaction involving a merchant, including details such as the account IBAN,
 * transaction amount, timestamp, description, and the merchant's name.
 * This class is used to store the relevant information about a transaction
 * and provides access methods for these details.
 */
public class CommerciantTransaction {
    private final String accountIBAN;
    private double amount;
    private final int timestamp;
    private final String description;
    private final String commerciant;

    /**
     * Constructs a CommerciantTransaction.
     *
     * @param accountIBAN the IBAN of the account used for the transaction
     * @param amount the amount involved in the transaction
     * @param timestamp the timestamp when the transaction occurred
     */
    public CommerciantTransaction(final String accountIBAN,
                                  final double amount,
                                  final int timestamp, final String description,
                                  final String commerciant) {
        this.accountIBAN = accountIBAN;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
        this.commerciant = commerciant;
    }

    /**
     * Retrieves the International Bank Account Number (IBAN) associated with the transaction.
     *
     * @return the IBAN of the account linked to this transaction.
     */
    public String getAccountIBAN() {
        return accountIBAN;
    }

    /**
     * Retrieves the amount associated with the transaction.
     *
     * @return the transaction amount as a double.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Updates the amount involved in the transaction.
     *
     * @param amount the new amount to set for the transaction
     */
    public void setAmount(final double amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the timestamp associated with the transaction.
     *
     * @return the timestamp of the transaction as an integer.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Retrieves the description of the transaction.
     *
     * @return the transaction description as a String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the name of the merchant associated with this transaction.
     *
     * @return the merchant's name as a String.
     */
    public String getCommerciant() {
        return commerciant;
    }


}
