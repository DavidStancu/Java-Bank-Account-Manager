package org.poo.TransactionsSuite;

/**
 * Represents a transaction where the status of a card is reported.
 * Inherits from the {@link Transaction} class and provides a description of the card status.
 */
public class CardStatus extends Transaction {
    private final String description;

    /**
     * Constructs a CardStatus transaction with the specified timestamp and description.
     *
     * @param timestamp the timestamp when the card status was recorded
     * @param description a description of the card status
     */
    public CardStatus(final int timestamp, final String description) {
        super(TransactionTag.CARD_STAT.name(), timestamp);
        this.description = description;
    }

    /**
     * Returns the description of the card status.
     *
     * @return the card status description
     */
    @Override
    public String getDescription() {
        return description;
    }
}
