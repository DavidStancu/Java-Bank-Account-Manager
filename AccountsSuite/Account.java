package org.poo.AccountsSuite;

import org.poo.CardsSuite.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank account with various attributes and operations, including
 * managing account balance, cards, and account information.
 */
public interface Account {

    /**
     * Retrieves the International Bank Account Number (IBAN) of the account.
     *
     * @return the IBAN of the account.
     */
    String getIBAN();

    /**
     * Retrieves the email associated with the account.
     *
     * @return the email address of the account holder.
     */
    String getEmail();

    /**
     * Retrieves the current balance of the account.
     *
     * @return the balance of the account.
     */
    double getBalance();

    /**
     * Retrieves the currency used by the account.
     *
     * @return the currency of the account.
     */
    String getCurrency();

    /**
     * Retrieves the type of account (e.g., savings, checking).
     *
     * @return the type of the account.
     */
    String getAccountType();

    /**
     * Adds a card to the account with the specified card type.
     *
     * @param cardType the type of card to be added (e.g., "Credit", "Debit").
     */
    void addCard(String cardType);

    /**
     * Sets the minimum balance required for the account.
     *
     * @param minimumBalance the minimum balance required to maintain the account.
     */
    void setMinimumBalance(double minimumBalance);

    /**
     * Retrieves a list of cards associated with the account.
     *
     * @return a list of cards associated with the account.
     */
    ArrayList<Card> getCards();

    /**
     * Sets the balance of the account.
     *
     * @param balance the new balance for the account.
     */
    void setBalance(double balance);

    /**
     * Sets a new list of cards for the account.
     *
     * @param newCards a list of cards to be associated with the account.
     */
    void setCards(List<Card> newCards);

    /**
     * Sets an alias for the account, which can be used as a reference instead of the IBAN.
     *
     * @param alias the alias to be set for the account.
     */
    void setAlias(String alias);

    /**
     * Retrieves the alias of the account, if set.
     *
     * @return the alias of the account, or null if not set.
     */
    String getAlias();

    /**
     * Checks the status of the account's balance, ensuring it meets any required conditions.
     */
    void checkBalanceStatus();

    /**
     * Retrieves the minimum balance required for the account.
     *
     * @return the minimum balance required.
     */
    double getMinBalance();
}
