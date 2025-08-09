package com.sallyvnge.optionpricingapi.volatility;

/**
 * Represents a single point in a volatility smile.
 * Contains a strike price (or log-moneyness) and its corresponding implied volatility.
 *
 * @param strike The strike price or log-moneyness value
 * @param iv The implied volatility at this strike
 */
public record VolPoint(double strike, double iv) {

    /**
     * Validates that the volatility point has valid values.
     * Note: Strike can be negative when representing log-moneyness.
     */
    public VolPoint {
        if (iv < 0) {
            throw new IllegalArgumentException("Implied volatility must be non-negative, got: " + iv);
        }
        if (!Double.isFinite(strike)) {
            throw new IllegalArgumentException("Strike must be finite, got: " + strike);
        }
        if (!Double.isFinite(iv)) {
            throw new IllegalArgumentException("Implied volatility must be finite, got: " + iv);
        }
    }
}
