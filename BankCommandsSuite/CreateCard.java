package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.TransactionsSuite.TransactionFactory;
import org.poo.TransactionsSuite.TransactionTag;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.List;

/**
 * Represents a command that creates a card for a specified account and user.
 * This command checks the command type (either "createCard" or "createOneTimeCard")
 * and creates a card of the respective type. It adds the newly created card to
 * the user's account and records the creation transaction.
 */
public class CreateCard implements BankCommand {

    private final List<User> users;
    private final CommandInput commandInput;

    /**
     * Constructs a CreateCard command.
     *
     * @param users the list of users whose accounts the card will be created for
     * @param commandInput the input data containing the account details and command
     */
    public CreateCard(final List<User> users, final CommandInput commandInput) {
        this.users = users;
        this.commandInput = commandInput;
    }

    /**
     * Executes the command to create a card for a specified account.
     * Depending on the command type, it creates either a "CLASSIC" or "ONETIME" card,
     * adds the card to the account, and logs the transaction. If the account or user
     * cannot be found, the command will return without making changes.
     */
    public void execute() {
        String accountIBAN = commandInput.getAccount();
        String email = commandInput.getEmail();
        String commandType = commandInput.getCommand();
        String cardType;

        if ("createCard".equalsIgnoreCase(commandType)) {
            cardType = "CLASSIC";
        } else if ("createOneTimeCard".equalsIgnoreCase(commandType)) {
            cardType = "ONETIME";
        } else {
            return;
        }

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (account.getIBAN().equals(accountIBAN)) {
                        account.addCard(cardType);
                        String cardNumber = account.getCards().getLast().getCardNumber();

                        user.addTransaction(TransactionFactory
                                .createTransaction(TransactionTag.CARD_CREATED,
                                        commandInput.getTimestamp(), account.getIBAN(),
                                        cardNumber, user.getEmail()));
                        return;
                    }
                }
                return;
            }
        }
    }
}
