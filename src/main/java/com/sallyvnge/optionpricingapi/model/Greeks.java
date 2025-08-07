package com.sallyvnge.optionpricingapi.model;

import lombok.Builder;

/**
 * Represents the Greeks of an option in the context of financial derivatives.
 * The Greeks are sensitivities of the option's price to various parameters.
 *
 * Delta, gamma, and vega are key sensitivities calculated from a pricing model like Black-Scholes.
 *
 * - Delta: Measures the sensitivity of the option's price to changes in the price of the underlying asset.
 * - Gamma: Measures the sensitivity of the option's delta to changes in the price of the underlying asset.
 * - Vega: Measures the sensitivity of the option's price to changes in the volatility of the underlying asset.
 */
@Builder
public record Greeks(
        double delta,
        double gamma,
        double vega
) {}
