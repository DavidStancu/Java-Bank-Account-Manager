package org.poo.AccountsSuite;

import org.poo.CardsSuite.Card;
import org.poo.CardsSuite.CardFactory;
import org.poo.MagicNumbers;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a savings bank account with basic account attributes,
 * interest rate management, and operations,
 * including managing account balance, cards, and setting up an alias
 * for the account.
 * The account type is fixed as "savings" and includes methods for card
 * handling, interest rate management,
 * and balance status checking.
 */
public class SavingsAccount implements Account {
    private String iban;
    private double balance;
    private double minBalance;
    private String email;
    private String currency;
    private String accountType;
    private List<Card> cards;
    private double interestRate;
    private String alias;

    /**
     * Constructs a new SavingsAccount with the provided currency and
     * interest rate.
     * The IBAN is generated automatically, and the initial balance
     * and minimum balance are set to 0.0.
     *
     * @param currency the currency of the account.
     * @param interestRate the interest rate for the savings account.
     */
    public SavingsAccount(final String currency, final double interestRate) {
        this.iban = Utils.generateIBAN();
        this.balance = 0.0;
        this.minBalance = 0.0;
        this.currency = currency;
        this.accountType = "savings";
        this.cards = new ArrayList<>();
        this.interestRate = interestRate;
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
     * Sets a new list of cards for the account.
     *
     * @param newCards the new list of cards to be associated with the account.
     */
    @Override
    public void setCards(final List<Card> newCards) {
        this.cards = newCards;
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
     * Retrieves the email associated with the account.
     *
     * @return the email address of the account holder.
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the type of account (fixed as "savings"
     * for this implementation).
     *
     * @return the account type.
     */
    @Override
    public String getAccountType() {
        return accountType;
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
            System.out.println("Error adding card: " + e.getMessage());
        }
    }

    /**
     * Retrieves the interest rate of the savings account.
     *
     * @return the interest rate for the savings account.
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets a new interest rate for the savings account.
     *
     * @param interestRate the new interest rate for the savings account.
     */
    public void setInterestRate(final double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Checks the balance status and updates the status of all cards associated with the account.
     * The card status will be set to "warning" if the balance exceeds
     * the minimum balance by more than 30,
     * and "frozen" if the balance is less than the minimum balance.
     */
    @Override
    public void checkBalanceStatus() {
        for (Card card : cards) {
            if (minBalance - balance >= MagicNumbers.MN30 && balance > minBalance) {
                card.setStatus("warning");
            } else if (balance < minBalance) {
                card.setStatus("frozen");
            }
            card.getStatus();
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

    /**
     * Applies the interest rate to the current balance of the savings account.
     * This method updates the balance by adding the calculated interest,
     * which is determined by multiplying the balance by the interest rate.
     */
    public void applyInterest() {
        balance += balance * interestRate;
    }

}
