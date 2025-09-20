package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.OutputBuilder;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.List;

/**
 * Represents a command that deletes an account for a specified user.
 * The command deletes an account if the account's balance is zero.
 * If the deletion is successful, it prints a success message;
 * otherwise, it prints an error message.
 */
public class DeleteAccount implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;

    /**
     * Constructs a DeleteAccount command.
     *
     * @param users the list of users whose accounts may be deleted
     * @param commandInput the input data containing the account details and timestamp
     * @param outputBuilder the builder used to output the result of the operation
     */
    public DeleteAccount(final List<User> users, final CommandInput commandInput,
                         final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the command to delete an account from a user.
     * The account is only deleted if its balance is zero.
     * If the account is deleted successfully, a success message is printed.
     * If no matching account is found or the balance is non-zero, an error message is printed.
     */
    public void execute() {
        String email = commandInput.getEmail();
        String iban = commandInput.getAccount();
        int timestamp = commandInput.getTimestamp();

        for (User user : users) {
            if (user.getEmail().equals(email)) {
                List<Account> accounts = user.getAccounts();
                for (Account account : accounts) {
                    if (account.getIBAN().equals(iban) && account.getBalance() == 0) {
                        accounts.remove(account);
                        outputBuilder.printDeleteAccountSuccess(timestamp);
                        return;
                    }
                }
                outputBuilder.printDeleteAccountError(timestamp);
                return;
            }
        }
    }
}
