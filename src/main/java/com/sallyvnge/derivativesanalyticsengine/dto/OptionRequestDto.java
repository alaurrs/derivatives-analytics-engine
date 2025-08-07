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
@Builder
@Validated
public record OptionRequestDto (

    @Positive
    @NotNull double underlyingPrice,
    @Positive
    @NotNull double strikePrice,
    @Positive
    @NotNull double timeToMaturity,
    @NotNull double riskFreeRate,
    @Positive
    @NotNull double volatility,
    @NotNull OptionType optionType
) {}
