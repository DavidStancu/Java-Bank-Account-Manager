package org.poo;

import org.poo.AccountsSuite.Account;
import org.poo.PaymentPlanSuite.PaymentPlan;
import org.poo.PaymentPlanSuite.PaymentPlanManager;
import org.poo.TransactionsSuite.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a user in the system with personal details, associated accounts, and transactions.
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String occupation;
    private List<Account> accounts;
    private List<Transaction> transactions;
    private PaymentPlan paymentPlan;
    private List<RequestNode> requestQueue;


    /**
     * Constructs a new User with the specified details.
     *
     * @param firstName  the user's first name
     * @param lastName   the user's last name
     * @param email      the user's email address
     * @param birthDate  the user's date of birth
     * @param occupation the user's occupation
     */
    public User(final String firstName, final String lastName, final String email,
                final LocalDate birthDate, final String occupation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.occupation = occupation;
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
        if ("student".equalsIgnoreCase(occupation)) {
            this.paymentPlan = PaymentPlanManager.getPlan("student");
        } else {
            this.paymentPlan = PaymentPlanManager.getPlan("standard");
            }
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Updates the payment plan for the user.
     *
     * @param paymentPlan the new PaymentPlan to be assigned to the user
     */
    public void setPaymentPlan(final PaymentPlan paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    /**
     * Checks if the user qualifies for an automatic upgrade to the gold payment plan.
     *
     * @return true if the user qualifies, false otherwise
     */
    public boolean canAutoUpgradeToGold() {
        long qualifyingTransactions = transactions.stream()
                .filter(tx -> {
                    return tx.getTransactionTag().equals(TransactionTag.ONLN_PAYMENT.name())
                            && ((OnlinePayment) tx).getAmount() >= MagicNumbers.MN300
                            || tx.getTransactionTag().equals(TransactionTag.SPLIT_PAY.name())
                            && ((SplitPay) tx).getTotalAmount() >= MagicNumbers.MN300
                            || tx.getTransactionTag().equals(TransactionTag.TRANSFER.name())
                            && Double.parseDouble(((TransferType) tx).getAmount().split(" ")[0])
                            >= MagicNumbers.MN300;
                })
                .count();
        return qualifyingTransactions >= MagicNumbers.MN5;
    }

    /**
     * Retrieves the current request queue of the user.
     * The request queue contains a list of pending RequestNode objects,
     * each of which represents a transaction request to be processed.
     *
     * @return a list of RequestNode objects representing the user's request queue
     */
    public List<RequestNode> getRequestQueue() {
        return requestQueue;
    }

    /**
     * Adds a new request to the user's request queue.
     *
     * @param request the RequestNode object to be added to the queue
     */
    public void addRequest(final RequestNode request) {
        this.requestQueue.add(request);
    }

    /**
     * Removes a specified request from the user's request queue.
     *
     * @param request the RequestNode object to be removed from the queue
     */
    public void removeRequest(final RequestNode request) {
        this.requestQueue.remove(request);
    }

    /**
     * Retrieves the birth date of the user.
     *
     * @return the birth date of the user as a LocalDate object.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date of the user.
     *
     * @param birthDate the date of birth to be assigned to the user
     */
    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Retrieves the occupation of the user.
     *
     * @return the occupation of the user as a String.
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Updates the occupation of the user.
     *
     * @param occupation the new occupation to be set for the user
     */
    public void setOccupation(final String occupation) {
        this.occupation = occupation;
    }

    /**
     * Retrieves the first name of the user.
     *
     * @return the first name of the user as a String.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name to be assigned to the user
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the user.
     *
     * @return the last name of the user as a String.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Updates the last name of the user.
     *
     * @param lastName the last name to be assigned to the user
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return the email address of the user as a String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the email address of the user.
     *
     * @param email the new email address to be set for the user
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Retrieves the list of accounts associated with the user.
     *
     * @return a list of Account objects representing the user's accounts.
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Adds an account to the user's list of accounts.
     * The account is added only if it is non-null.
     *
     * @param account the Account object to be added to the user's accounts
     */
    public void addAccount(final Account account) {
        if (account != null) {
            accounts.add(account);
        }
    }

    /**
     * Adds a new transaction to the user's transaction list.
     * Only non-null transactions are added to the list.
     *
     * @param transaction the Transaction object to be added to the user's transaction list
     */
    public void addTransaction(final Transaction transaction) {
        if (transaction != null) {
            transactions.add(transaction);
        }
    }

    /**
     * Retrieves the list of transactions associated with the user.
     *
     * @return a list of Transaction objects representing the user's transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Retrieves the current payment plan assigned to the user.
     *
     * @return the user's current PaymentPlan object.
     */
    public PaymentPlan getPaymentPlan() {
        return paymentPlan;
    }
}
