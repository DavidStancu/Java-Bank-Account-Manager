package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.CardsSuite.Card;
import org.poo.OutputBuilder;
import org.poo.TransactionsSuite.TransactionFactory;
import org.poo.TransactionsSuite.TransactionTag;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.List;

/**
 * Represents a command that checks the status of a specific card and updates the
 * account status accordingly if the minimum balance condition is met.
 * This command iterates through users, their accounts, and the cards within those accounts
 * to check the status of a given card based on its number.
 * If the card is found and is active but the balance is below the minimum required,
 * the card's status is updated, and a transaction is recorded. If the card is not found,
 * an error message is printed.
 */
public class CheckCardStatus implements BankCommand {

    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;

    /**
     * Constructs a CheckCardStatus command.
     *
     * @param users the list of users whose accounts and cards are to be checked
     * @param commandInput the input data containing the card number and timestamp
     * @param outputBuilder the builder used for generating the output message
     */
    public CheckCardStatus(final List<User> users, final CommandInput commandInput,
                           final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the command to check the status of the card. If the card is active and
     * the account balance is below the minimum required, the card's status is updated to
     * frozen and a transaction is recorded. If the card is not found, an error message is printed.
     */
    @Override
    public void execute() {
        String cardNumber = commandInput.getCardNumber();

        boolean cardFound = false;

        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        cardFound = true;

                        String status = card.getStatus();
                        double balance = account.getBalance();
                        double minBalance = account.getMinBalance();
                        String description;

                        if ("active".equals(status) && balance <= minBalance) {
                            description = "You have reached the minimum amount"
                                    + " of funds, the card will be frozen";
                            user.addTransaction(TransactionFactory.createTransaction(
                                    TransactionTag.CARD_STAT,
                                    commandInput.getTimestamp(),
                                    description
                            ));
                            account.checkBalanceStatus();
                            break;
                        }
                    }
                }
            }
        }

        if (!cardFound) {
            outputBuilder.printCheckCardStatusError(commandInput.getTimestamp());
        }
    }
}
