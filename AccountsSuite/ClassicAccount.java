package org.poo.AccountsSuite;

import org.poo.CardsSuite.Card;
import org.poo.CardsSuite.CardFactory;
import org.poo.MagicNumbers;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a classic bank account with basic account attributes and operations,
 * including managing account balance, cards, and setting up an alias for the account.
 * The account type is fixed as "classic" and includes methods for card handling and
 * balance status checking.
 */
public class ClassicAccount implements Account {
    private String iban;
    private double balance;
    private double minBalance;
    private String email;
    private String currency;
    private String accountType;
    private List<Card> cards;
    private String alias;

    /**
     * Constructs a new ClassicAccount with the provided email and currency.
     * The IBAN is generated automatically and the initial balance
     * and minimum balance are set to 0.0.
     *
     * @param email the email address of the account holder.
     * @param currency the currency of the account.
     */
    public ClassicAccount(final String email, final String currency) {
        this.iban = Utils.generateIBAN();
        this.balance = 0.0;
        this.minBalance = 0.0;
        this.email = email;
        this.currency = currency;
        this.accountType = "classic";
        this.cards = new ArrayList<>();
        this.alias = null;
    }

    /**
     * Retrieves the alias of the account, if set.
     *
     * @return the alias of the account, or null if not set.
     */
    @Override
    public String getAlias() {
        return alias;
    }

    /**
     * Sets a new alias for the account.
     *
     * @param alias the alias to be set for the account.
     */
    @Override
    public void setAlias(final String alias) {
        this.alias = alias;
    }

    /**
     * Retrieves a copy of the list of cards associated with the account.
     *
     * @return a new list containing all the cards associated with the account.
     */
    @Override
    public ArrayList<Card> getCards() {
        return new ArrayList<>(cards);
    }

    /**
     * Sets a new minimum balance for the account.
     *
     * @param minimumBalance the minimum balance to be set for the account.
     */
    public void setMinimumBalance(final double minimumBalance) {
        if (minimumBalance >= 0) {
            this.minBalance = minimumBalance;
        }
    }

    /**
     * Sets a new list of cards for the account.
     *
     * @param newCards the new list of cards to be associated with the account.
     */
    @Override
    public void setCards(final List<Card> newCards) {
        this.cards = newCards;
    }

    /**
     * Retrieves the IBAN of the account.
     *
     * @return the IBAN of the account.
     */
    @Override
    public String getIBAN() {
        return iban;
    }

    /**
     * Retrieves the current balance of the account.
     *
     * @return the balance of the account.
     */
    @Override
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the account.
     *
     * @param balance the new balance for the account.
     */
    @Override
    public void setBalance(final double balance) {
        this.balance = balance;
    }

    /**
     * Retrieves the currency of the account.
     *
     * @return the currency of the account.
     */
    @Override
    public String getCurrency() {
        return currency;
    }

    /**
     * Retrieves the email associated with the account.
     *
     * @return the email address of the account holder.
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the type of account (fixed as "classic" for this implementation).
     *
     * @return the account type.
     */
    @Override
    public String getAccountType() {
        return accountType;
    }

    /**
     * Adds a new card to the account based on the specified card type.
     * The card is created using the CardFactory.
     *
     * @param cardType the type of card to be created and added (e.g., "Credit", "Debit").
     */
    @Override
    public void addCard(final String cardType) {
        try {
            Card newCard = CardFactory.createCard(cardType);
            cards.add(newCard);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Checks the balance status and updates the status of all cards associated with the account.
     * The card status will be set to "warning" if the balance exceeds the
     * minimum balance by more than 30,
     * and "frozen" if the balance is less than or equal to the minimum balance.
     */
    @Override
    public void checkBalanceStatus() {
        for (Card card : cards) {
            if (balance - minBalance >= MagicNumbers.MN30 && balance > minBalance) {
                card.setStatus("warning");
            } else if (balance <= minBalance) {
                card.setStatus("frozen");
            }
        }
    }

    /**
     * Retrieves the minimum balance required for the account.
     *
     * @return the minimum balance required for the account.
     */
    @Override
    public double getMinBalance() {
        return minBalance;
    }

}
