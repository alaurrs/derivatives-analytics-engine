package com.sallyvnge.optionpricingapi.volatility;

@FunctionalInterface
public interface SmileInterpolator {

    /**
     * Interpolates the implied volatility at a given strike using the provided volatility smile.
     *
     * @param smile  The volatility smile containing strikes and corresponding implied volatilities.
     * @param strike The strike price at which to interpolate the implied volatility.
     * @return The interpolated implied volatility at the specified strike.
     */
    double ivAtStrike(VolSmile smile, double strike);
}
