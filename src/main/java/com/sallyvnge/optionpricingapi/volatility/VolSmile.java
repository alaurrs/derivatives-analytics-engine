package com.sallyvnge.optionpricingapi.volatility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a volatility smile containing multiple volatility points.
 * The smile consists of strike prices and their corresponding implied volatilities.
 * Points are automatically sorted by strike price for efficient interpolation.
 */
public record VolSmile(List<VolPoint> volPoints) {

    /**
     * Creates a volatility smile and ensures points are sorted by strike.
     */
    public VolSmile {
        if (volPoints == null) {
            throw new IllegalArgumentException("Volatility points cannot be null");
        }

        // Create a defensive copy and sort by strike
        volPoints = new ArrayList<>(volPoints);
        volPoints.sort(Comparator.comparing(VolPoint::strike));

        // Validate that all strikes are unique
        validateUniqueStrikes(volPoints);
    }

    /**
     * Validates that all strikes in the smile are unique.
     * @param points The list of volatility points to validate
     * @throws IllegalArgumentException if duplicate strikes are found
     */
    private static void validateUniqueStrikes(List<VolPoint> points) {
        for (int i = 1; i < points.size(); i++) {
            if (Double.compare(points.get(i-1).strike(), points.get(i).strike()) == 0) {
                throw new IllegalArgumentException(
                    "Duplicate strike found: " + points.get(i).strike());
            }
        }
    }

    /**
     * Returns the number of points in the smile.
     * @return The number of volatility points
     */
    public int size() {
        return volPoints.size();
    }

    /**
     * Checks if the smile is empty.
     * @return true if the smile contains no points
     */
    public boolean isEmpty() {
        return volPoints.isEmpty();
    }

    /**
     * Gets the minimum strike in the smile.
     * @return The minimum strike price
     * @throws IllegalStateException if the smile is empty
     */
    public double minStrike() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot get min strike from empty smile");
        }
        return volPoints.get(0).strike();
    }

    /**
     * Gets the maximum strike in the smile.
     * @return The maximum strike price
     * @throws IllegalStateException if the smile is empty
     */
    public double maxStrike() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot get max strike from empty smile");
        }
        return volPoints.get(volPoints.size() - 1).strike();
    }
}
