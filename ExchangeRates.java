package org.poo;

/**
 * Represents an exchange rate between two currencies.
 * The {@code ExchangeRates} class holds information about the source currency,
 * the target currency, and the conversion rate between them.
 */
public class ExchangeRates {
    private String from;
    private String to;
    private double rate;

    /**
     * Gets the source currency of the exchange rate.
     *
     * @return The source currency as a {@code String}.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the source currency of the exchange rate.
     *
     * @param from The source currency as a {@code String}.
     */
    public void setFrom(final String from) {
        this.from = from;
    }

    /**
     * Gets the target currency of the exchange rate.
     *
     * @return The target currency as a {@code String}.
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the target currency of the exchange rate.
     *
     * @param to The target currency as a {@code String}.
     */
    public void setTo(final String to) {
        this.to = to;
    }

    /**
     * Gets the conversion rate from the source currency to the target
     * currency.
     *
     * @return The exchange rate as a {@code double}.
     */
    public double getRate() {
        return rate;
    }

    /**
     * Sets the conversion rate from the source currency to the target currency.
     *
     * @param rate The exchange rate as a {@code double}.
     */
    public void setRate(final double rate) {
        this.rate = rate;
    }
}
