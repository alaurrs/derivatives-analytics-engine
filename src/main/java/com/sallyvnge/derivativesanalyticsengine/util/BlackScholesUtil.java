package com.sallyvnge.derivativesanalyticsengine.util;

import com.sallyvnge.derivativesanalyticsengine.dto.OptionRequestDto;

public class BlackScholesUtil {

    private BlackScholesUtil() {}

    /**
     * Compute d1 from Black-Scholes model.
     */
    public static double computeD1(double S, double K, double T, double r, double sigma) {
        return (Math.log(S / K) + (r + 0.5 * sigma * sigma) * T)
                / (sigma * Math.sqrt(T));
    }

    /**
     * Compute d2 from d1
     */
    public static double computeD2(double d1, double sigma, double T) {
        return d1 - sigma * Math.sqrt(T);
    }
}
