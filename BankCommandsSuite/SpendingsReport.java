package org.poo.BankCommandsSuite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.AccountsSuite.Account;
import org.poo.BankTeller;
import org.poo.CommerciantsSuite.CommerciantTransaction;
import org.poo.OutputBuilder;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.*;

/**
 * The SpendingsReport class is a specific implementation of the {@link BankCommand} interface,
 * designed to generate a spendings report for a given account within a specified time range.
 *
 * This report includes details about transactions made with commerciants, their timestamps,
 * descriptions, amounts, and the commerciants involved. Additionally, the total spendings
 * with each commerciant are calculated and presented as part of the report.
 *
 * The report is generated based on the provided input, including the account's IBAN,
 * start and end timestamps for the transactions to be included, and a timestamp for the
 * command execution.
 * The output of the report is built using the OutputBuilder class.
 *
 * The class relies on helper methods from the BankTeller to retrieve account and transaction
 * data.
 */
public class SpendingsReport implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;

    public SpendingsReport(final List<User> users, final CommandInput commandInput,
                         final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the spendings report command by analyzing transactions associated
     * with a specific bank account over a defined time period and generating a
     * detailed report.
     *
     * The method performs the following:
     * 1. Retrieves the IBAN, start timestamp, end timestamp, and command timestamp
     *    from the input command.
     * 2. Verifies the existence of the account associated with the provided IBAN.
     *    If no account is found, an error message is output.
     * 3. Fetches all merchant transactions and filters them for the account within
     *    the specified timeframe.
     * 4. Accumulates the totals spent at each merchant and constructs a structured
     *    representation of the matching transactions.
     * 5. Generates and outputs the report, including account details, total spending
     *    per merchant, transaction data, and the current balance.
     */
    @Override
    public void execute() {
        String iban = commandInput.getAccount();
        int startTimestamp = commandInput.getStartTimestamp();
        int endTimestamp = commandInput.getEndTimestamp();
        int commandTimestamp = commandInput.getTimestamp();

        Account account = BankTeller.findAccountByIBAN(users, iban);
        if (account == null) {
            outputBuilder.printStandardError("spendingsReport",
                    "Account not found", commandTimestamp);
            return;
        }

        List<CommerciantTransaction> allTransactions = BankTeller.getCommerciantTransactions();
        Map<String, Double> commerciantsTotals = new HashMap<>();
        ArrayNode transactionsArray = new ObjectMapper().createArrayNode();

        for (CommerciantTransaction transaction : allTransactions) {
            if (transaction.getAccountIBAN().equals(iban)
                    && transaction.getTimestamp() >= startTimestamp
                    && transaction.getTimestamp() <= endTimestamp) {

                ObjectNode transactionNode = new ObjectMapper().createObjectNode();
                transactionNode.put("timestamp", transaction.getTimestamp());
                transactionNode.put("description", "Card payment");
                transactionNode.put("amount", transaction.getAmount());
                transactionNode.put("commerciant", transaction.getCommerciant());

                transactionsArray.add(transactionNode);

                commerciantsTotals.merge(
                        transaction.getCommerciant(),
                        transaction.getAmount(),
                        Double::sum
                );
            }
        }

        outputBuilder.buildSpendingsReport(
                account.getIBAN(),
                account.getBalance(),
                account.getCurrency(),
                commerciantsTotals,
                transactionsArray,
                commandTimestamp
        );
    }
}
