package com.sallyvnge.optionpricingapi.volatility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class LinearSmileInterpolatorTest {

    private LinearSmileInterpolator interpolator;
    private VolSmile testSmile;

    @BeforeEach
    void setUp() {
        interpolator = new LinearSmileInterpolator();

        // Create a test smile with strikes 90, 100, 110 and IVs 0.25, 0.20, 0.30
        List<VolPoint> points = Arrays.asList(
            new VolPoint(90.0, 0.25),
            new VolPoint(100.0, 0.20),
            new VolPoint(110.0, 0.30)
        );
        testSmile = new VolSmile(points);
    }

    @Test
    void should_interpolate_between_two_points() {
        // Given: Strike 95 is between 90 and 100
        double strike = 95.0;

        // When
        double iv = interpolator.ivAtStrike(testSmile, strike);

        // Then: Should interpolate linearly between 0.25 and 0.20
        double expected = 0.25 + (0.20 - 0.25) * (95.0 - 90.0) / (100.0 - 90.0);
        assertThat(iv).isCloseTo(expected, within(1e-10));
        assertThat(iv).isCloseTo(0.225, within(1e-3));
    }

    @Test
    void should_return_boundary_value_for_strike_below_range() {
        // Given: Strike below minimum
        double strike = 80.0;

        // When
        double iv = interpolator.ivAtStrike(testSmile, strike);

        // Then: Should return first point's IV
        assertThat(iv).isEqualTo(0.25);
    }

    @Test
    void should_return_boundary_value_for_strike_above_range() {
        // Given: Strike above maximum
        double strike = 120.0;

        // When
        double iv = interpolator.ivAtStrike(testSmile, strike);

        // Then: Should return last point's IV
        assertThat(iv).isEqualTo(0.30);
    }

    @Test
    void should_return_exact_value_for_existing_strike() {
        // Given: Exact strike from smile
        double strike = 100.0;

        // When
        double iv = interpolator.ivAtStrike(testSmile, strike);

        // Then: Should return exact IV
        assertThat(iv).isEqualTo(0.20);
    }

    @Test
    void should_interpolate_with_log_moneyness() {
        // Given
        double strike = 95.0;
        double forward = 100.0;

        // When
        double iv = interpolator.ivAtStrikeLogMoneyness(testSmile, strike, forward);

        // Then: Should give a reasonable interpolated value
        assertThat(iv).isGreaterThan(0.0);
        assertThat(iv).isLessThan(1.0);
    }

    @Test
    void should_handle_single_point_smile() {
        // Given: Smile with only one point
        List<VolPoint> singlePoint = Collections.singletonList(new VolPoint(100.0, 0.20));
        VolSmile singlePointSmile = new VolSmile(singlePoint);

        // When
        double iv = interpolator.ivAtStrike(singlePointSmile, 95.0);

        // Then: Should return the single point's IV
        assertThat(iv).isEqualTo(0.20);
    }

    @Test
    void should_throw_exception_for_empty_smile() {
        // Given: Empty smile
        VolSmile emptySmile = new VolSmile(Collections.emptyList());

        // When & Then
        assertThatThrownBy(() -> interpolator.ivAtStrike(emptySmile, 100.0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Cannot interpolate with empty points list");
    }

    @Test
    void should_produce_same_results_for_both_methods_at_strikes() {
        // Given: Test at exact strikes with forward = 100
        double forward = 100.0;

        for (VolPoint point : testSmile.volPoints()) {
            double strike = point.strike();

            // When
            double ivNormal = interpolator.ivAtStrike(testSmile, strike);
            double ivLogMoneyness = interpolator.ivAtStrikeLogMoneyness(testSmile, strike, forward);

            // Then: Should be very close (allowing for small numerical differences)
            assertThat(ivLogMoneyness).isCloseTo(ivNormal, within(1e-10));
        }
    }
}
