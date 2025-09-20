package org.poo;

/**
 * The ExtendedCommerciant class represents a commerciant with additional properties
 * such as cashback strategies and type, along with basic information like ID, name,
 * and account details.
 *
 * This class provides methods to retrieve the properties of the commerciant and
 * a static method to search for a commerciant by name.
 */
public class ExtendedCommerciant {
    private final int id;
    private final String name;
    private final String account;
    private final String type;
    private final String cashbackStrategy;

    public ExtendedCommerciant(final int id, final String name, final String account,
                               final String type, final String cashbackStrategy) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.type = type;
        this.cashbackStrategy = cashbackStrategy;
    }

    /**
     * Searches for an ExtendedCommerciant by its name within the collection of
     * extended commerciants.
     *
     * @param name the name of the commerciant to search for, case-insensitive
     * @return the ExtendedCommerciant object matching the provided name, or null if
     * no match is found
     */
    public static ExtendedCommerciant findCommerciantByName(final String name) {
        for (ExtendedCommerciant commerciant : BankTeller.extendedCommerciants) {
            if (commerciant.getName().equalsIgnoreCase(name)) {
                return commerciant;
            }
        }
        return null;
    }

    /**
     * Retrieves the unique identifier of the commerciant.
     *
     * @return the ID of the commerciant
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the name of the commerciant.
     *
     * @return the name of the commerciant
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the account details of the commerciant.
     *
     * @return the account information as a string
     */
    public String getAccount() {
        return account;
    }

    /**
     * Retrieves the type of the commerciant.
     *
     * @return the type of the commerciant as a string
     */
    public String getType() {
        return type;
    }

    /**
     * Retrieves the cashback strategy associated with the commerciant.
     *
     * @return the cashback strategy as a string, which indicates the criteria
     *         or method used for calculating cashback, such as "numberOfTransactions"
     *         or "spendingThreshold".
     */
    public String getCashbackStrategy() {
        return cashbackStrategy;
    }

}
