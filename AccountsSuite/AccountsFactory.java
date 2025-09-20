package org.poo.AccountsSuite;

/**
 * A factory class for creating different types of accounts based on the provided parameters.
 * The class supports creating "CLASSIC", "SAVINGS", and "BUSINESS" accounts,
 * with the possibility of
 * passing additional parameters for specific account configurations.
 */
public class AccountsFactory {

    /**
     * Creates an account of the specified type.
     *
     * <p>If the account type is "CLASSIC", a new ClassicAccount is created.
     * If the account type is "SAVINGS", a new SavingsAccount is created,
     * and an extra parameter for the interest rate is required.
     * If the account type is "BUSINESS", a new BusinessAccount is created,
     * and an email and currency are required.</p>
     *
     * @param accountType the type of account to create ("CLASSIC", "SAVINGS", or "BUSINESS").
     * @param email the email address of the account holder.
     * @param currency the currency of the account.
     * @param extraParams any additional parameters required for certain account
     *                   types (e.g., interest rate for "SAVINGS").
     * @return a new Account object based on the specified type and parameters.
     * @throws IllegalArgumentException if an invalid account type is provided or
     * if required parameters are missing.
     */
    public static Account createAccount(final String accountType, final String email,
                                        final String currency, final Double... extraParams) {
        if ("CLASSIC".equalsIgnoreCase(accountType)) {
            return new ClassicAccount(email, currency);
        } else if ("SAVINGS".equalsIgnoreCase(accountType)) {
            if (extraParams.length > 0) {
                return new SavingsAccount(currency, extraParams[0]);
            } else {
                throw new IllegalArgumentException("Savings account requires an interest rate.");
            }
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }
    }
}
