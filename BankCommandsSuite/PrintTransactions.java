package org.poo.BankCommandsSuite;

import org.poo.OutputBuilder;
import org.poo.TransactionsSuite.Transaction;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.List;

/**
 * Command class responsible for printing the transactions of a user.
 * It retrieves the user's transactions based on the email provided in the command input
 * and then outputs them using the provided OutputBuilder.
 */
public class PrintTransactions implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;

    /**
     * Constructs a PrintTransactions command.
     *
     * @param users the list of users to search for the specified email
     * @param commandInput the command input containing the user's email and timestamp
     * @param outputBuilder the output builder used to print the transactions
     */
    public PrintTransactions(final List<User> users, final CommandInput commandInput,
                             final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the command to print the transactions of the user with the specified email.
     * It searches for the user by email and retrieves their transactions. If the user is found
     * and has transactions, they are printed using the outputBuilder. If the user is not found
     * or they have no transactions, no output is generated.
     */
    @Override
    public void execute() {
        String email = commandInput.getEmail();
        int timestamp = commandInput.getTimestamp();

        User user = null;
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                user = u;
                break;
            }
        }

        if (user == null) {
            return;
        }

        List<Transaction> transactions = user.getTransactions();
        if (transactions == null || transactions.isEmpty()) {
            return;
        }

        outputBuilder.printTransactions(transactions, timestamp);
    }
}
