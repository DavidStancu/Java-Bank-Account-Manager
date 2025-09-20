package org.poo.TransactionsSuite;

import java.util.List;

/**
 * Represents a failed split payment transaction, inheriting from the {@link Transaction} class.
 * This class captures the details of a failed payment split and the associated error.
 */
public class FailedSplitPay extends Transaction {
    private final List<String> involvedAccounts;
    private final double attemptedAmount;
    private final String currency;

    /**
     * Constructs a FailedSplitPay transaction with the specified details.
     *
     * @param timestamp the timestamp when the split payment occurred
     * @param involvedAccounts a list of account IBANs involved in the payment split
     * @param attemptedAmount the amount attempted to be split across the involved accounts
     * @param currency the currency in which the payment was made
     */
    public FailedSplitPay(final int timestamp, final List<String> involvedAccounts,
                          final double attemptedAmount, final String currency) {
        super(TransactionTag.FAILED_SPLIT_PAY.name(), timestamp);

        this.involvedAccounts = involvedAccounts;
        this.attemptedAmount = attemptedAmount;
        this.currency = currency;
    }

    /**
     * Returns the list of involved accounts in the failed split payment.
     *
     * @return a list of account IBANs involved in the payment
     */
    public List<String> getInvolvedAccounts() {
        return involvedAccounts;
    }

    /**
     * Returns the currency in which the attempted split payment was made.
     *
     * @return the currency of the payment
     */
    public String getCurrency() {
        return currency;
    }


    /**
     * Returns a description of the failed split payment transaction.
     *
     * @return a description of the failed split payment
     */
    @Override
    public String getDescription() {
        return String.format("Failed split payment of %.2f %s: ",
                attemptedAmount, currency);
    }

    /**
     * Returns the amount attempted to be paid before the failure.
     *
     * @return the attempted amount
     */
    public double getFailedAmount() {
        return attemptedAmount;
    }
}
