package org.poo;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.AccountsSuite.Account;
import org.poo.BankCommandsSuite.*;
import org.poo.CashbackSuite.CashBackRules;
import org.poo.CommerciantsSuite.Commerciant;
import org.poo.CommerciantsSuite.CommerciantTransaction;
import org.poo.fileio.*;
import org.poo.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BankTeller {
    private final OutputBuilder outputBuilder;
    private static List<User> users;
    private static List<ExchangeRates> exchangeRates;
    private static Map<String, Commerciant> commerciants;
    private static List<CommerciantTransaction> commerciantTransactions;
    public static List<ExtendedCommerciant> extendedCommerciants;
    private int timestamp;

    public BankTeller() {
        this.outputBuilder = new OutputBuilder();
        this.users = new ArrayList<>();
        this.exchangeRates = new ArrayList<>();
        this.commerciants = new HashMap<>();
        this.commerciantTransactions = new ArrayList<>();
        this.extendedCommerciants = new ArrayList<>();
        this.timestamp = 0;
    }

    /**
     * Starts a new day by resetting random generators, clearing and
     * initializing data structures, and executing commands based on the provided input.
     *
     * @param inputData The input data containing users, exchange rates, commands,
     *                  and commerciants to process for the day.
     */
    public void startDay(final ObjectInput inputData) {
        Utils.resetRandom();

        commerciants.clear();
        CashBackRules.resetCashbackData();

        for (UserInput userInput : inputData.getUsers()) {
            User user = new User(
                    userInput.getFirstName(),
                    userInput.getLastName(),
                    userInput.getEmail(),
                    LocalDate.parse(userInput.getBirthDate()),
                    userInput.getOccupation()
            );
            users.add(user);
        }

        for (ExchangeInput rateInput : inputData.getExchangeRates()) {
            ExchangeRates rate = new ExchangeRates();
            rate.setFrom(rateInput.getFrom());
            rate.setTo(rateInput.getTo());
            rate.setRate(rateInput.getRate());
            exchangeRates.add(rate);

            ExchangeRates inverseRate = new ExchangeRates();
            inverseRate.setFrom(rateInput.getTo());
            inverseRate.setTo(rateInput.getFrom());
            inverseRate.setRate(1 / rateInput.getRate());
            exchangeRates.add(inverseRate);

        }

        for (CommerciantInput commerciantInput : inputData.getCommerciants()) {
            ExtendedCommerciant extendedCommerciant = new ExtendedCommerciant(
                    commerciantInput.getId(),
                    commerciantInput.getCommerciant(),
                    commerciantInput.getAccount(),
                    commerciantInput.getType(),
                    commerciantInput.getCashbackStrategy()
            );
            extendedCommerciants.add(extendedCommerciant);
        }

        for (CommandInput command : inputData.getCommands()) {
            String commandName = command.getCommand();
            timestamp++;
            switch (commandName) {
                case "printUsers" -> {
                    PrintUsers printUsersCommand = new PrintUsers(users, outputBuilder, command);
                    printUsersCommand.execute();
                }
                case "addAccount" -> {
                    AddAccount addAccountCommand = new AddAccount(users, command);
                    addAccountCommand.execute();
                }
                case "createCard", "createOneTimeCard" -> {
                    CreateCard createCardCommand = new CreateCard(users, command);
                    createCardCommand.execute();
                }
                case "addFunds" -> {
                    AddFunds addFundsCommand = new AddFunds(users, command);
                    addFundsCommand.execute();
                }
                case "deleteAccount" -> {
                    DeleteAccount deleteAccountCommand = new DeleteAccount(users,
                            command, outputBuilder);
                    deleteAccountCommand.execute();
                }
                case "deleteCard" -> {
                    DeleteCard deleteCardCommand = new DeleteCard(users, command);
                    deleteCardCommand.execute();
                }
                case "setMinimumBalance" -> {
                    SetMinimumBalance setMinimumBalanceCommand =
                            new SetMinimumBalance(users, command);
                    setMinimumBalanceCommand.execute();
                }
                case "payOnline" -> {
                    PayOnline payOnlineCommand = new PayOnline(users, command, outputBuilder);
                    payOnlineCommand.execute();
                }
                case "sendMoney" -> {
                    SendMoney sendMoneyCommand = new SendMoney(users, command, outputBuilder);
                    sendMoneyCommand.execute();
                }
                case "setAlias" -> {
                    SetAlias setAliasCommand = new SetAlias(users, command);
                    setAliasCommand.execute();
                }
                case "printTransactions" -> {
                    PrintTransactions printTransactionsCommand =
                            new PrintTransactions(users, command, outputBuilder);
                    printTransactionsCommand.execute();
                }
                case "checkCardStatus" -> {
                    CheckCardStatus checkCardStatusCommand = new CheckCardStatus(users,
                            command, outputBuilder);
                    checkCardStatusCommand.execute();
                }
                case "splitPayment" -> {
                    SplitPayment splitPaymentCommand = new SplitPayment(users,
                            command, outputBuilder);
                    splitPaymentCommand.execute();
                }
                case "report" -> {
                    Report reportCommand = new Report(users, command, outputBuilder);
                    reportCommand.execute();
                }
                case "spendingsReport" -> {
                    SpendingsReport spendingsReportCommand = new SpendingsReport(users,
                            command, outputBuilder);
                    spendingsReportCommand.execute();
                }
                case "addInterest" -> {
                    AddInterest addInterestCommand = new AddInterest(users, command,
                            outputBuilder);
                    addInterestCommand.execute();
                }
                case "changeInterestRate" -> {
                    ChangeInterestRate changeInterestRateCommand =
                            new ChangeInterestRate(users, command, outputBuilder);
                    changeInterestRateCommand.execute();
                }
                case "withdrawSavings" -> {
                    WithdrawSavings withdrawSavingsCommand = new WithdrawSavings(users,
                            command, outputBuilder);
                    withdrawSavingsCommand.execute();
                }
                case "upgradePlan" -> {
                    UpgradePlan upgradePlanCommand = new UpgradePlan(users, command);
                    upgradePlanCommand.execute();
                }
                case "cashWithdrawal" -> {
                    CashWithdrawal cashWithdrawalCommand = new CashWithdrawal(users,
                            command, outputBuilder);
                    cashWithdrawalCommand.execute();
                }
                default -> {

                }
            }
        }

    }

    /**
     * Retrieves the output array from the output builder.
     *
     * @return an ArrayNode containing the output data.
     */
    public ArrayNode getOutput() {
        return outputBuilder.getOutput();
    }

    /**
     * Converts the given amount from one currency to another based on defined exchange rates.
     *
     * @param amount       the amount to be converted.
     * @param fromCurrency the currency code of the original amount (e.g., "USD").
     * @param toCurrency   the target currency code to which the amount will be converted
     *                    (e.g., "EUR").
     * @return the equivalent amount in the target currency.
     */
    public static double convertCurrency(final double amount,
                                         final String fromCurrency,
                                         final String toCurrency) {
        double ans = convertCurrencyRecursive(amount, fromCurrency,
                toCurrency, new ArrayList<>());
        return ans;

    }

    /**
     * Recursively converts a given amount from one currency to another
     * based on defined exchange rates.
     * If a direct exchange rate is not available, this method attempts to
     * find a path of intermediate currencies
     * to perform the conversion.
     *
     * @param amount           the amount to be converted.
     * @param fromCurrency     the currency code of the original amount (e.g., "USD").
     * @param toCurrency       the target currency code to which the amount will be converted
     *                        (e.g., "EUR").
     * @param visitedCurrencies a list of visited currencies to avoid cycles during recursive
     *                         conversion.
     * @return the equivalent amount in the target currency.
     */
    public static double convertCurrencyRecursive(final double amount,
                                                  final String fromCurrency,
                                                  final String toCurrency,
                                                  final List<String> visitedCurrencies) {
        for (ExchangeRates rate : exchangeRates) {
            if (rate.getFrom().equalsIgnoreCase(fromCurrency)
                    && rate.getTo().equalsIgnoreCase(toCurrency)) {
                return amount * rate.getRate();
            }
        }

        for (ExchangeRates rate : exchangeRates) {
            if (rate.getFrom().equalsIgnoreCase(fromCurrency)) {
                double convertedAmount = convertCurrencyRecursive(amount * rate.getRate(),
                        rate.getTo(), toCurrency, visitedCurrencies);
                if (convertedAmount != amount) {
                    return convertedAmount;
                }
            }
        }

        return amount;
    }

    /**
     * Searches for a user in the system by their email address.
     * The comparison is case-insensitive and trims any whitespace around the email.
     *
     * @param email the email address of the user to be searched
     * @return the User object if a matching email is found, or null if no match is found
     */
    public static User findUserByEmail(final String email) {
        for (User user : users) {
            if (user.getEmail().trim().equalsIgnoreCase(email.trim())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Searches for an account belonging to the given user by matching either the IBAN
     * or the alias with the provided identifier. The alias comparison is case-insensitive.
     *
     * @param user       the user whose accounts will be searched.
     * @param identifier the identifier to search for, which can be either the IBAN
     *                   or the alias of the account.
     * @return the account that matches the provided identifier, or null if no match is found.
     */
    public static Account findAccountByIBANOrAlias(final User user, final String identifier) {
        for (Account account : user.getAccounts()) {
            if (account.getIBAN().equals(identifier)
                    || (account.getAlias() != null && account.getAlias()
                            .equalsIgnoreCase(identifier))) {
                return account;
            }
        }
        return null;
    }

    /**
     * Searches for an account by its IBAN from a list of users and their accounts.
     * Returns the account if a match is found; otherwise, returns null.
     *
     * @param users The list of users whose accounts will be searched.
     * @param iban  The IBAN to search for in the accounts.
     * @return The account with the matching IBAN or null if no match is found.
     */
    public static Account findAccountByIBAN(final List<User> users, final String iban) {
        if (users == null || iban == null) {
            return null;
        }

        for (User user : users) {
            List<Account> accounts = user.getAccounts();
            for (Account account : accounts) {
                if (iban.equals(account.getIBAN())) {
                    return account;
                }
            }
        }
        return null;
    }

    public static Map<String, Commerciant> getCommerciants() {
        return commerciants;
    }

    public static List<CommerciantTransaction> getCommerciantTransactions() {
        return commerciantTransactions;
    }

    /**
     * Adds a transaction involving a merchant to the list of merchant transactions.
     *
     * @param transaction the transaction to be added, which includes details
     *                    such as the account IBAN, amount, timestamp, description,
     *                    and the merchant's name.
     */
    public static void addTransaction(final CommerciantTransaction transaction) {
        commerciantTransactions.add(transaction);
    }
}
