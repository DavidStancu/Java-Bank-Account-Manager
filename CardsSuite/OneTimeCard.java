package org.poo.CardsSuite;

import org.poo.utils.Utils;

/**
 * Represents a one-time card in the banking system.
 * The {@code OneTimeCard} class implements the {@code Card} interface and provides
 * the functionality specific to a one-time card, including the card number, status,
 * and tag associated with it.
 */
public class OneTimeCard implements Card {
    private String cardNumber;
    private String status;
    private String tag;

    /**
     * Constructs a new {@code OneTimeCard} with a generated card number,
     * an initial status of "active", and a tag of "ONETIME".
     */
    public OneTimeCard() {
        this.cardNumber = Utils.generateCardNumber();
        this.status = "active";
        this.tag = "ONETIME";
    }

    /**
     * Returns the card number of this {@code OneTimeCard}.
     *
     * @return The card number as a {@code String}.
     */
    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Returns the current status of this {@code OneTimeCard}.
     *
     * @return The status of the card as a {@code String}.
     */
    @Override
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of this {@code OneTimeCard}.
     *
     * @param status The new status of the card as a {@code String}.
     */
    @Override
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Retrieves the tag associated with this {@code OneTimeCard}.
     *
     * @return The tag of the card as a {@code String}.
     */
    @Override
    public String getTag() {
        return tag;
    }

    /**
     * Changes the card number of this {@code OneTimeCard} by generating a new card number
     * using the {@code Utils.generateCardNumber()} utility method.
     */
    public void changeCardNumber() {
        this.cardNumber = Utils.generateCardNumber();
    }
}
