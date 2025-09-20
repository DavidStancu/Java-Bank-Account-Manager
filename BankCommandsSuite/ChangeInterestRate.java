package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.AccountsSuite.SavingsAccount;
import org.poo.OutputBuilder;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.List;

/**
 * Represents a command for changing the interest rate of a savings account.
 */
public class ChangeInterestRate implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;

    /**
     * Constructs a ChangeInterestRate command.
     *
     * @param users the list of users whose accounts will be searched
     * @param commandInput the input data containing the account and new interest rate
     * @param outputBuilder the output builder for handling error messages
     */
    public ChangeInterestRate(final List<User> users, final CommandInput commandInput,
                              final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the command to change the interest rate of a savings account.
     */
    @Override
    public void execute() {
        String accountIBAN = commandInput.getAccount();
        double newInterestRate = commandInput.getInterestRate();
        int timestamp = commandInput.getTimestamp();

        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIBAN().equals(accountIBAN)) {
                    if ("savings".equals(account.getAccountType())) {
                        ((SavingsAccount) account).setInterestRate(newInterestRate);
                        return;
                    } else {
                        outputBuilder.printStandardError(
                                "changeInterestRate",
                                "This is not a savings account",
                                timestamp
                        );
                        return;
                    }
                }
            }
        }

        outputBuilder.printStandardError(
                "changeInterestRate",
                "Account not found",
                timestamp
        );
    }
}
