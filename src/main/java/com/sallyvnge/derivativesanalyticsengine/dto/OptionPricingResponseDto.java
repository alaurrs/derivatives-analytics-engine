package com.sallyvnge.derivativesanalyticsengine.dto;

import com.sallyvnge.derivativesanalyticsengine.model.PricingModel;
import lombok.Builder;

/**
 * Represents the response for option pricing calculations in the context of financial derivatives.
 * The response contains the computed price of the option, key sensitivities (Greeks), the volatility used,
 * the pricing model employed for the calculation, and the input parameters.
 *
 * - Price: The calculated price of the option.
 * - Delta: Measures the sensitivity of the option's price to changes in the price of the underlying asset.
 * - Gamma: Measures the sensitivity of the option's delta to changes in the price of the underlying asset.
 * - Vega: Measures the sensitivity of the option's price to changes in the volatility of the underlying asset.
 * - Volatility Used: The volatility value input used in the pricing calculation.
 * - Pricing Model: The pricing model employed, such as Black-Scholes.
 * - Input: The request parameters provided for the pricing calculation.
 */
@Builder
public record OptionPricingResponseDto(
        double price,
        double delta,
        double gamma,
        double vega,
        double volatilityUsed,
        PricingModel pricingModel,
        OptionRequestDto input
) {
}