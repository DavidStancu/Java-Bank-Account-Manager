package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.AccountsSuite.AccountsFactory;
import org.poo.TransactionsSuite.TransactionFactory;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.List;

import static org.poo.TransactionsSuite.TransactionTag.ACCT_CREATED;

/**
 * Represents a command to add a new account to a user in the banking system.
 * This command validates the provided account details, creates the account,
 * and associates it with the specified user. If the account is successfully created,
 * a transaction for account creation is also recorded.
 */
public class AddAccount implements BankCommand {
    private final List<User> users;
    private final CommandInput command;

    /**
     * Constructs a new AddAccount command with the given list of users and command input.
     *
     * @param users the list of users to which the account may be added.
     * @param command the input containing details for the new account.
     */
    public AddAccount(final List<User> users, final CommandInput command) {
        this.users = users;
        this.command = command;
    }

    /**
     * Executes the command to add a new account to the user.
     * The account details are extracted from the command, and the account is created.
     * If the account type is "SAVINGS", the interest rate is also required. After successfully
     * creating the account, it is added to the user's account list, and a new account creation
     * transaction is recorded.
     */
    public void execute() {
        String email = command.getEmail();
        String accountType = command.getAccountType();
        String currency = command.getCurrency();
        Double interestRate = command.getInterestRate();
        int timestamp = command.getTimestamp();

        for (User user : users) {
            if (user.getEmail().equals(email.trim())) {
                Account newAccount;
                try {
                    if ("SAVINGS".equalsIgnoreCase(accountType)) {
                        if (interestRate == null) {
                            return;
                        }
                        newAccount = AccountsFactory.createAccount(accountType,
                                email, currency, interestRate);
                    } else {
                        newAccount = AccountsFactory.createAccount(accountType,
                                email, currency);
                    }
                } catch (IllegalArgumentException e) {
                    return;
                }

                user.addAccount(newAccount);
                user.addTransaction(TransactionFactory.createTransaction(ACCT_CREATED, timestamp));
                return;
            }
        }
    }

}
