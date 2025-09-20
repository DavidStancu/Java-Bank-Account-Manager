package org.poo.CardsSuite;

/**
 * Represents a generic card interface with methods to get and set the card's status.
 * The implementation of this interface should define the specific behavior for card types.
 */
public interface Card {

    /**
     * Retrieves the card number associated with this card.
     *
     * @return The card number as a String.
     */
    String getCardNumber();

    /**
     * Retrieves the current status of the card.
     *
     * @return The status of the card as a String.
     */
    String getStatus();

    /**
     * Sets the status of the card.
     *
     * @param status The new status of the card.
     */
    void setStatus(String status);

    /**
     * Retrieves the tag associated with this card.
     *
     * @return The tag of the card as a String.
     */
    String getTag();
}
