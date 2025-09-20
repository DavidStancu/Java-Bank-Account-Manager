package org.poo.BankCommandsSuite;

import org.poo.*;
import org.poo.AccountsSuite.Account;
import org.poo.TransactionsSuite.TransactionFactory;
import org.poo.TransactionsSuite.TransactionTag;
import org.poo.fileio.CommandInput;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * Represents a command for withdrawing funds from a savings account to a classic account.
 */
public class WithdrawSavings implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;

    /**
     * Constructs a WithdrawSavings command.
     *
     * @param users the list of users to search for the specified account
     * @param commandInput the input data containing the withdrawal details
     * @param outputBuilder the output builder used for handling error messages
     */
    public WithdrawSavings(final List<User> users, final CommandInput commandInput,
                           final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the withdrawal command.
     * Transfers funds from a savings account to a classic account if conditions are met.
     */
    @Override
    public void execute() {
        String savingsIban = commandInput.getAccount();
        double amount = commandInput.getAmount();
        String currency = commandInput.getCurrency();
        int timestamp = commandInput.getTimestamp();

        for (User user : users) {
            Account savingsAccount = null;
            Account targetClassicAccount = null;
            List<Account> accounts = user.getAccounts();
            if (accounts == null) {
                continue;
            }

            if (accounts.isEmpty()) {
                continue;
            }

            for (Account account : accounts) {
                if (account.getIBAN().equals(savingsIban)
                        && "savings".equals(account.getAccountType())) {
                    savingsAccount = account;
                } else if (account.getCurrency().equalsIgnoreCase(currency)
                        && targetClassicAccount == null) {
                    targetClassicAccount = account;
                }
            }

            if (savingsAccount == null) {
                return;
            }

            if (targetClassicAccount == null) {
                user.addTransaction(TransactionFactory
                        .createTransaction(TransactionTag.NO_CLASSIC,
                                commandInput.getTimestamp(),
                                "You do not have a classic account."));
                return;
            }

            if (!isUserOldEnough(user)) {
                user.addTransaction(TransactionFactory.createTransaction(
                        TransactionTag.UNDERAGE,
                        timestamp,
                        "You don't have the minimum age required."));
                return;
            }

            double convertedAmount = amount;
            if (!savingsAccount.getCurrency().equalsIgnoreCase(currency)) {
                convertedAmount = BankTeller.convertCurrency(amount,
                        currency, savingsAccount.getCurrency());
            }

            if (savingsAccount.getBalance() < convertedAmount) {
                return;
            }

            savingsAccount.setBalance(savingsAccount.getBalance()
                    - convertedAmount);
            targetClassicAccount.setBalance(targetClassicAccount.getBalance()
                    + amount);
            return;
        }
    }

    /**
     * Checks if the user is old enough to withdraw funds (minimum age: 21 years).
     *
     * @param user the user to check
     * @return true if the user is at least 21 years old, false otherwise
     */
    private boolean isUserOldEnough(final User user) {
        LocalDate birthDate = user.getBirthDate();
        if (birthDate == null) {
            return false;
        }
        LocalDate referenceDate = LocalDate.of(MagicNumbers.MN2024,
                MagicNumbers.MN12, MagicNumbers.MN15);
        return Period.between(birthDate, referenceDate).getYears() >= MagicNumbers.MN21;
    }
}
