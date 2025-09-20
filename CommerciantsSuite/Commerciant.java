package org.poo.CommerciantsSuite;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a commerciant (merchant) in the banking system.
 * A {@code Commerciant} is associated with a name and a map of account payments,
 * where each account is linked to a total payment amount.
 */
public class Commerciant {
    private final String name;
    private final Map<String, Double> accountPayments;

    /**
     * Constructs a new {@code Commerciant} with the specified name.
     * Initializes the account payments map.
     *
     * @param name The name of the commerciant.
     */
    public Commerciant(final String name) {
        this.name = name;
        this.accountPayments = new HashMap<>();
    }

    /**
     * Adds a payment for a specific account identified by its IBAN.
     * If the account already has payments, the new payment is added to the existing total.
     * If not, a new payment entry is created for the account.
     *
     * @param accountIBAN The IBAN of the account to which the payment is linked.
     * @param amount The payment amount to be added.
     */
    public void addPayment(final String accountIBAN, final double amount) {
        accountPayments.put(accountIBAN, accountPayments.getOrDefault(accountIBAN, 0.0) + amount);
    }

    /**
     * Returns the total amount of all payments across all accounts.
     *
     * @return The total amount of all payments as a {@code double}.
     */
    public double getTotalPayments() {
        return accountPayments.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    /**
     * Returns a map of all accounts and their associated payment amounts.
     * The map's key is the account IBAN, and the value is the payment amount.
     *
     * @return A map of account payments where the key is the IBAN and the value
     * is the payment amount.
     */
    public Map<String, Double> getAccountPayments() {
        return accountPayments;
    }

    /**
     * Returns the name of the commerciant.
     *
     * @return The name of the commerciant as a {@code String}.
     */
    public String getName() {
        return name;
    }
}
