package com.sallyvnge.optionpricingapi.volatility;

import java.util.List;

public class LinearSmileInterpolator implements SmileInterpolator{

    /**
     * Interpolates the implied volatility at a given strike using linear interpolation
     * based on the provided volatility smile.
     * @param smile  The volatility smile containing strikes and corresponding implied volatilities.
     * @param strike The strike price at which to interpolate the implied volatility.
     * @return The interpolated implied volatility at the specified strike.
     */
    @Override
    public double ivAtStrike(VolSmile smile, double strike) {
        List<VolPoint> points = smile.volPoints();
        return interpolateLinear(points, strike);
    }

    /**
     * Interpolates the implied volatility at a given strike using linear interpolation
     * based on log-moneyness transformation of the volatility smile.
     * @param smile The volatility smile containing strikes and corresponding implied volatilities.
     * @param strike The strike price at which to interpolate the implied volatility.
     * @param forward The forward price used for log-moneyness calculation.
     * @return The interpolated implied volatility at the specified strike.
     */
    public double ivAtStrikeLogMoneyness(VolSmile smile, double strike, double forward) {
        List<VolPoint> points = smile.volPoints();

        List<VolPoint> pointsK = points.stream()
                .map(p -> new VolPoint(Math.log(p.strike() / forward), p.iv()))
                .toList();

        double k = Math.log(strike / forward);

        return interpolateLinear(pointsK, k);
    }

    /**
     * Performs linear interpolation on a list of volatility points.
     * @param points The list of volatility points (sorted by strike).
     * @param targetStrike The target strike value for interpolation.
     * @return The interpolated implied volatility.
     * @throws IllegalStateException if the target strike is outside the range of provided points.
     */
    private double interpolateLinear(List<VolPoint> points, double targetStrike) {
        if (points.isEmpty()) {
            throw new IllegalArgumentException("Cannot interpolate with empty points list");
        }

        if (points.size() == 1) {
            return points.get(0).iv();
        }

        // Handle boundary cases - extrapolation using boundary values
        VolPoint first = points.get(0);
        if (targetStrike <= first.strike()) {
            return first.iv();
        }

        VolPoint last = points.get(points.size() - 1);
        if (targetStrike >= last.strike()) {
            return last.iv();
        }

        // Find the two points surrounding the target strike and interpolate
        return findAndInterpolate(points, targetStrike);
    }

    /**
     * Finds the two surrounding points and performs linear interpolation.
     * @param points The list of volatility points.
     * @param targetStrike The target strike value.
     * @return The interpolated implied volatility.
     * @throws IllegalStateException if no surrounding points are found.
     */
    private double findAndInterpolate(List<VolPoint> points, double targetStrike) {
        for (int i = 0; i < points.size() - 1; i++) {
            VolPoint p1 = points.get(i);
            VolPoint p2 = points.get(i + 1);

            if (targetStrike >= p1.strike() && targetStrike <= p2.strike()) {
                return calculateLinearInterpolation(p1, p2, targetStrike);
            }
        }

        throw new IllegalStateException("Strike not within smile range: " + targetStrike);
    }

    /**
     * Calculates linear interpolation between two points.
     * @param p1 The first point.
     * @param p2 The second point.
     * @param targetStrike The target strike value.
     * @return The interpolated implied volatility.
     */
    private double calculateLinearInterpolation(VolPoint p1, VolPoint p2, double targetStrike) {
        double slope = (p2.iv() - p1.iv()) / (p2.strike() - p1.strike());
        return p1.iv() + slope * (targetStrike - p1.strike());
    }
}
