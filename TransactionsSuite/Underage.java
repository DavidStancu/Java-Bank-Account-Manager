package org.poo.TransactionsSuite;

/**
 * Represents a transaction where an operation fails due to the user being underage.
 * Inherits from the {@link Transaction} class and provides a description of the "underage" event.
 */
public class Underage extends Transaction {
    private final String message;

    /**
     * Constructs an Underage transaction with the specified timestamp and message.
     *
     * @param timestamp the timestamp when the "underage" event occurred
     * @param message a message describing the underage restriction
     */
    public Underage(final int timestamp, final String message) {
        super(TransactionTag.UNDERAGE.name(), timestamp);
        this.message = message;
    }

    /**
     * Returns the message describing the "underage" event.
     *
     * @return the "underage" event message
     */
    @Override
    public String getDescription() {
        return message;
    }
}
