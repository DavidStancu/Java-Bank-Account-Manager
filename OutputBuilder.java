package org.poo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.AccountsSuite.Account;
import org.poo.CardsSuite.Card;
import org.poo.TransactionsSuite.*;

import java.util.*;

/**
 * The OutputBuilder class is responsible for generating JSON output for various banking operations,
 * including user details, account status, transactions, and error messages.
 */
public class OutputBuilder {
    private final ArrayNode output;
    public OutputBuilder() {
        this.output = new ObjectMapper().createArrayNode();
    }


    /**
     * Generates the JSON output for printing the list of users with their accounts and cards.
     *
     * @param users List of User objects.
     * @param timestamp The timestamp of the operation.
     */
    public void printUsers(final List<User> users, final int timestamp) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();
        outputNode.put("command", "printUsers");
        ArrayNode usersArray = objectMapper.createArrayNode();

        for (User user : users) {
            ObjectNode userNode = objectMapper.createObjectNode();
            userNode.put("firstName", user.getFirstName());
            userNode.put("lastName", user.getLastName());
            userNode.put("email", user.getEmail());
            ArrayNode accountsArray = objectMapper.createArrayNode();

            if (user.getAccounts() != null && !user.getAccounts().isEmpty()) {
                for (Account account : user.getAccounts()) {
                    ObjectNode accountNode = objectMapper.createObjectNode();
                    accountNode.put("IBAN", account.getIBAN());
                    accountNode.put("balance", account.getBalance());
                    accountNode.put("currency", account.getCurrency());
                    ArrayNode cardsArray = objectMapper.createArrayNode();
                    if (account.getCards() != null && !account.getCards().isEmpty()) {
                        for (Card card : account.getCards()) {
                            ObjectNode cardNode = objectMapper.createObjectNode();
                            cardNode.put("cardNumber", card.getCardNumber());
                            cardNode.put("status", card.getStatus());
                            cardsArray.add(cardNode);
                        }
                    }
                    accountNode.put("type", account.getAccountType());
                    accountNode.set("cards", cardsArray);
                    accountsArray.add(accountNode);
                }
            }

            userNode.set("accounts", accountsArray);
            usersArray.add(userNode);
        }

