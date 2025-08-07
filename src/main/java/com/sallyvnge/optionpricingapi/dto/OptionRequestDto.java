package com.sallyvnge.optionpricingapi.dto;

import com.sallyvnge.optionpricingapi.model.OptionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

/**
 * Represents the request data required for option pricing and risk metrics calculations
 * in the context of financial derivatives. This includes data on the underlying asset,
 * contract details, and market conditions.
 *
 * The request data fields are:
 * - Underlying Price: Current price of the underlying asset (must be positive).
 * - Strike Price: The price at which the option can be exercised (must be positive).
 * - Time to Maturity: Time remaining until the option's expiration, expressed in years (must be positive).
 * - Risk-Free Rate: Annualized risk-free interest rate, expressed as a decimal (can be negative or positive).
 * - Volatility: Annualized standard deviation of the returns of the underlying asset (must be positive).
 * - Option Type: The type of the option, specifying whether it is a CALL or PUT.
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
