package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.BankTeller;
import org.poo.TransactionsSuite.TransactionFactory;
import org.poo.TransactionsSuite.TransactionTag;
import org.poo.User;
import org.poo.PaymentPlanSuite.PaymentPlan;
import org.poo.PaymentPlanSuite.PaymentPlanManager;
import org.poo.fileio.CommandInput;

import java.util.List;

/**
 * Represents a command that upgrades the payment plan of a user for a specified account.
 */
public class UpgradePlan implements BankCommand {

    private final List<User> users;
    private final CommandInput commandInput;

    /**
     * Constructs an UpgradePlan command.
     *
     * @param users the list of users whose payment plan will be upgraded
     * @param commandInput the input data containing account details and the new plan
     */
    public UpgradePlan(final List<User> users, final CommandInput commandInput) {
        this.users = users;
        this.commandInput = commandInput;
    }

    /**
     * Executes the command to upgrade the payment plan for a specified account.
     * The new plan type is fetched from the command input and applied
     * to the account's user.
     * A transaction is created to log the change.
     */
    @Override
    public void execute() {
        String accountIBAN = commandInput.getAccount();
        String newPlanType = commandInput.getNewPlanType();

        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getIBAN().equals(accountIBAN)) {
                    PaymentPlan currentPlan = user.getPaymentPlan();
                    PaymentPlan newPlan = PaymentPlanManager.getPlan(newPlanType);

                    if (newPlan == null) {
                        return;
                    }

                    if (currentPlan.getType().equalsIgnoreCase(newPlanType)) {
                        return;
                    }

                    double upgradeFeeRON = PaymentPlanManager
                            .calculateUpgradeFee(currentPlan.getType(), newPlanType);

                    if (upgradeFeeRON == 0) {
                        return;
                    }

                    double upgradeFeeInAccountCurrency = upgradeFeeRON;
                    if (!account.getCurrency().equalsIgnoreCase("RON")) {
                        upgradeFeeInAccountCurrency = BankTeller.convertCurrency(
                                upgradeFeeRON,
                                "RON",
                                account.getCurrency()
                        );
                    }

                    if (account.getBalance() < upgradeFeeInAccountCurrency) {
                        user.addTransaction(TransactionFactory.createTransaction(
                                TransactionTag.NO_FUNDS,
                                commandInput.getTimestamp(),
                                "Insufficient funds for upgrade"
                        ));
                        return;
                    }

                    account.setBalance(account.getBalance() - upgradeFeeInAccountCurrency);

                    user.setPaymentPlan(newPlan);

                    user.addTransaction(TransactionFactory.createTransaction(
                            TransactionTag.PLAN_UPGRADED,
                            commandInput.getTimestamp(),
                            account.getIBAN(),
                            newPlanType,
                            upgradeFeeInAccountCurrency
                    ));

                    return;
                }
            }
        }
    }
}

