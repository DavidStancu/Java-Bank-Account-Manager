package org.poo.TransactionsSuite;

/**
 * Enum representing the various types of transactions.
 * Each transaction type is associated with a specific tag used to identify the transaction.
 */
public enum TransactionTag {
    /**
     * Represents a transfer transaction.
     */
    TRANSFER,

    /**
     * Represents a no funds transaction.
     */
    NO_FUNDS,

    /**
     * Represents an account creation transaction.
     */
    ACCT_CREATED,

    /**
     * Represents a card creation transaction.
     */
    CARD_CREATED,

    /**
     * Represents a card deletion transaction.
     */
    CARD_DELETED,

    /**
     * Represents an online payment transaction.
     */
    ONLN_PAYMENT,

    /**
     * Represents a card status transaction.
     */
    CARD_STAT,

    /**
     * Represents a split payment transaction.
     */
    SPLIT_PAY,
    FAILED_SPLIT_PAY,
    UNDERAGE,
    PLAN_UPGRADED,
    WITHDRAW_CASH,
    INTEREST,
    NO_CLASSIC,
    NULL_PAYMENT,
    CUSTOM_SPLIT
}
