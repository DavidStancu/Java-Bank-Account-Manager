package org.poo.CashbackSuite;

/**
 * Represents categories of merchants used for categorizing transactions in cashback
 * strategies. Each merchant category corresponds to a specific type of business or
 * service, allowing for more granular tracking and application of cashback rules.
 *
 * The predefined categories include:
 * - FOOD: Represents transactions related to food services or purchases.
 * - CLOTHES: Represents transactions related to clothing or apparel.
 * - TECH: Represents transactions related to technology or electronics.
 *
 * This enumeration can be used to classify and manage transactions based on the
 * type of merchant involved and to determine applicable cashback or discount rules.
 */
public enum CommerciantCategory {
    FOOD, CLOTHES, TECH
}
