package com.sallyvnge.derivativesanalyticsengine.dto;

import com.sallyvnge.derivativesanalyticsengine.model.OptionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * Input for pricing an European option using Black-Scholes model
 */
@Data
@Builder
@Validated
public class OptionRequestDto {

    @Positive
    @NotNull
    private double underlyingPrice;
    @Positive
    @NotNull
    private double strikePrice;
    @Positive
    @NotNull
    private double timeToMaturity;
    @NotNull
    private double riskFreeRate;
    @Positive
    @NotNull
    private double volatility;
    @NotNull
    private OptionType optionType;
}
