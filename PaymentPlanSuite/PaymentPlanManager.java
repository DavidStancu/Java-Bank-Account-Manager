package org.poo.PaymentPlanSuite;

import org.poo.MagicNumbers;

import java.util.HashMap;
import java.util.Map;

/**
 * The PaymentPlanManager class is responsible for managing and retrieving payment plans.
 * It provides functionality to access specific payment plans, as well as to calculate
 * the upgrade fees between different plans.
 *
 * This class includes predefined payment plans such as "standard", "student", "silver", and "gold",
 * each having distinct attributes like transaction fees and minimum transaction amounts.
 *
 * Methods in this class are primarily used to retrieve payment plan details and determine
 * upgrade costs.
 *
 * The PaymentPlanManager is designed for static access, meaning its methods and data can be
 * accessed without instantiating the class.
 */
public class PaymentPlanManager {
    private static final Map<String, PaymentPlan> PLANS = new HashMap<>();

    static {
        PLANS.put("standard", new PaymentPlan("standard", MagicNumbers.MND002,
                0));
        PLANS.put("student", new PaymentPlan("student", 0, 0));
        PLANS.put("silver", new PaymentPlan("silver", MagicNumbers.MND001,
                MagicNumbers.MN500));
        PLANS.put("gold", new PaymentPlan("gold", 0, 0));
    }

    /**
     * Retrieves a specified payment plan based on the given type.
     * The method performs a case-insensitive lookup to identify
     * and return the corresponding payment plan from the predefined set.
     *
     * @param type the type of the payment plan to retrieve. This value is case-insensitive.
     * @return the PaymentPlan object corresponding to the specified type,
     *         or null if no matching plan is found.
     */
    public static PaymentPlan getPlan(final String type) {
        return PLANS.get(type.toLowerCase());
    }

    /**
     * Calculates the upgrade fee from the current payment plan to a new payment plan.
     * The fee depends on the specific combination of the current plan and the desired new plan.
     *
     * The following rules are applied:
     * - Upgrades from "standard" or "student" to "silver" cost 100.
     * - Upgrades from "standard" or "student" to "gold" cost 350.
     * - Upgrades from "silver" to "gold" cost 250.
     * - Any other combination results in a fee of 0.
     *
     * @param currentPlan the current payment plan as a string.
     * @param newPlan the target payment plan for the upgrade as a string.
     * @return the upgrade fee as a double. Returns 0 if no valid upgrade is applicable.
     */
    public static double calculateUpgradeFee(final String currentPlan,
                                             final String newPlan) {
        if (currentPlan.equalsIgnoreCase("standard")
                || currentPlan.equalsIgnoreCase("student")) {
            if (newPlan.equalsIgnoreCase("silver")) {
                return MagicNumbers.MN100;
            }
            if (newPlan.equalsIgnoreCase("gold")) {
                return MagicNumbers.MN350;
            }
        } else if (currentPlan.equalsIgnoreCase("silver")
                && newPlan.equalsIgnoreCase("gold")) {
            return MagicNumbers.MN250;
        }
        return 0;
    }
}
