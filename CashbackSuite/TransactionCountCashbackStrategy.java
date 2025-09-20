package org.poo.CashbackSuite;

import java.util.Map;

public class TransactionCountCashbackStrategy implements CashbackStrategy {

    private static final Map<Integer, Map<CommerciantCategory, Double>> transactionThresholds = Map.of(
            2, Map.of(CommerciantCategory.FOOD, 0.02),
            5, Map.of(CommerciantCategory.CLOTHES, 0.05),
            10, Map.of(CommerciantCategory.TECH, 0.1)
    );

    private final CommerciantCategory category;

    /**
     * Constructs a TransactionCountCashbackStrategy instance associated with a specific
     * merchant category. This strategy uses predefined transaction thresholds for the given
     * category to calculate cashback based on the number of transactions a user has made.
     *
     * @param category the merchant category used to determine applicable transaction thresholds
     *                 and calculate cashback amounts
     */
    public TransactionCountCashbackStrategy(final CommerciantCategory category) {
        this.category = category;
    }

    /**
     * Calculates the cashback for a given transaction based on the transaction amount,
     * account details, and predefined cashback rules.
     *
     * @param data an instance of AccountCashbackData containing transaction counts
     *             and discount usage information
     * @param transactionAmount the monetary amount of the current transaction
     * @param account the account identifier associated with the transaction
     * @return the calculated cashback amount for the transaction
     */
    @Override
    public double calculateCashback(final CashBackRules.AccountCashbackData data,
                                    final double transactionAmount,
                                    final String account) {
        double cashback = 0.0;

        int transactionCount = data.getTransactionCounts().getOrDefault(category, 0);

        for (int threshold : transactionThresholds.keySet()) {
            Map<CommerciantCategory, Double> categoryMap = transactionThresholds.get(threshold);
            if (categoryMap != null && categoryMap.containsKey(category)) {
                if (transactionCount >= threshold && !data.getDiscountsUsed().get(category)) {
                    cashback = categoryMap.get(category) * transactionAmount;
                    data.getDiscountsUsed().put(category, true);
                    break;
                }
            }
        }

        data.incrementTransactionCount(category);

        return cashback;
    }
}
