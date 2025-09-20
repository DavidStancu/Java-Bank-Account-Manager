package org.poo.BankCommandsSuite;

import org.poo.*;
import org.poo.AccountsSuite.Account;
import org.poo.AccountsSuite.ClassicAccount;
import org.poo.TransactionsSuite.TransactionFactory;
import org.poo.TransactionsSuite.TransactionTag;
import org.poo.fileio.CommandInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Command class responsible for processing a split payment across multiple accounts.
 * This class divides a total amount into equal parts and deducts the corresponding
 * amount
 * from each specified account. The transaction is recorded for each account involved.
 */
public class SplitPayment implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;

    /**
     * Constructs a SplitPayment command.
     *
     * @param users         the list of users in the system
     * @param commandInput  the input containing the details for the split payment
     * @param outputBuilder the output builder used to format and print results
     */
    public SplitPayment(final List<User> users, final CommandInput commandInput,
                        final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the SplitPayment command.
     * This method performs the following steps:
     * <ol>
     *     <li>Removes duplicate IBANs from the list of account IBANs.</li>
     *     <li>Checks if each account has sufficient funds for the split amount.</li>
     *     <li>If all accounts have sufficient funds, deducts the split amount from each
     *     account.</li>
     *     <li>Creates a transaction for each account involved in the split payment.</li>
     * </ol>
     */
    @Override
    public void execute() {
        String splitPaymentType = commandInput.getSplitPaymentType();
        List<String> accountIBANs = commandInput.getAccounts();
        double totalAmount = commandInput.getAmount();
        String currency = commandInput.getCurrency();
        int timestamp = commandInput.getTimestamp();

        if ("custom".equalsIgnoreCase(splitPaymentType)) {
            List<SplitNode> nullPaymentNodes = new ArrayList<>();

            for (String iban : accountIBANs) {
                SplitNode nullNode = new SplitNode(iban, 0.0, 0);
                nullPaymentNodes.add(nullNode);

                boolean accountFound = false;
                for (User user : users) {
                    Account account = BankTeller.findAccountByIBANOrAlias(user, iban);
                    if (account != null
                            && "CLASSIC".equalsIgnoreCase(account.getAccountType())) {
                        ClassicAccount classicAccount = (ClassicAccount) account;
                        RequestNode requestNode = new RequestNode(0.0, iban);
                        user.getRequestQueue().add(requestNode);
                        accountFound = true;
                        break;
                    }
                }
                if (!accountFound) {
                    outputBuilder.printStandardError("splitPayment",
                            "Account not found: " + iban, timestamp);
                }
            }

            for (User user : users) {
                user.addTransaction(TransactionFactory.createTransaction(
                        TransactionTag.NULL_PAYMENT,
                        timestamp,
                        accountIBANs,
                        0.0,
                        currency
                ));
            }
        } else {
            int numberOfAccounts = accountIBANs.size();
            double splitAmount = totalAmount / numberOfAccounts;

            for (String iban : accountIBANs) {
                processSplitPayment(iban, splitAmount, currency,
                        timestamp, false);
            }
        }
    }

    /**
     * Procesează plata pentru un cont specific.
     *
     * @param iban           IBAN-ul contului
     * @param amount         Suma care trebuie retrasă
     * @param currency       Moneda tranzacției
     * @param timestamp      Timpul tranzacției
     * @param addRequestNode Dacă trebuie adăugat un RequestNode în coadă
     */
    private void processSplitPayment(final String iban, final double amount,
                                     final String currency,
                                     final int timestamp,
                                     final boolean addRequestNode) {
        boolean accountFound = false;

        for (User user : users) {
            Account account = BankTeller.findAccountByIBANOrAlias(user, iban);
            if (account != null) {
                accountFound = true;

                double amountToWithdraw = amount;
                if (!account.getCurrency().equalsIgnoreCase(currency)) {
                    amountToWithdraw = BankTeller.convertCurrency(amount, currency,
                            account.getCurrency());
                }

                if (account.getBalance() < amountToWithdraw) {
                    outputBuilder.printStandardError("splitPayment",
                            "Insufficient funds for account: " + iban, timestamp);
                    user.addTransaction(TransactionFactory
                            .createTransaction(TransactionTag.FAILED_SPLIT_PAY,
                                    timestamp, List.of(iban), amountToWithdraw, currency));
                    return;
                }

                account.setBalance(account.getBalance() - amountToWithdraw);

                user.addTransaction(TransactionFactory.createTransaction(TransactionTag.SPLIT_PAY,
                        timestamp, List.of(iban), amount, currency));

                if (addRequestNode && "CLASSIC".equalsIgnoreCase(account.getAccountType())) {
                    ClassicAccount classicAccount = (ClassicAccount) account;
                    RequestNode requestNode = new RequestNode(amount, iban);
                    user.getRequestQueue().add(requestNode);
                }
                return;
            }
        }

        if (!accountFound) {
            outputBuilder.printStandardError("splitPayment",
                    "Account not found: " + iban, timestamp);
        }
    }
}
