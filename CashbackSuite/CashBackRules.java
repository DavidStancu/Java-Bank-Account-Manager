package org.poo.CashbackSuite;

import org.poo.ExtendedCommerciant;
import org.poo.User;

import java.util.*;

/**
 * The CashBackRules class provides functionality for managing cashback rules and calculations
 * based on user transactions, merchant categories, and payment plans. It maintains cashback
 * data for multiple accounts and determines the cashback amount based on defined strategies.
 */
public class CashBackRules {

    /**
     * Represents cashback data associated with an account, tracking merchant category
     * transaction counts, discounts usage, and total spending.
     */
    public static class AccountCashbackData {
        private Map<CommerciantCategory, Integer> transactionCounts;
        private Map<CommerciantCategory, Boolean> discountsUsed;
        private double totalSpent;

        public AccountCashbackData() {
            this.transactionCounts = new EnumMap<>(CommerciantCategory.class);
            this.discountsUsed = new EnumMap<>(CommerciantCategory.class);
            this.totalSpent = 0;

            for (CommerciantCategory category : CommerciantCategory.values()) {
                transactionCounts.put(category, 0);
                discountsUsed.put(category, false);
            }
        }

        /**
         * Increments the transaction count for a specified merchant category. If the category
         * is not already present in the transaction counts, it initializes the count to 1.
         *
         * @param category the MerchantCategory for which the transaction count should be
         *                incremented
         */
        public void incrementTransactionCount(final CommerciantCategory category) {
            transactionCounts.put(category, transactionCounts.getOrDefault(category, 0) + 1);
        }

        /**
         * Adds a specified spending amount to the total spending tracked for an account.
         *
         * @param amount the spending amount to be added to the total
         */
        public void addSpending(final double amount) {
            totalSpent += amount;
        }

        /**
         * Retrieves a mapping of merchant categories to the respective transaction counts
         * for each category. The returned map tracks how many transactions have been made
         * in each merchant category and provides a summary of the transaction activity.
         *
         * @return a map where the keys are instances of MerchantCategory and the values
         *         are integers representing the corresponding transaction counts.
         */
        public Map<CommerciantCategory, Integer> getTransactionCounts() {
            return transactionCounts;
        }


        /**
         * Retrieves a mapping of merchant categories to a boolean flag indicating
         * whether a discount has been used for each category. The returned map helps
         * track which categories have already utilized discounts during transactions.
         *
         * @return a map where the keys are instances of MerchantCategory and the values
         *         are booleans indicating whether a discount has been used for the
         *         respective category.
         */
        public Map<CommerciantCategory, Boolean> getDiscountsUsed() {
            return discountsUsed;
        }

        /**
         * Retrieves the total amount spent, representing the cumulative spending
         * tracked for an account across all transactions.
         *
         * @return the total spending amount as a double
         */
        public double getTotalSpent() {
            return totalSpent;
        }

    }

    private static final Map<String, AccountCashbackData> accountCashbackDataMap = new HashMap<>();

    /**
     * Calculates the cashback amount for a given transaction. The cashback is
     * determined based on the user's payment plan and the merchant's category.
     * The method evaluates two strategies: one based on spending thresholds
     * and another on transaction counts, returning the higher cashback value.
     *
     * @param accountIBAN the IBAN of the account where the transaction occurred
     * @param user the user performing the transaction
     * @param commerciant the merchant involved in the transaction
     * @param transactionAmount the transaction amount for which cashback is calculated
     * @return the calculated cashback amount for the transaction
     */
    public static double calculateCashback(final String accountIBAN,
            final User user, final ExtendedCommerciant commerciant,
                                           final double transactionAmount) {

        AccountCashbackData data = getCashbackDataForAccount(accountIBAN);

        CommerciantCategory category = getMerchantCategory(commerciant);

        CashbackStrategy spendingStrategy = new SpendingThresholdCashbackStrategy(user
                .getPaymentPlan().getType());
        CashbackStrategy transactionStrategy = new TransactionCountCashbackStrategy(category);

        double cashbackFromSpending = spendingStrategy.calculateCashback(data,
                transactionAmount, accountIBAN);
        double cashbackFromTransactions = transactionStrategy.calculateCashback(data,
                transactionAmount, accountIBAN);

        data.incrementTransactionCount(category);
        data.addSpending(transactionAmount);
        updateCashbackDataForAccount(accountIBAN, data);

        return Math.max(cashbackFromSpending, cashbackFromTransactions);
    }

    /**
     * Determines the merchant category for the given commerciant based on its type.
     *
     * @param commerciant the ExtendedCommerciant object, representing the merchant
     *                    whose type will be mapped to a MerchantCategory.
     * @return the MerchantCategory corresponding to the commerciant's type, such as FOOD,
     *         CLOTHES, or TECH.
     * @throws IllegalArgumentException if the commerciant's type does not match any known category.
     */
    public static CommerciantCategory getMerchantCategory(
            final ExtendedCommerciant commerciant) {
        String type = commerciant.getType().trim().toLowerCase();

        switch (type) {
            case "food":
                return CommerciantCategory.FOOD;
            case "clothes":
                return CommerciantCategory.CLOTHES;
            case "tech":
                return CommerciantCategory.TECH;
            default:
                throw new IllegalArgumentException("Unknown category "
                        + type);
        }
    }

    /**
     * Retrieves the cashback data associated with a specific account. If no cashback data exists
     * for the provided account IBAN, a new instance of {@code AccountCashbackData} is created
     * and associated with the account.
     *
     * @param accountIBAN the IBAN of the account for which cashback data is to be retrieved
     * @return an instance of {@code AccountCashbackData} containing the cashback details
     * for the account
     */
    public static AccountCashbackData getCashbackDataForAccount(final String accountIBAN) {
        return accountCashbackDataMap.computeIfAbsent(accountIBAN,
                k -> new AccountCashbackData());
    }

    /**
     * Updates the cashback data associated with a specific account. This method
     * replaces the existing cashback data for the given account IBAN with the new
     * {@code AccountCashbackData} provided.
     *
     * @param accountIBAN the IBAN of the account for which cashback data is to be updated
     * @param data the new {@code AccountCashbackData} instance containing updated
     *             cashback information
     */
    public static void updateCashbackDataForAccount(final String accountIBAN,
                                                    final AccountCashbackData data) {
        accountCashbackDataMap.put(accountIBAN, data);
    }

    /**
     * Resets all cashback data in the application.
     *
     * This method is responsible for clearing the internal data structure
     * (`accountCashbackDataMap`) that holds the cashback information for all accounts.
     * It is typically used to initialize or reset the state of cashback data to an
     * empty state,
     * ensuring no residual data from past operations.
     */
    public static void resetCashbackData() {
        accountCashbackDataMap.clear();
    }
}