        outputNode.set("output", usersArray);
        outputNode.put("timestamp", timestamp);
        output.add(outputNode);
    }

    /**
     * Generates the JSON output for successful account deletion.
     *
     * @param timestamp The timestamp of the operation.
     */
    public void printDeleteAccountSuccess(final int timestamp) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();

        outputNode.put("command", "deleteAccount");

        ObjectNode successNode = objectMapper.createObjectNode();
        successNode.put("success", "Account deleted");
        successNode.put("timestamp", timestamp);

        outputNode.set("output", successNode);
        outputNode.put("timestamp", timestamp);
        output.add(outputNode);
    }

    /**
     * Generates the JSON output for an error during an online payment.
     *
     * @param description The error description.
     * @param timestamp The timestamp of the operation.
     */
    public void payOnlineError(final String description, final int timestamp) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode outputNode = objectMapper.createObjectNode();

        outputNode.put("command", "payOnline");

        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("description", description);
        errorNode.put("timestamp", timestamp);

        outputNode.set("output", errorNode);

        outputNode.put("timestamp", timestamp);
        output.add(outputNode);
    }

    /**
     * Generates JSON output for account creation.
     *
     * @param timestamp The timestamp of the operation.
     * @param description The description of the operation.
     * @param outputArray The output array to which the result is added.
     */
    public void printAccountCreated(final int timestamp, final String description,
                                    final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();

        outputNode.put("timestamp", timestamp);
        outputNode.put("description", description);

        outputArray.add(outputNode);
    }

    /**
     * Generates JSON output for an error during account deletion.
     *
     * @param timestamp The timestamp of the operation.
     */
    public void printDeleteAccountError(final int timestamp) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();

        outputNode.put("command", "deleteAccount");

        ObjectNode successNode = objectMapper.createObjectNode();
        successNode.put("error", "Account couldn't be deleted"
                + " - see org.poo.transactions for details");
        successNode.put("timestamp", timestamp);

        outputNode.set("output", successNode);
        outputNode.put("timestamp", timestamp);
        output.add(outputNode);
    }

    /**
     * Generates JSON output for an error during card status check.
     *
     * @param timestamp The timestamp of the operation.
     */
    public void printCheckCardStatusError(final int timestamp) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();

        outputNode.put("command", "checkCardStatus");

        ObjectNode outputContent = objectMapper.createObjectNode();
        outputContent.put("description", "Card not found");
        outputContent.put("timestamp", timestamp);

        outputNode.set("output", outputContent);

        outputNode.put("timestamp", timestamp);

        output.add(outputNode);
    }

    /**
     * Generates JSON output for a transfer transaction.
     *
     * @param timestamp The timestamp of the operation.
     * @param description The description of the transfer.
     * @param senderIBAN The IBAN of the sender.
     * @param receiverIBAN The IBAN of the receiver.
     * @param amount The amount transferred.
     * @param transferType The type of the transfer.
     * @param outputArray The output array to which the result is added.
     */
    public void printTransferType(final int timestamp, final String description,
                                  final String senderIBAN, final String receiverIBAN,
                                  final String amount, final String transferType,
                                  final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();

        outputNode.put("timestamp", timestamp);
        outputNode.put("description", description);
        outputNode.put("senderIBAN", senderIBAN);
        outputNode.put("receiverIBAN", receiverIBAN);
        outputNode.put("amount", amount);
        outputNode.put("transferType", transferType);

        outputArray.add(outputNode);
    }

    /**
     * Generates JSON output for a transaction when there are insufficient funds.
     *
     * @param timestamp The timestamp of the operation.
     * @param description The description of the transaction.
     * @param outputArray The output array to which the result is added.
     */
    private void printNoFundsTransaction(final int timestamp, final String description,
                                         final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode transactionNode = objectMapper.createObjectNode();

        transactionNode.put("description", description);
        transactionNode.put("timestamp", timestamp);

        outputArray.add(transactionNode);
    }

    /**
     * Generates JSON output for card creation.
     *
     * @param timestamp The timestamp of the operation.
     * @param description The description of the card creation.
     * @param accountIBAN The IBAN of the account the card belongs to.
     * @param cardNumber The number of the created card.
     * @param cardHolder The holder of the card.
     * @param outputArray The output array to which the result is added.
     */
    private void printCardCreated(final int timestamp, final String description,
                                  final String accountIBAN, final String cardNumber,
                                  final String cardHolder, final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode cardCreatedNode = objectMapper.createObjectNode();
        cardCreatedNode.put("account", accountIBAN);
        cardCreatedNode.put("card", cardNumber);
        cardCreatedNode.put("cardHolder", cardHolder);
        cardCreatedNode.put("description", description);
        cardCreatedNode.put("timestamp", timestamp);
        outputArray.add(cardCreatedNode);
    }

    /**
     * Generates JSON output for a transfer operation.
     *
     * @param timestamp The timestamp of the operation.
     * @param description The description of the transfer.
     * @param commerciant The name of the merchant.
     * @param amount The amount transferred.
     * @param outputArray The output array to which the result is added.
     */
    public void printTransfer(final int timestamp, final String description,
                              final String commerciant, final double amount,
                              final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode transferNode = objectMapper.createObjectNode();

        transferNode.put("amount", amount);
        transferNode.put("commerciant", commerciant);
        transferNode.put("description", description);
        transferNode.put("timestamp", timestamp);

        outputArray.add(transferNode);
    }

    /**
     * Prints card status information.
     *
     * @param timestamp the timestamp of the transaction
     * @param description the description of the card status
     * @param outputArray the array to add the output to
     */
    public void printCardStatus(final int timestamp, final String description,
                                final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode cardStatusNode = objectMapper.createObjectNode();

        cardStatusNode.put("description", description);
        cardStatusNode.put("timestamp", timestamp);

        outputArray.add(cardStatusNode);
    }

    /**
     * Prints details of a split payment transaction.
     *
     * @param timestamp the timestamp of the transaction
     * @param description the description of the split payment
     * @param involvedAccounts the list of involved account IBANs
     * @param amountPerAccount the amount per account
     * @param currency the currency used
     * @param outputArray the array to add the output to
     */
    private void printSplitPay(final int timestamp, final String description,
                               final List<String> involvedAccounts, final double amountPerAccount,
                               final String currency, final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode splitPaymentNode = objectMapper.createObjectNode();
        splitPaymentNode.put("timestamp", timestamp);
        splitPaymentNode.put("description", description);
        splitPaymentNode.put("currency", currency);
        splitPaymentNode.put("amount", amountPerAccount);

        ArrayNode accountsArray = splitPaymentNode.putArray("involvedAccounts");
        for (String account : involvedAccounts) {
            accountsArray.add(account);
        }
        outputArray.add(splitPaymentNode);
    }

    /**
     * Generates JSON output for a failed split payment transaction.
     *
     * @param timestamp The timestamp of the operation.
     * @param description The description of the failed split payment.
     * @param involvedAccounts The list of involved account IBANs.
     * @param failedAmount The amount that failed to be processed.
     * @param currency The currency of the failed transaction.
     * @param outputArray The JSON array to which the result is added.
     */
    private void printFailedSplitPay(final int timestamp, final String description,
                                     final List<String> involvedAccounts,
                                     final double failedAmount, final String currency,
                                     final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode transactionNode = objectMapper.createObjectNode();

        transactionNode.put("type", "FAILED_SPLIT_PAY");
        transactionNode.put("timestamp", timestamp);
        transactionNode.put("description", description);

        ArrayNode accountsArray = objectMapper.createArrayNode();
        for (String account : involvedAccounts) {
            accountsArray.add(account);
        }
        transactionNode.set("involvedAccounts", accountsArray);

        transactionNode.put("failedAmount", failedAmount);
        transactionNode.put("currency", currency);

        outputArray.add(transactionNode);
    }

    /**
     * Generates a JSON output for an underage transaction.
     *
     * @param timestamp The timestamp of the transaction.
     * @param description The description of the transaction.
     * @param outputArray The JSON array to which the result is added.
     */
    private void printUnderageTransaction(final int timestamp,
                                          final String description,
                                          final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode transactionNode = objectMapper.createObjectNode();

        transactionNode.put("description", description);
        transactionNode.put("timestamp", timestamp);

        outputArray.add(transactionNode);
    }

    /**
     * Builds an output JSON for a cash withdrawal error.
     *
     * @param description the error description
     * @param timestamp the timestamp of the error
     */
    public void cashWithdrawalError(final String description,
                                    final int timestamp) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();

        outputNode.put("command", "cashWithdrawal");

        ObjectNode outputDetails = objectMapper.createObjectNode();
        outputDetails.put("description", description);
        outputDetails.put("timestamp", timestamp);

        outputNode.set("output", outputDetails);
        outputNode.put("timestamp", timestamp);

        output.add(outputNode);
    }

    /**
     * Generates the JSON output for a plan upgrade transaction.
     *
     * @param planUpgrade The instance of the PlanUpgrade transaction containing details
     *                    such as timestamp, description, account IBAN, and the new plan
     *                    type.
     * @param outputArray The output array to which the transaction details are added.
     */
    private void printPlanUpgrade(final PlanUpgrade planUpgrade,
                                  final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode transactionNode = objectMapper.createObjectNode();

        transactionNode.put("timestamp", planUpgrade.getTimestamp());
        transactionNode.put("description", planUpgrade.getDescription());
        transactionNode.put("accountIBAN", planUpgrade.getAccountIBAN());
        transactionNode.put("newPlanType", planUpgrade.getNewPlanType());

        outputArray.add(transactionNode);
    }

    /**
     * Generates JSON output for a cash withdrawal transaction.
     *
     * @param withdrawCashTransaction The instance of WithdrawCash representing the
     *                               withdrawal transaction,
     *                                 containing details such as timestamp, description,
     *                                 and amount.
     * @param outputArray The output array to which the transaction details are added.
     */
    private void printWithdrawCash(final WithdrawCash withdrawCashTransaction,
                                   final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode transactionNode = objectMapper.createObjectNode();

        transactionNode.put("timestamp", withdrawCashTransaction.getTimestamp());
        transactionNode.put("description", withdrawCashTransaction.getDescription());
        transactionNode.put("amount", withdrawCashTransaction.getAmount());

        outputArray.add(transactionNode);
    }

    /**
     * Generates the JSON output for an interest transaction.
     *
     * @param interestTransaction The instance of Interest containing details
     *                             such as timestamp, amount, and currency.
     * @param outputArray The output array to which the transaction details are added.
     */
    private void printInterestTransaction(final Interest interestTransaction,
                                          final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode transactionNode = objectMapper.createObjectNode();

        transactionNode.put("timestamp", interestTransaction.getTimestamp());
        transactionNode.put("description", "Interest rate income");
        transactionNode.put("amount", interestTransaction.getAmount());
        transactionNode.put("currency", interestTransaction.getCurrency());

        outputArray.add(transactionNode);
    }

    /**
     * Generates JSON output for a transaction that does not belong to a classic category.
     *
     * @param timestamp The timestamp of the transaction.
     * @param description The description of the transaction.
     * @param outputArray The JSON array to which the transaction details are added.
     */
    private void printNoClassicTransaction(final int timestamp, String description,
                                           final ArrayNode outputArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode transactionNode = objectMapper.createObjectNode();

        transactionNode.put("timestamp", timestamp);
        transactionNode.put("description", description);

        outputArray.add(transactionNode);
    }
    /**
     * Prints a list of transactions.
     *
     * @param transfers the list of transactions
     * @param timestamp the timestamp of the transaction
     */
    public void printTransactions(final List<Transaction> transfers, final int timestamp) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();
        outputNode.put("command", "printTransactions");

        ArrayNode outputArray = objectMapper.createArrayNode();

        for (Transaction transaction : transfers) {
            String transactionTagString = transaction.getTransactionTag();
            TransactionTag transactionTag = TransactionTag.valueOf(transactionTagString);
            switch (transactionTag) {
                case ACCT_CREATED -> {
                    printAccountCreated(transaction.getTimestamp(), transaction.getDescription(),
                            outputArray);
                }
                case TRANSFER -> {
                    TransferType transferType = (TransferType) transaction;
                    printTransferType(transferType.getTimestamp(), transferType.getDescription(),
                            transferType.getSenderIBAN(), transferType.getReceiverIBAN(),
                            transferType.getAmount(),
                            transferType.getTransferType(), outputArray);
                }
                case NO_FUNDS -> {
                    NoFunds noFundsTransaction = (NoFunds) transaction;
                    printNoFundsTransaction(noFundsTransaction.getTimestamp(),
                            noFundsTransaction.getDescription(),
                            outputArray);
                }
                case CARD_CREATED -> {
                    CardCreated cardCreated = (CardCreated) transaction;
                    printCardCreated(cardCreated.getTimestamp(), cardCreated.getDescription(),
                            cardCreated.getAccountIBAN(),
                            cardCreated.getCardNumber(), cardCreated.getCardHolder(), outputArray);
                }
                case CARD_DELETED -> {
                    CardDeleted cardDeleted = (CardDeleted) transaction;
                    printCardCreated(cardDeleted.getTimestamp(), cardDeleted.getDescription(),
                            cardDeleted.getAccountIBAN(),
                            cardDeleted.getCardNumber(), cardDeleted.getCardHolder(),
                            outputArray);
                }
                case ONLN_PAYMENT -> {
                    OnlinePayment transfer = (OnlinePayment) transaction;
                    printTransfer(transfer.getTimestamp(),
                            transfer.getDescription(), transfer.getCommerciant(),
                            transfer.getAmount(), outputArray);
                }
                case CARD_STAT -> {
                    CardStatus cardStatusTransaction = (CardStatus) transaction;
                    printCardStatus(cardStatusTransaction.getTimestamp(),
                            cardStatusTransaction.getDescription(),
                            outputArray);
                }
                case SPLIT_PAY -> {
                    SplitPay splitPayment = (SplitPay) transaction;
                    printSplitPay(splitPayment.getTimestamp(), splitPayment.getDescription(),
                            splitPayment.getInvolvedAccounts(), splitPayment.getTotalAmount(),
                            splitPayment.getCurrency(),
                            outputArray);
                }
                case UNDERAGE -> {
                    Underage underageTransaction = (Underage) transaction;
                    printUnderageTransaction(underageTransaction.getTimestamp(),
                            underageTransaction.getDescription(), outputArray);
                }
                case PLAN_UPGRADED -> {
                    printPlanUpgrade((PlanUpgrade) transaction, outputArray);
                }
                case WITHDRAW_CASH -> {
                    WithdrawCash withdrawCashTransaction = (WithdrawCash) transaction;
                    printWithdrawCash(withdrawCashTransaction, outputArray);
                }
                case INTEREST -> {
                    Interest interestTransaction = (Interest) transaction;
                    printInterestTransaction(interestTransaction, outputArray);
                }
                case NO_CLASSIC -> {
                    NoClassic noClassicTransaction = (NoClassic) transaction;
                    printNoClassicTransaction(noClassicTransaction.getTimestamp(),
                            noClassicTransaction.getDescription(), outputArray);
                }
                case FAILED_SPLIT_PAY -> {
                    FailedSplitPay failedSplitPayment = (FailedSplitPay) transaction;
                    printFailedSplitPay(failedSplitPayment.getTimestamp(),
                            failedSplitPayment.getDescription(),
                            failedSplitPayment.getInvolvedAccounts(),
                            failedSplitPayment.getFailedAmount(),
                            failedSplitPayment.getCurrency(), outputArray);
                }
                default -> {

                }
            }
        }

        outputNode.set("output", outputArray);
        outputNode.put("timestamp", timestamp);
        output.add(outputNode);
    }

    /**
     * Prints a report containing account details and transactions.
     *
     * @param timestamp the timestamp of the transaction
     * @param iban the IBAN of the account
     * @param balance the balance of the account
     * @param currency the currency of the account
     * @param transactions the list of transactions
     */
    public void printReport(final int timestamp, final String iban, final double balance,
                            final String currency, final List<Transaction> transactions) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();

        outputNode.put("command", "report");

        ObjectNode reportNode = objectMapper.createObjectNode();
        reportNode.put("IBAN", iban);
        reportNode.put("balance", balance);
        reportNode.put("currency", currency);

        ArrayNode transactionsArray = objectMapper.createArrayNode();

        for (Transaction transaction : transactions) {
            TransactionTag transactionTag = TransactionTag.valueOf(transaction.getTransactionTag());

            switch (transactionTag) {
                case ACCT_CREATED -> {
                    AccountCreated accountCreated = (AccountCreated) transaction;
                    printAccountCreated(accountCreated.getTimestamp(),
                            accountCreated.getDescription(),
                            transactionsArray);
                }
                case TRANSFER -> {
                    TransferType transferType = (TransferType) transaction;
                    printTransferType(transferType.getTimestamp(), transferType.getDescription(),
                            transferType.getSenderIBAN(), transferType.getReceiverIBAN(),
                            transferType.getAmount(), transferType.getTransferType(),
                            transactionsArray);
                }
                case CARD_CREATED -> {
                    CardCreated cardCreated = (CardCreated) transaction;
                    printCardCreated(cardCreated.getTimestamp(), cardCreated.getDescription(),
                            cardCreated.getAccountIBAN(), cardCreated.getCardNumber(),
                            cardCreated.getCardHolder(), transactionsArray);
                }
                case ONLN_PAYMENT -> {
                    OnlinePayment onlinePayment = (OnlinePayment) transaction;
                    printTransfer(onlinePayment.getTimestamp(), onlinePayment.getDescription(),
                            onlinePayment.getCommerciant(),
                            onlinePayment.getAmount(), transactionsArray);
                }
                default -> {
                }
            }
        }

        reportNode.set("transactions", transactions.isEmpty() ? objectMapper.createArrayNode()
                : transactionsArray);

        outputNode.set("output", reportNode);
        outputNode.put("timestamp", timestamp);

        output.add(outputNode);
    }

    /**
     * Builds a spendings report and adds it as a JSON object to the output.
     * The report includes IBAN, balance, currency, sorted list of commerciants with their totals,
     * a transactions array, and a timestamp.
     *
     * @param iban The IBAN of the account for which the report is generated.
     * @param balance The current balance of the account.
     * @param currency The currency of the account balance.
     * @param commerciantsTotals A map containing commerciants and their respective total spendings.
     * @param transactionsArray An ArrayNode representing the transactions associated with the account.
     * @param commandTimestamp The timestamp indicating when the command was executed.
     */
    public void buildSpendingsReport(final String iban, final double balance,
                                     final String currency,
                                     final Map<String, Double> commerciantsTotals,
                                     final ArrayNode transactionsArray,
                                     final int commandTimestamp) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();

        outputNode.put("IBAN", iban);
        outputNode.put("balance", balance);
        outputNode.put("currency", currency);

        List<Map.Entry<String, Double>> sortedCommerciants =
                new ArrayList<>(commerciantsTotals.entrySet());
        sortedCommerciants.sort((entry1, entry2) -> {
            int nameComparison = entry1.getKey().compareTo(entry2.getKey());
            if (nameComparison != 0) {
                return nameComparison;
            }
            return Double.compare(entry2.getValue(), entry1.getValue());
        });

        ArrayNode commerciantsArray = objectMapper.createArrayNode();
        for (Map.Entry<String, Double> entry : sortedCommerciants) {
            ObjectNode commerciantNode = objectMapper.createObjectNode();
            commerciantNode.put("commerciant", entry.getKey());
            commerciantNode.put("total", entry.getValue());

            commerciantsArray.add(commerciantNode);
        }

        outputNode.set("commerciants", commerciantsArray);
        outputNode.set("transactions", transactionsArray);

        ObjectNode finalOutputNode = objectMapper.createObjectNode();
        finalOutputNode.put("command", "spendingsReport");
        finalOutputNode.set("output", outputNode);
        finalOutputNode.put("timestamp", commandTimestamp);

        output.add(finalOutputNode);
    }

    /**
     * Logs an error message with the provided command, description, and timestamp.
     *
     * @param command the command associated with the error
     * @param description a brief description of the error
     * @param timestamp the timestamp indicating when the error occurred
     */
    public void printStandardError(final String command, final String description, final int timestamp) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode outputNode = objectMapper.createObjectNode();

        outputNode.put("command", command);

        ObjectNode outputContent = objectMapper.createObjectNode();
        outputContent.put("description", description);
        outputContent.put("timestamp", timestamp);

        outputNode.set("output", outputContent);

        outputNode.put("timestamp", timestamp);

        output.add(outputNode);
    }

    /**
     * Gets the output array of all transactions.
     *
     * @return the output array
     */
    public ArrayNode getOutput() {
        return output;
    }
}
