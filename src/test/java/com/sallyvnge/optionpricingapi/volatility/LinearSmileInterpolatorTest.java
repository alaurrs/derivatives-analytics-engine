package com.sallyvnge.optionpricingapi.volatility;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinearSmileInterpolatorTest {

    @Test
    void should_return_correct_interpolated_volatility() {
        // Given
        VolSmile volSmile = VolSmile.builder()
                .tenor("1M")
                .maturityYears(1.0/12.0)
                .volPoints(
                        List.of(
                                new VolPoint(90, 0.3),
                                new VolPoint(110, 0.2)
                        )
                )
                .build();

        LinearSmileInterpolator interpolator = new LinearSmileInterpolator();

        // When
        double interpolatedVol = interpolator.ivAtStrike(volSmile, 105);

        // Then
        assertEquals(0.225, interpolatedVol, 1e-12);
    }

    @Test
    void should_return_lowest_iv_when_strike_is_below_min() {
        VolSmile volSmile = VolSmile.builder()
                .tenor("1M")
                .maturityYears(1.0 / 12.0)
                .volPoints(List.of(
                        new VolPoint(90, 0.3),
                        new VolPoint(110, 0.2)
                ))
                .build();

        LinearSmileInterpolator interpolator = new LinearSmileInterpolator();

        double iv = interpolator.ivAtStrike(volSmile, 80);

        assertEquals(0.3, iv, 1e-12);
    }

    @Test
    void should_return_highest_iv_when_strike_is_above_max() {
        VolSmile volSmile = VolSmile.builder()
                .tenor("1M")
                .maturityYears(1.0 / 12.0)
                .volPoints(List.of(
                        new VolPoint(90, 0.3),
                        new VolPoint(110, 0.2)
                ))
                .build();

        LinearSmileInterpolator interpolator = new LinearSmileInterpolator();

        double iv = interpolator.ivAtStrike(volSmile, 130);

        assertEquals(0.2, iv, 1e-12);
    }

    @Test
    void should_interpolate_correctly_with_multiple_points() {
        // Given
        VolSmile volSmile = VolSmile.builder()
                .tenor("1M")
                .maturityYears(1.0 / 12.0)
                .volPoints(List.of(
                        new VolPoint(90, 0.3),
                        new VolPoint(100, 0.27),
                        new VolPoint(110, 0.2)
                ))
                .build();

        LinearSmileInterpolator interpolator = new LinearSmileInterpolator();

        // When
        double iv = interpolator.ivAtStrike(volSmile, 105);

        // Then
        assertEquals(0.235, iv, 1e-12);
    }
}
