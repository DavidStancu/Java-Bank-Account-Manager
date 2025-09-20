package org.poo.BankCommandsSuite;

import org.poo.*;
import org.poo.AccountsSuite.Account;
import org.poo.CardsSuite.Card;
import org.poo.TransactionsSuite.TransactionFactory;
import org.poo.TransactionsSuite.TransactionTag;
import org.poo.fileio.CommandInput;

import java.util.List;

public class CashWithdrawal implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;

    public CashWithdrawal(final List<User> users, final CommandInput commandInput,
                          final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the cash withdrawal command.
     * The user must have sufficient funds, and a transaction
     * will be created for the withdrawal.
     */
    @Override
    public void execute() {

        String email = commandInput.getEmail();
        String cardNumber = commandInput.getCardNumber();
        double amountRON = commandInput.getAmount();
        int timestamp = commandInput.getTimestamp();

        for (User user : users) {
            if (user.getEmail().trim().equalsIgnoreCase(email.trim())) {
                for (Account account : user.getAccounts()) {
                    for (Card card : account.getCards()) {
                        if (card.getCardNumber().equals(cardNumber)) {

                            if (amountRON <= 0) {
                                return;
                            }

                            double transactionFee = 0;
                            if (user.getPaymentPlan().isFeeApplicable(amountRON)) {
                                transactionFee = user.getPaymentPlan().getTransactionFee()
                                        * amountRON;
                            }

                            double totalAmountRON = amountRON + transactionFee;

                            double amount = totalAmountRON;
                            if (!account.getCurrency().equalsIgnoreCase("RON")) {
                                amount = BankTeller.convertCurrency(totalAmountRON, "RON",
                                        account.getCurrency());
                            }


                            if (account.getBalance() >= amount) {
                                double balance = account.getBalance();
                                account.setBalance(balance - amount);


                                user.addTransaction(TransactionFactory
                                        .createTransaction(TransactionTag.WITHDRAW_CASH,
                                                timestamp, amountRON));

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
        outputBuilder.cashWithdrawalError("Card not found", timestamp);
    }
}
