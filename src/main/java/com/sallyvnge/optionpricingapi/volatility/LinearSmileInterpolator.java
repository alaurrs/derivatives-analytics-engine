package com.sallyvnge.optionpricingapi.volatility;

import java.util.List;

public class LinearSmileInterpolator implements SmileInterpolator{

    /**
     * Interpolates the implied volatility at a given strike using linear interpolation
     * based on the provided volatility smile.
     * @param smile  The volatility smile containing strikes and corresponding implied volatilities.
     * @param strike The strike price at which to interpolate the implied volatility.
     * @return
     */
    @Override
    public double ivAtStrike(VolSmile smile, double strike) {
        List<VolPoint> points = smile.volPoints();

        // minimum and maximum strikes
        VolPoint first = points.get(0);
        if (strike <= first.strike()) {
            return first.iv();
        }

        VolPoint last = points.get(points.size() - 1);
        if (strike >= last.strike()) {
            return last.iv();
        }

        // find the two points surrounding the strike
        for (int i = 0; i < points.size() - 1; i++) {
            VolPoint p1 = points.get(i);
            VolPoint p2 = points.get(i + 1);

            if (strike >= p1.strike() && strike <= p2.strike()) {
                // linear interpolation
                double slope = (p2.iv() - p1.iv()) / (p2.strike() - p1.strike());
                return p1.iv() + slope * (strike - p1.strike());
            }
        }

        throw new IllegalStateException("Strike not within smile range: " + strike);
    }
}
