package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.List;

/**
 * Command class responsible for setting the minimum balance for a specific account.
 * This class updates the minimum balance requirement of an account, identified by its IBAN.
 */
public class SetMinimumBalance implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;

    /**
     * Constructs a SetMinimumBalance command.
     *
     * @param users the list of users in the system
     * @param commandInput the input containing the details to set the minimum balance
     */
    public SetMinimumBalance(final List<User> users, final CommandInput commandInput) {
        this.users = users;
        this.commandInput = commandInput;
    }

    /**
     * Executes the SetMinimumBalance command.
     * This method updates the minimum balance requirement of the specified account,
     * identified by its IBAN. The minimum balance is set to the provided amount,
     * if the amount is greater than or equal to 0.
     */
    @Override
    public void execute() {
        String iban = commandInput.getAccount();
        double amount = commandInput.getAmount();

        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIBAN().equals(iban)) {
                    if (amount >= 0) {
                        account.setMinimumBalance(amount);
                    }
                    return;
                }
            }
        }
    }
}
