package com.sallyvnge.derivativesanalyticsengine.service;

import com.sallyvnge.derivativesanalyticsengine.dto.OptionRequestDto;
import com.sallyvnge.derivativesanalyticsengine.model.OptionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class BlackScholesPricingServiceTest {

    private BlackScholesPricingService blackScholesPricingService;

    @BeforeEach()
    void setUp() {
        blackScholesPricingService = new BlackScholesPricingService();
    }

    @Test
    void should_calculate_call_option_price_correctly() {
        // Given
        OptionRequestDto request = OptionRequestDto.builder()
                .underlyingPrice(100.0)
                .strikePrice(100.0)
                .timeToMaturity(1.0) // 1 an
                .riskFreeRate(0.05)  // 5%
                .volatility(0.2)     // 20%
                .optionType(OptionType.CALL)
                .build();

        // When
        double price = blackScholesPricingService.calculatePrice(request);

        // Then
        assertThat(price).isCloseTo(10.45, within(0.01)); // Valeur connue
    }

    @Test
    void should_calculate_put_option_price_correctly() {
        // Given
        OptionRequestDto request = OptionRequestDto.builder()
                .underlyingPrice(100.0)
                .strikePrice(100.0)
                .timeToMaturity(1.0)
                .riskFreeRate(0.05)
                .volatility(0.2)
                .optionType(OptionType.PUT)
                .build();

        // When
        double price = blackScholesPricingService.calculatePrice(request);

        // Then
        assertThat(price).isCloseTo(5.57, within(0.01)); // Valeur connue
    }

    @Test
    void should_return_zero_for_zero_volatility_call_option() {
        // Given
        OptionRequestDto request = OptionRequestDto.builder()
                .underlyingPrice(100.0)
                .strikePrice(90.0)
                .timeToMaturity(1.0)
                .riskFreeRate(0.05)
                .volatility(0.0)
                .optionType(OptionType.CALL)
                .build();

        // When
        double price = blackScholesPricingService.calculatePrice(request);

        // Then
        assertThat(price).isCloseTo(100.0 - 90.0 * Math.exp(-0.05), within(0.01));
    }
}
