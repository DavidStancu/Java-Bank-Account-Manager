package org.poo.TransactionsSuite;

/**
 * Represents a transaction where a transaction fails due to insufficient funds.
 * Inherits from the {@link Transaction} class and provides a description of the "no funds" event.
 */
public class NoFunds extends Transaction {
    private final String description;

    /**
     * Constructs a NoFunds transaction with the specified timestamp and description.
     *
     * @param timestamp the timestamp when the "no funds" event occurred
     * @param description a description of the "no funds" event
     */
    public NoFunds(final int timestamp, final String description) {
        super(TransactionTag.NO_FUNDS.name(), timestamp);
        this.description = description;
    }

    /**
     * Returns the description of the "no funds" event.
     *
     * @return the "no funds" event description
     */
    @Override
    public String getDescription() {
        return description;
    }
}
