package org.poo.TransactionsSuite;

public class WithdrawCash extends Transaction {
    private final double amount;

    /**
     * Constructs a WithdrawCashTransaction.
     *
     * @param timestamp the timestamp of the transaction
     * @param amount the amount withdrawn
     */
    public WithdrawCash(final int timestamp, final double amount) {
        super(TransactionTag.WITHDRAW_CASH.name(), timestamp);
        this.amount = amount;
    }

    /**
     * Returns a description of the withdrawal transaction.
     *
     * @return a string description of the withdrawal transaction
     */
    @Override
    public String getDescription() {
        return "Cash withdrawal of " + amount;
    }

    /**
     * Returns the amount withdrawn.
     *
     * @return the withdrawal amount
     */
    public double getAmount() {
        return amount;
    }
}
