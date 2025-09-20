package org.poo.CashbackSuite;

/**
 * Represents a strategy for calculating cashback on financial transactions.
 * Different implementations of this interface can provide various cashback
 * calculation mechanisms based on transaction attributes, account details,
 * or other business logic.
 */
public interface CashbackStrategy {
    /**
     * Calculates the cashback amount for a given transaction based on the provided
     * account cashback data, transaction amount, and account details. The calculation
     * may include evaluation of various business rules and conditions such as spending
     * thresholds, transaction counts, and merchant categories.
     *
     * @param data an instance of {@link CashBackRules.AccountCashbackData} containing
     *             the cashback details and history for the account
     * @param transactionAmount the amount of the transaction for which cashback is being calculated
     * @param account the account identifier for which the transaction occurred
     * @return the calculated cashback amount for the transaction
     */
    double calculateCashback(CashBackRules.AccountCashbackData data,
                             double transactionAmount, String account);
}
