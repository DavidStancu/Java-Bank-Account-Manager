package org.poo.TransactionsSuite;

/**
 * Represents a transaction where a new account is created.
 * Inherits from the {@link Transaction} class and provides a specific description.
 */
public class AccountCreated extends Transaction {
    private static final String DESCRIPTION = "New account created";

    /**
     * Constructs an AccountCreated transaction with the specified timestamp.
     *
     * @param timestamp the timestamp when the account was created
     */
    public AccountCreated(final int timestamp) {
        super(TransactionTag.ACCT_CREATED.name(), timestamp);
    }

    /**
     * Returns the description of the transaction, which is "New account created".
     *
     * @return the transaction description
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
