package org.poo.TransactionsSuite;

import java.util.List;

/**
 * Represents a split payment transaction, inheriting from the {@link Transaction} class.
 * This class captures the details of a payment split across multiple involved accounts.
 */
public class SplitPay extends Transaction {
    private final List<String> involvedAccounts;
    private final double totalAmount;
    private final String currency;

    /**
     * Constructs a SplitPay transaction with the specified timestamp,
     * involved accounts, total amount, and currency.
     *
     * @param timestamp the timestamp when the split payment occurred
     * @param involvedAccounts a list of account IBANs involved in the payment split
     * @param totalAmount the total amount to be split across the involved accounts
     * @param currency the currency in which the payment is made
     */
    public SplitPay(final int timestamp, final List<String> involvedAccounts,
                    final double totalAmount, final String currency) {
        super(TransactionTag.SPLIT_PAY.name(), timestamp);
        this.involvedAccounts = involvedAccounts;
        this.totalAmount = totalAmount;
        this.currency = currency;
    }

    /**
     * Returns the list of involved accounts in the split payment.
     *
     * @return a list of account IBANs involved in the payment
     */
    public List<String> getInvolvedAccounts() {
        return involvedAccounts;
    }

    /**
     * Returns the currency in which the split payment is made.
     *
     * @return the currency of the payment
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Returns a description of the split payment transaction, including the
     * total amount and currency.
     *
     * @return a description of the split payment
     */
    @Override
    public String getDescription() {
        return String.format("Split payment of %.2f %s", totalAmount
                * involvedAccounts.size(), currency);
    }

    /**
     * Returns the total amount of the payment before it is split across the involved accounts.
     *
     * @return the total amount of the payment
     */
    public double getTotalAmount() {
        return totalAmount;
    }
}
