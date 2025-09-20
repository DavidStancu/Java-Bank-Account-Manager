package org.poo.TransactionsSuite;

/**
 * Represents a transaction where a new card is created for an account.
 * Inherits from the {@link Transaction} class and provides details about the created card.
 */
public class CardCreated extends Transaction {
    private final String accountIBAN;
    private final String cardNumber;
    private final String cardHolder;

    /**
     * Constructs a CardCreated transaction with the specified details.
     *
     * @param timestamp the timestamp when the card was created
     * @param accountIBAN the IBAN of the account associated with the new card
     * @param cardNumber the card number of the newly created card
     * @param cardHolder the name of the cardholder
     */
    public CardCreated(final int timestamp, final String accountIBAN,
                       final String cardNumber, final String cardHolder) {
        super(TransactionTag.CARD_CREATED.name(), timestamp);
        this.accountIBAN = accountIBAN;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    /**
     * Returns the description of the transaction, which is "New card created".
     *
     * @return the transaction description
     */
    @Override
    public String getDescription() {
        return "New card created";
    }

    /**
     * Gets the IBAN of the account associated with the newly created card.
     *
     * @return the account IBAN
     */
    public String getAccountIBAN() {
        return accountIBAN;
    }

    /**
     * Gets the card number of the newly created card.
     *
     * @return the card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Gets the name of the cardholder for the newly created card.
     *
     * @return the cardholder's name
     */
    public String getCardHolder() {
        return cardHolder;
    }
}
