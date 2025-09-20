package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.ClassicAccount;
import org.poo.RequestNode;
import org.poo.OutputBuilder;
import org.poo.User;
import org.poo.fileio.CommandInput;
import org.poo.TransactionsSuite.CustomSplit;
import java.util.List;

/**
 * Represents a command that allows a user to accept a split payment within the banking system.
 * This command verifies the user's email, checks for the existence of a classic account,
 * ensures there are pending requests in the user's request queue, and processes the
 * requested payment
 * if the balance is sufficient.
 */
public class AcceptSplitPayment implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;
    private final CustomSplit customSplitTransaction;

    public AcceptSplitPayment(final List<User> users, final CommandInput commandInput,
                              final OutputBuilder outputBuilder,
                              final CustomSplit customSplitTransaction) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
        this.customSplitTransaction = customSplitTransaction;
    }

    /**
     * Executes the command to accept a split payment for a user. This method performs
     * the following steps:
     *
     * 1. Retrieves the email and timestamp from the command input.
     * 2. Finds the user associated with the provided email.
     * 3. Checks if the user has a classic account.
     * 4. Validates if there are any pending payment requests in the user's request queue.
     * 5. Processes the first request in the queue if the classic account has sufficient balance,
     *    deducting the requested amount and updating the corresponding request node with
     *    the amount and timestamp.
     *
     * This method ensures that only valid users and payment requests are processed
     * while maintaining account integrity.
     */
    @Override
    public void execute() {
        String email = commandInput.getEmail();
        int timestamp = commandInput.getTimestamp();

        User user = users.stream()
                .filter(u -> u.getEmail().trim().equalsIgnoreCase(email.trim()))
                .findFirst()
                .orElse(null);

        if (user == null) {
            return;
        }

        ClassicAccount classicAccount = user.getAccounts().stream()
                .filter(acc -> "CLASSIC".equalsIgnoreCase(acc.getAccountType()))
                .map(acc -> (ClassicAccount) acc)
                .findFirst()
                .orElse(null);

        if (classicAccount == null) {
            return;
        }

        if (user.getRequestQueue().isEmpty()) {
            return;
        }

        RequestNode requestNode = user.getRequestQueue().remove(0);
        double requestedAmount = requestNode.getAmount();

        if (classicAccount.getBalance() < requestedAmount) {
            return;
        }

        classicAccount.setBalance(classicAccount.getBalance() - requestedAmount);
        requestNode.setAmountBuffer(requestedAmount);
        requestNode.setTimestamp(timestamp);

    }

}
