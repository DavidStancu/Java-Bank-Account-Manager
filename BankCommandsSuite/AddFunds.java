package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.List;

import static org.poo.BankTeller.findAccountByIBANOrAlias;

/**
 * Represents a command to add funds to a specific account in the banking system.
 * The command validates the provided amount and IBAN/alias, and if valid, adds the
 * specified amount to the account's balance.
 */
public class AddFunds implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;

    /**
     * Constructs a new AddFunds command with the given list of users and command input.
     *
     * @param users the list of users whose accounts may be updated.
     * @param commandInput the input containing the account identifier (IBAN or alias)
     *                     and the amount to be added.
     */
    public AddFunds(final List<User> users, final CommandInput commandInput) {
        this.users = users;
        this.commandInput = commandInput;
    }

    /**
     * Executes the command to add funds to a specified account.
     * The account is identified by the IBAN or alias provided in the input. If the
     * account exists and the amount is positive, the specified amount is added to
     * the account's balance. No action is taken if the amount is less than or equal
     * to zero.
     */
    public void execute() {
        String iban = commandInput.getAccount();
        double amount = commandInput.getAmount();

        if (amount <= 0) {
            return;
        }

        for (User user : users) {
            Account account = findAccountByIBANOrAlias(user, iban);
            if (account != null) {
                account.setBalance(account.getBalance() + amount);
                return;
            }
        }
    }
}
