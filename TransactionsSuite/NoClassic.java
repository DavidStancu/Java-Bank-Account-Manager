package org.poo.TransactionsSuite;

/**
 * Represents a transaction indicating that the user does not have a classic account.
 * Inherits from the {@link Transaction} class and provides a description of the
 * "no classic account" event.
 */
public class NoClassic extends Transaction {
    private final String message;

    /**
     * Constructs a NoClassicTransaction with the specified timestamp.
     *
     * @param timestamp the timestamp when the "no classic account" event occurred
     */
    public NoClassic(final int timestamp, final String message) {
        super(TransactionTag.NO_CLASSIC.name(), timestamp);
        this.message = message;
    }

    /**
     * Returns a description of the "no classic account" event.
     *
     * @return the "no classic account" event message
     */
    @Override
    public String getDescription() {
        return message;
    }
}
