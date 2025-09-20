package org.poo.BankCommandsSuite;

import org.poo.*;
import org.poo.AccountsSuite.Account;
import org.poo.CardsSuite.Card;
import org.poo.CardsSuite.OneTimeCard;
import org.poo.CashbackSuite.CashBackRules;
import org.poo.CommerciantsSuite.CommerciantTransaction;
import org.poo.PaymentPlanSuite.PaymentPlanManager;
import org.poo.TransactionsSuite.TransactionFactory;
import org.poo.TransactionsSuite.TransactionTag;
import org.poo.fileio.CommandInput;

import java.util.List;

import static org.poo.TransactionsSuite.TransactionTag.ONLN_PAYMENT;

/**
 * Represents a command for making an online payment using a user's card.
 * The payment is processed by checking the card's status, converting currencies if necessary,
 * and updating the account balance. A transaction is recorded for each payment attempt.
 */
public class PayOnline implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;

    /**
     * Constructs a PayOnline command.
     *
     * @param users         the list of users whose accounts may contain the card to be used for
     *                      the payment
     * @param commandInput  the input data containing the payment details
     * @param outputBuilder the output builder used for handling error messages
     *                      and transaction outputs
     */
    public PayOnline(final List<User> users, final CommandInput commandInput,
                     final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the command to make an online payment.
     * The payment is made using the provided card and account information.
     * The account balance is updated accordingly, and a transaction is recorded.
     * Currency conversion is performed if the payment currency differs from the account currency.
     * If the card is frozen or funds are insufficient, appropriate error transactions are created.
     * If the card is not found, an error message is returned.
     */
    public void execute() {
        String email = commandInput.getEmail();
        String cardNumber = commandInput.getCardNumber();
        double amount = commandInput.getAmount();
        String currency = commandInput.getCurrency();
        String description = commandInput.getDescription();
        String commerciant = commandInput.getCommerciant();
        int timestamp = commandInput.getTimestamp();

        if (amount == 0.0) {
            return;
        }

        for (User user : users) {
            if (user.getEmail().trim().equalsIgnoreCase(email.trim())) {
                for (Account account : user.getAccounts()) {
                    for (Card card : account.getCards()) {
                        if (card.getCardNumber().trim().equals(cardNumber.trim())) {

                            double convertedAmount = amount;
                            if (card.getStatus().equals("frozen")) {
                                user.addTransaction(TransactionFactory
                                        .createTransaction(TransactionTag.CARD_STAT,
                                                commandInput.getTimestamp(),
                                                "The card is frozen"));
                                return;
                            }

                            if (!account.getCurrency().equalsIgnoreCase(currency)) {
                                convertedAmount = BankTeller.convertCurrency(amount,
                                        currency, account.getCurrency());
                            }

                            double convertedForCashback = convertedAmount;
                            if (!account.getCurrency().equalsIgnoreCase("RON")) {
                                convertedForCashback = BankTeller.convertCurrency(convertedAmount,
                                        account.getCurrency(), "RON");
                            }

                            ExtendedCommerciant commerciantAcc = ExtendedCommerciant
                                    .findCommerciantByName(commerciant);
                            double cashbackAmount = 0;
                            if (commerciantAcc != null) {
                                String cashbackStrategy = commerciantAcc
                                                          .getCashbackStrategy().toLowerCase();

                                if ("numberoftransactions".equals(cashbackStrategy)) {
                                    cashbackAmount = CashBackRules.calculateCashback(
                                            account.getIBAN(), user, commerciantAcc,
                                            convertedForCashback);
                                } else if ("spendingthreshold".equals(cashbackStrategy)) {
                                    cashbackAmount = CashBackRules.calculateCashback(
                                            account.getIBAN(), user, commerciantAcc,
                                            convertedForCashback);
                                }
                            }

                            double finalCashbackAmount = cashbackAmount;
                            if (!account.getCurrency().equalsIgnoreCase("RON")
                                    && cashbackAmount > 0) {
                                finalCashbackAmount = BankTeller.convertCurrency(cashbackAmount,
                                        "RON", account.getCurrency());
                            }

                            double transactionFeeRON = 0;
                            if (user.getPaymentPlan().isFeeApplicable(convertedForCashback)) {
                                transactionFeeRON = user.getPaymentPlan()
                                                    .getTransactionFee() * convertedForCashback;
                            }

                            double finalTransactionFee = transactionFeeRON;
                            if (!account.getCurrency().equalsIgnoreCase("RON")
                                    && transactionFeeRON > 0) {
                                finalTransactionFee = BankTeller.convertCurrency(transactionFeeRON,
                                        "RON", account.getCurrency());
                            }

                            convertedAmount += finalTransactionFee;

                            if (account.getBalance() >= convertedAmount) {
                                account.setBalance(account.getBalance() - convertedAmount);

                                if (finalCashbackAmount > 0) {
                                    account.setBalance(account.getBalance() + finalCashbackAmount);
                                }

                                CommerciantTransaction commerciantTransaction =
                                        new CommerciantTransaction(account.getIBAN(),
                                                convertedAmount - finalTransactionFee,
                                                timestamp,
                                                description, commerciant);
                                BankTeller.addTransaction(commerciantTransaction);

                                if (user.canAutoUpgradeToGold()) {
                                    user.setPaymentPlan(PaymentPlanManager.getPlan("gold"));
                                }

                                user.addTransaction(TransactionFactory
                                        .createTransaction(ONLN_PAYMENT,
                                                timestamp, description,
                                                convertedAmount - finalTransactionFee,
                                                commerciant));

                                if ("ONETIME".equals(card.getTag())) {
                                    String oldCardNumber = card.getCardNumber();

                                    user.addTransaction(TransactionFactory.createTransaction(
                                            TransactionTag.CARD_DELETED,
                                            commandInput.getTimestamp(),
                                            account.getIBAN(),
                                            oldCardNumber,
                                            user.getEmail()
                                    ));

                                    ((OneTimeCard) card).changeCardNumber();

                                    String newCardNumber = card.getCardNumber();

                                    user.addTransaction(TransactionFactory.createTransaction(
                                            TransactionTag.CARD_CREATED,
                                            commandInput.getTimestamp(),
                                            account.getIBAN(),
                                            newCardNumber,
                                            user.getEmail()
                                    ));
                                }

                            } else {
                                user.addTransaction(TransactionFactory
                                        .createTransaction(TransactionTag.NO_FUNDS,
                                                timestamp, "Insufficient funds"));
                            }
                            return;
                        }
                    }
                }
            }
        }
        outputBuilder.payOnlineError("Card not found", timestamp);
    }
}
