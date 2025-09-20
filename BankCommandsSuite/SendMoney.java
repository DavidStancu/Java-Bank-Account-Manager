package org.poo.BankCommandsSuite;

import org.poo.*;
import org.poo.AccountsSuite.Account;
import org.poo.PaymentPlanSuite.PaymentPlan;
import org.poo.PaymentPlanSuite.PaymentPlanManager;
import org.poo.TransactionsSuite.TransactionFactory;
import org.poo.TransactionsSuite.TransactionTag;
import org.poo.fileio.CommandInput;

import java.util.List;

import static org.poo.BankTeller.findAccountByIBANOrAlias;
import static org.poo.BankTeller.findUserByEmail;

/**
 * Command class responsible for handling money transfers between users.
 * This class processes the transfer of funds from a sender to a receiver,
 * including currency conversion if needed, and records the transaction for both users.
 */
public class SendMoney implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;
    private final OutputBuilder outputBuilder;
    /**
     * Constructs a SendMoney command.
     *
     * @param users the list of users in the system
     * @param commandInput the input containing the details of the transaction
     */
    public SendMoney(final List<User> users, final CommandInput commandInput,
                     final OutputBuilder outputBuilder) {
        this.users = users;
        this.commandInput = commandInput;
        this.outputBuilder = outputBuilder;
    }

    /**
     * Executes the send money command.
     */
    @Override
    public void execute() {
        String senderEmail = commandInput.getEmail();
        String senderIBAN = commandInput.getAccount();
        String receiverIBAN = commandInput.getReceiver();
        double amount = commandInput.getAmount();
        String description = commandInput.getDescription();
        int timestamp = commandInput.getTimestamp();

        User senderUser = findUserByEmail(senderEmail);
        if (senderUser == null) {
            return;
        }

        Account senderAccount = findAccountByIBANOrAlias(senderUser,
                senderIBAN);
        if (senderAccount == null) {
            return;
        }

        User receiverUser = null;
        Account receiverAccount = null;

        for (User user : users) {
            receiverAccount = findAccountByIBANOrAlias(user, receiverIBAN);
            if (receiverAccount != null) {
                receiverUser = user;
                break;
            }
        }

        if (receiverAccount == null) {
            outputBuilder.printStandardError("sendMoney",
                    "User not found", timestamp);
            return;
        }

        double convertedAmount = amount;

        if (!senderAccount.getCurrency().equalsIgnoreCase(receiverAccount.getCurrency())) {
            convertedAmount = BankTeller.convertCurrency(amount,
                    senderAccount.getCurrency(), receiverAccount.getCurrency());
        }

        PaymentPlan senderPlan = PaymentPlanManager.getPlan(senderUser.getPaymentPlan()
                .getType());
        double fee = 0.0;

        if ("silver".equalsIgnoreCase(senderPlan.getType())) {
            double amountInRON = BankTeller.convertCurrency(amount,
                    senderAccount.getCurrency(), "RON");
            if (amountInRON >= MagicNumbers.MN500) {
                fee = amount * senderPlan.getTransactionFee();
            }
        } else if ("standard".equalsIgnoreCase(senderPlan.getType())) {
            fee = amount * senderPlan.getTransactionFee();
        }


        double totalDeduction = amount + fee;


        if (senderAccount.getBalance() < totalDeduction) {
            senderUser.addTransaction(TransactionFactory.createTransaction(
                    TransactionTag.NO_FUNDS, timestamp, "Insufficient funds"));
            return;
        }

        senderAccount.setBalance(senderAccount.getBalance() - totalDeduction);
        receiverAccount.setBalance(receiverAccount.getBalance() + convertedAmount);


        senderUser.addTransaction(TransactionFactory.createTransaction(
                TransactionTag.TRANSFER, timestamp, description,
                senderAccount.getIBAN(),
                receiverAccount.getIBAN(), String.valueOf(amount),
                "sent", senderAccount.getCurrency()));

        receiverUser.addTransaction(TransactionFactory.createTransaction(
                TransactionTag.TRANSFER, timestamp, description,
                senderAccount.getIBAN(),
                receiverAccount.getIBAN(), String.valueOf(convertedAmount),
                "received", receiverAccount.getCurrency()));
    }

}
