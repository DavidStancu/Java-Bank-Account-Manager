package org.poo.TransactionsSuite;

/**
 * Represents a transfer transaction between two accounts.
 * This class contains the details of the transfer, including sender and receiver IBANs,
 * the transfer amount, the transfer type, description, and currency.
 */
public class TransferType extends Transaction {
    private final String senderIBAN;
    private final String receiverIBAN;
    private final String amount;
    private final String transferType;
    private final String description;
    private final String currency;

    /**
     * Constructs a TransferType transaction.
     *
     * @param timestamp       The timestamp of the transaction.
     * @param description    A description of the transaction.
     * @param senderIBAN     The IBAN of the sender's account.
     * @param receiverIBAN   The IBAN of the receiver's account.
     * @param amount         The amount being transferred.
     * @param transferType   The type of the transfer (e.g., "standard", "priority").
     * @param currency       The currency used in the transfer.
     */
    public TransferType(final int timestamp, final String description,
                        final String senderIBAN, final String receiverIBAN,
                        final String amount, final String transferType, final String currency) {
        super(TransactionTag.TRANSFER.name(), timestamp);
        this.description = description;
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.transferType = transferType;
        this.currency = currency;
    }

    /**
     * Returns the description of the transfer.
     *
     * @return The description of the transaction.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns the IBAN of the sender's account.
     *
     * @return The sender's IBAN.
     */
    public String getSenderIBAN() {
        return senderIBAN;
    }

    /**
     * Returns the IBAN of the receiver's account.
     *
     * @return The receiver's IBAN.
     */
    public String getReceiverIBAN() {
        return receiverIBAN;
    }

    /**
     * Returns the amount being transferred, followed by the currency.
     *
     * @return The amount and currency of the transfer.
     */
    public String getAmount() {
        return amount + " " + currency;
    }

    /**
     * Returns the type of the transfer (e.g., "standard", "priority").
     *
     * @return The transfer type.
     */
    public String getTransferType() {
        return transferType;
    }
}
