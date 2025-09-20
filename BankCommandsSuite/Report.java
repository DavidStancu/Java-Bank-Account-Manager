package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.OutputBuilder;
import org.poo.TransactionsSuite.Transaction;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Command class responsible for generating a report for a specified account.
 * This class filters transactions based on the provided timestamps and prints the account balance
 * along with the relevant transactions within the specified time range.
 */
public class Report implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;

    /**
     * Constructs a Report command.
     *
     * @param users the list of users whose accounts will be checked for the report
     * @param commandInput the input containing details of the report request
     * @param outputBuilder the builder responsible for printing the report
     */
    public Report(final List<User> users, final CommandInput commandInput,
                  final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the report command to generate a report for the specified account.
     * This method filters the transactions for the given account based on the provided
     * timestamp range and prints the account details along with the filtered transactions.
     */
    @Override
    public void execute() {
        String accountIBAN = commandInput.getAccount();
        int startTimestamp = commandInput.getStartTimestamp();
        int endTimestamp = commandInput.getEndTimestamp();
        int timestamp = commandInput.getTimestamp();
        boolean found = false;
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIBAN().equals(accountIBAN)) {
                    found = true;
                    List<Transaction> filteredTransactions = new ArrayList<>();
                    List<Transaction> transactions = user.getTransactions();

                    if (transactions != null && !transactions.isEmpty()) {
                        for (Transaction transaction : transactions) {
                            if (transaction.getTimestamp() >= startTimestamp
                                    && transaction.getTimestamp() <= endTimestamp) {
                                filteredTransactions.add(transaction);
                            }
                        }
                    }

                    if (!filteredTransactions.isEmpty()
                            || (filteredTransactions.isEmpty()
                            && account.getBalance() != 0)) {
                        outputBuilder.printReport(timestamp, accountIBAN,
                                account.getBalance(), account.getCurrency(),
                                filteredTransactions);
                    }
                    return;
                }
            }
            if (!found) {
                outputBuilder.printStandardError("report", "Account not found", timestamp);
                return;
            }

        }
    }
}
