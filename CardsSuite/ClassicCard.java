package org.poo.CardsSuite;

import org.poo.utils.Utils;

/**
 * Represents a classic card in the banking system.
 * The {@code ClassicCard} class implements the {@code Card} interface and provides
 * the functionality specific to a classic card, including the card number, status,
 * and tag associated with it.
 */
public class ClassicCard implements Card {
    private String cardNumber;
    private String status;
    private String tag;

    /**
     * Constructs a new {@code ClassicCard} with a generated card number,
     * an initial status of "active", and a tag of "CLASSIC".
     */
    public ClassicCard() {
        this.cardNumber = Utils.generateCardNumber();
        this.status = "active";
        this.tag = "CLASSIC";
    }

    /**
     * Returns the card number of this {@code ClassicCard}.
     *
     * @return The card number as a {@code String}.
     */
    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Returns the current status of this {@code ClassicCard}.
     *
     * @return The status of the card as a {@code String}.
     */
    @Override
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of this {@code ClassicCard}.
     *
     * @param status The new status of the card as a {@code String}.
     */

    @Override
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Retrieves the tag associated with this card.
     *
     * @return The tag of the card as a String.
     */
    @Override
    public String getTag() {
        return tag;
    }
}
