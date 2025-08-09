package com.sallyvnge.optionpricingapi.volatility;

/**
 * Interface for smile interpolation strategies.
 * Provides methods to interpolate implied volatility at specific strikes
 * based on a volatility smile.
 */
public interface SmileInterpolator {

    /**
     * Interpolates the implied volatility at a given strike.
     *
     * @param smile The volatility smile containing strikes and corresponding implied volatilities
     * @param strike The strike price at which to interpolate the implied volatility
     * @return The interpolated implied volatility at the specified strike
     */
    double ivAtStrike(VolSmile smile, double strike);
}
