package com.sallyvnge.optionpricingapi.volatility;

import lombok.Builder;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Builder
public record VolSmile (
        String tenor,
        double maturityYears,
        List<VolPoint> volPoints

) {

    public VolSmile {
        validateTenor(tenor);
        validateMaturityYears(maturityYears);

        volPoints = normalizeVolPoints(volPoints);
    }

    public static VolSmile of(String tenor, double maturityYears, List<VolPoint> points) {
        return new VolSmile(tenor, maturityYears, points);
    }

    public int size() {
        return volPoints.size();
    }

    private static void validateTenor(String tenor) {
        if (tenor == null || tenor.isBlank()) {
            throw new IllegalArgumentException("Tenor cannot be null or blank");
        }
    }

    private static void validateMaturityYears(double maturityYears) {
        if (!Double.isFinite(maturityYears) || maturityYears <= 0.0) {
            throw new IllegalArgumentException("Maturity years must be a positive finite number");
        }
    }

    private static List<VolPoint> normalizeVolPoints(List<VolPoint> volPoints) {
        if (volPoints == null) {
            throw new IllegalArgumentException("Volatility points cannot be null");
        }

        if (volPoints.size() < 2) {
            throw new IllegalArgumentException("Volatility points must contain at least two points for interpolation");
        }

        List<VolPoint> sorted = volPoints.stream()
                .map(Objects::requireNonNull)
                .sorted(Comparator.comparingDouble(VolPoint::strike))
                .toList();

        for (int i = 0; i < sorted.size(); i++) {
            VolPoint p = sorted.get(i);

            if (!Double.isFinite(p.strike()) || p.strike() <= 0.0) {
                throw new IllegalArgumentException("strike must be finite and > 0");
            }
            if (!Double.isFinite(p.iv()) || p.iv() <= 0.0 || p.iv() >= 5.0) {
                throw new IllegalArgumentException("iv must be finite and in (0, 5)");
            }
            if (i > 0) {
                double prevK = sorted.get(i - 1).strike();
                if (p.strike() <= prevK) {
                    throw new IllegalArgumentException("strikes must be strictly increasing");
                }
            }
        }

        return List.copyOf(sorted);
    }
}
