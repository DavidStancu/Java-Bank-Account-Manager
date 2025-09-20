package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.AccountsSuite.SavingsAccount;
import org.poo.MagicNumbers;
import org.poo.OutputBuilder;
import org.poo.TransactionsSuite.TransactionFactory;
import org.poo.TransactionsSuite.TransactionTag;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.List;

public class AddInterest implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;

    /**
     * Constructs an AddInterest command.
     *
     * @param users the list of users to search for the account
     * @param commandInput the input data containing account and user details
     */
    public AddInterest(final List<User> users, final CommandInput commandInput,
                       final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the command to add interest to a specified account.
     * If the account or user cannot be found, the command will return without making changes.
     */
    @Override
    public void execute() {

        String accountIdentifier = commandInput.getAccount();
        int timestamp = commandInput.getTimestamp();


        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIBAN().equals(accountIdentifier)
                        || (account.getAlias() != null
                        && account.getAlias().equalsIgnoreCase(accountIdentifier))) {

                    if ("savings".equals(account.getAccountType())) {
                        double oldBalance = account.getBalance();
                        ((SavingsAccount) account).applyInterest();
                        double newBalance = account.getBalance();

                        double interestAmount = newBalance - oldBalance;
                        interestAmount = Math.round(interestAmount
                                * MagicNumbers.MND100) / MagicNumbers.MND100;

                        user.addTransaction(TransactionFactory.createTransaction(
                                TransactionTag.INTEREST,
                                interestAmount,
                                account.getCurrency(),
                                timestamp
                        ));
                        return;
                    } else {
                        outputBuilder.printStandardError(
                                "addInterest",
                                "This is not a savings account",
                                timestamp
                        );
                        return;
                    }
                }
            }
        }
    }
}
