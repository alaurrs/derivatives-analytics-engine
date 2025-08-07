package com.sallyvnge.derivativesanalyticsengine.util;

import org.apache.commons.math3.distribution.NormalDistribution;

public class NormalDistributionUtil {

    private static final NormalDistribution NORMAL_DISTRIBUTION = new NormalDistribution();

    public static double cumulativeDistribution(double x) {
        return NORMAL_DISTRIBUTION.cumulativeProbability(x);
    }

    public static double probabilityDensity(double x) {
        return NORMAL_DISTRIBUTION.density(x);
    }
}
