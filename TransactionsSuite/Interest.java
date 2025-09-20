package org.poo.TransactionsSuite;

/**
 * Represents a transaction where interest is added to an account.
 * Inherits from the {@link Transaction} class and provides a description
 * for the interest operation.
 */
public class Interest extends Transaction {
    private final double amount;
    private final String currency;
    private final String description;

    /**
     * Constructs an InterestTransaction with the specified amount, currency,
     * timestamp, and description.
     *
     * @param amount the interest amount added to the account
     * @param currency the currency of the interest amount
     * @param timestamp the timestamp when the interest transaction occurred
     */
    public Interest(final double amount, final String currency,
                    final int timestamp) {
        super(TransactionTag.INTEREST.name(), timestamp);
        this.amount = amount;
        this.currency = currency;
        this.description = "Interest rate income";
    }

    /**
     * Returns the amount of interest added.
     *
     * @return the interest amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the currency of the interest.
     *
     * @return the currency of the interest
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Returns the description of the interest transaction.
     *
     * @return the transaction description
     */
    @Override
    public String getDescription() {
        return description;
    }
}
