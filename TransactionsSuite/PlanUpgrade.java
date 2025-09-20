package org.poo.TransactionsSuite;

/**
 * Represents a transaction for upgrading the payment plan of a user.
 * This class provides a specific description of the plan upgrade transaction.
 */
public class PlanUpgrade extends Transaction {
    private final String accountIBAN;
    private final String newPlanType;
    private final String description;


    public PlanUpgrade(final int timestamp, final String accountIBAN,
                       final String newPlanType) {
        super(TransactionTag.PLAN_UPGRADED.name(), timestamp);
        this.accountIBAN = accountIBAN;
        this.newPlanType = newPlanType;
        this.description = "Upgrade plan";
    }

    /**
     * Returns a description of the transaction in a JSON-like format.
     *
     * @return a JSON-like string representing the plan upgrade transaction
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the new plan type associated with the plan upgrade transaction.
     *
     * @return the type of the new plan as a string
     */
    public String getNewPlanType() {
        return newPlanType;
    }

    /**
     * Retrieves the IBAN (International Bank Account Number) associated
     * with the account involved in the plan upgrade transaction.
     *
     * @return the IBAN of the account as a string
     */
    public String getAccountIBAN() {
        return accountIBAN;
    }
}
