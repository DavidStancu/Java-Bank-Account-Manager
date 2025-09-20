package org.poo.CardsSuite;

/**
 * A factory class for creating different types of cards.
 * The {@code CardFactory} class provides a method to create a {@code Card} object
 * based on the specified card type.
 */
public class CardFactory {

    /**
     * Creates a card of the specified type.
     *
     * @param cardType The type of card to create. It should be either "CLASSIC" or "ONETIME".
     * @return A {@code Card} object of the requested type.
     * @throws IllegalArgumentException If the provided card type is invalid.
     */
    public static Card createCard(final String cardType) {
        if ("CLASSIC".equalsIgnoreCase(cardType)) {
            return new ClassicCard();
        } else if ("ONETIME".equalsIgnoreCase(cardType)) {
            return new OneTimeCard();
        } else {
            throw new IllegalArgumentException("Invalid card type: " + cardType);
        }
    }
}
