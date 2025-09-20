package org.poo.CashbackSuite;

import org.poo.MagicNumbers;

import java.util.Map;

/**
 * A cashback strategy that calculates cashback amounts based on spending thresholds
 * and account plan types. It implements the {@link CashbackStrategy} interface and
 * defines specific rules for calculating cashback percentages for different spending
 * tiers and account plans.
 *
 * The cashback percentage varies depending on the account plan type
 * (e.g., "standard", "student", "silver", "gold") and the total amount spent.
 * There are predefined spending thresholds (500, 300, and 100) with associated
 * cashback rates. Each threshold defines a distinct rate for eligible plans,
 * and the calculated cashback is a percentage of the transaction amount.
 */
public class SpendingThresholdCashbackStrategy implements CashbackStrategy {

    private static final Map<String, Double> BIG = Map.of(
            "standard", 0.0025,
            "student", 0.0025,
            "silver", 0.005,
            "gold", 0.007
    );
    private static final Map<String, Double> MEDIUM = Map.of(
            "standard", 0.002,
            "student", 0.002,
            "silver", 0.004,
            "gold", 0.0055
    );
    private static final Map<String, Double> SMALL = Map.of(
            "standard", 0.001,
            "student", 0.001,
            "silver", 0.003,
            "gold", 0.005
    );

    private final String planType;

    /**
     * Constructs a new {@code SpendingThresholdCashbackStrategy} with the specified
     * account plan type. The plan type determines the applicable cashback rules for
     * different spending thresholds.
     *
     * @param planType the type of payment plan (e.g., "standard", "student",
     *                 "silver", "gold") to be used for determining cashback rates
     */
    public SpendingThresholdCashbackStrategy(final String planType) {
        this.planType = planType.toLowerCase();
    }

    /**
     * Calculates the cashback amount for a given transaction based on spending thresholds
     * and account plan type. The cashback is determined by the total spending amount
     * associated with the account and the particular rate defined for the account's plan type.
     *
     * @param data             an instance of {@code CashBackRules.AccountCashbackData}
     *                         representing the cashback data including the total spending amount
     * @param transactionAmount the monetary value of the transaction for which cashback
     *                         needs to be calculated
     * @param account          the account identifier associated with the transaction
     * @return the calculated cashback amount for the given transaction
     */
    @Override
    public double calculateCashback(final CashBackRules.AccountCashbackData data,
                                    final double transactionAmount,
                                    final String account) {
        double cashback = 0.0;

        data.addSpending(transactionAmount);

        if (data.getTotalSpent() >= MagicNumbers.MN500
                && BIG.containsKey(planType)) {
            cashback = BIG.get(planType) * transactionAmount;
        } else if (data.getTotalSpent() >= MagicNumbers.MN300
                && MEDIUM.containsKey(planType)) {
            cashback = MEDIUM.get(planType) * transactionAmount;
        } else if (data.getTotalSpent() >= MagicNumbers.MN100
                && SMALL.containsKey(planType)) {
            cashback = SMALL.get(planType) * transactionAmount;
        }
        return cashback;
    }
}
