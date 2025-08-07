package com.sallyvnge.derivativesanalyticsengine.service;

import com.sallyvnge.derivativesanalyticsengine.dto.OptionPricingResponseDto;
import com.sallyvnge.derivativesanalyticsengine.dto.OptionRequestDto;
import com.sallyvnge.derivativesanalyticsengine.model.Greeks;
import com.sallyvnge.derivativesanalyticsengine.model.OptionType;
import com.sallyvnge.derivativesanalyticsengine.model.PricingModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OptionPricingServiceTest {
    private BlackScholesPricingService blackScholesPricingService;
    private GreeksCalculatorService greeksCalculatorService;
    private OptionPricingService optionPricingService;

    @BeforeEach
    void setUp() {
        blackScholesPricingService = mock(BlackScholesPricingService.class);
        greeksCalculatorService = mock(GreeksCalculatorService.class);
        optionPricingService = new OptionPricingService(blackScholesPricingService, greeksCalculatorService);
    }

    @Test
    void should_build_full_pricing_response() {
        // Given
        OptionRequestDto request = OptionRequestDto.builder()
                .underlyingPrice(100.0)
                .strikePrice(100.0)
                .timeToMaturity(1.0)
                .riskFreeRate(0.05)
                .volatility(0.2)
                .optionType(OptionType.CALL)
                .build();

        when(blackScholesPricingService.calculatePrice(request)).thenReturn(10.45);
        when(greeksCalculatorService.calculate(request))
                .thenReturn(new Greeks(0.6368, 0.0188, 37.52));

        // When
        OptionPricingResponseDto response = optionPricingService.calculate(request);

        // Then
        assertThat(response.price()).isEqualTo(10.45);
        assertThat(response.delta()).isEqualTo(0.6368);
        assertThat(response.gamma()).isEqualTo(0.0188);
        assertThat(response.vega()).isEqualTo(37.52);
        assertThat(response.volatilityUsed()).isEqualTo(0.2);
        assertThat(response.pricingModel()).isEqualTo(PricingModel.BLACK_SCHOLES);
        assertThat(response.input()).isEqualTo(request);

        // And verify interactions
        verify(blackScholesPricingService).calculatePrice(request);
        verify(greeksCalculatorService).calculate(request);
    }
}
