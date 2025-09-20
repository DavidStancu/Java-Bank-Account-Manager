package org.poo.TransactionsSuite;

/**
 * Represents a transaction where a card is deleted (destroyed) from an account.
 * Inherits from the {@link Transaction} class and provides details about the deleted card.
 */
public class CardDeleted extends Transaction {
    private final String accountIBAN;
    private final String cardNumber;
    private final String cardHolder;

    /**
     * Constructs a CardDeleted transaction with the specified details.
     *
     * @param timestamp the timestamp when the card was deleted
     * @param accountIBAN the IBAN of the account associated with the deleted card
     * @param cardNumber the card number of the deleted card
     * @param cardHolder the name of the cardholder whose card was deleted
     */
    public CardDeleted(final int timestamp, final String accountIBAN,
                       final String cardNumber, final String cardHolder) {
        super(TransactionTag.CARD_DELETED.name(), timestamp);
        this.accountIBAN = accountIBAN;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    /**
     * Returns the description of the transaction, which is "The card has been destroyed".
     *
     * @return the transaction description
     */
    @Override
    public String getDescription() {
        return "The card has been destroyed";
    }

    /**
     * Gets the IBAN of the account associated with the deleted card.
     *
     * @return the account IBAN
     */
    public String getAccountIBAN() {
        return accountIBAN;
    }

    /**
     * Gets the card number of the deleted card.
     *
     * @return the card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Gets the name of the cardholder whose card was deleted.
     *
     * @return the cardholder's name
     */
    public String getCardHolder() {
        return cardHolder;
    }
}
