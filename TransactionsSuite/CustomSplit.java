package org.poo.TransactionsSuite;

import org.poo.SplitNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a custom split transaction, inheriting from the {@link Transaction} class.
 * This class handles transactions where the split amounts are assigned manually.
 */
public class CustomSplit extends Transaction {
    private final List<SplitNode> nodes;
    private String transactionTag;

    /**
     * Constructs a CustomSplit transaction with the specified timestamp
     * and initializes it with a NULL_PAYMENT tag.
     *
     * @param timestamp the timestamp when the transaction occurred
     */
    public CustomSplit(final int timestamp) {
        super(TransactionTag.NULL_PAYMENT.name(), timestamp);
        this.nodes = new ArrayList<>();
        this.transactionTag = TransactionTag.NULL_PAYMENT.name();
    }

    /**
     * Adds a node to the transaction, representing an account and its associated amount.
     *
     * @param iban the IBAN of the account
     * @param amount the amount associated with the account
     * @param timestamp the timestamp of the node
     */
    public void addNode(final String iban,
                        final double amount, final int timestamp) {
        nodes.add(new SplitNode(iban, amount, timestamp));
    }

    /**
     * Updates the transaction tag from NULL_PAYMENT to CUSTOM_SPLIT.
     */
    public void finalizeSplit() {
        this.transactionTag = TransactionTag.CUSTOM_SPLIT.name();
    }

    /**
     * Returns the current transaction tag.
     *
     * @return the transaction tag
     */
    @Override
    public String getTransactionTag() {
        return transactionTag;
    }

    /**
     * Returns the list of nodes involved in the custom split transaction.
     *
     * @return a list of transaction nodes
     */
    public List<SplitNode> getNodes() {
        return nodes;
    }

    /**
     * Returns a description of the custom split transaction, including the total
     * number of accounts involved and their respective amounts.
     *
     * @return a description of the custom split transaction
     */
    @Override
    public String getDescription() {
        StringBuilder description = new StringBuilder("Custom Split Payment:\n");
        for (SplitNode node : nodes) {
            description.append(String.format("- IBAN: %s, Amount: %.2f, Timestamp: %d\n",
                    node.getIban(), node.getAmount(), node.getTimestamp()));
        }
        return description.toString();
    }
}
