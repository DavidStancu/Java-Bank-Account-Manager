package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.List;

/**
 * Command class responsible for setting an alias to a specific account of a user.
 * This class updates the alias of the specified account, identified by the
 * user's email and account IBAN.
 */
public class SetAlias implements BankCommand {
    private final List<User> users;
    private final CommandInput command;

    /**
     * Constructs a SetAlias command.
     *
     * @param users the list of users in the system
     * @param command the input containing the details to set the alias
     */
    public SetAlias(final List<User> users, final CommandInput command) {
        this.users = users;
        this.command = command;
    }

    /**
     * Executes the SetAlias command.
     * This method updates the alias of the account belonging to the specified user
     * by matching the user's email
     * and the account's IBAN. If a match is found, the account's alias is
     * updated with the provided alias.
     */
    @Override
    public void execute() {
        String email = command.getEmail();
        String iban = command.getAccount();
        String alias = command.getAlias();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                for (Account account : user.getAccounts()) {
                    if (account.getIBAN().equals(iban)) {
                        account.setAlias(alias);
                        return;
                    }
                }
            }
        }
    }
}
