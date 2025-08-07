package com.sallyvnge.derivativesanalyticsengine.dto;

import com.sallyvnge.derivativesanalyticsengine.model.PricingModel;
import lombok.Builder;

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