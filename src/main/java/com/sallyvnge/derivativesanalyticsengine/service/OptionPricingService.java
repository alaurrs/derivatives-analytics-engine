package com.sallyvnge.derivativesanalyticsengine.service;

import com.sallyvnge.derivativesanalyticsengine.dto.OptionPricingResponseDto;
import com.sallyvnge.derivativesanalyticsengine.dto.OptionRequestDto;
import com.sallyvnge.derivativesanalyticsengine.model.Greeks;
import com.sallyvnge.derivativesanalyticsengine.model.PricingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionPricingService {

    private final BlackScholesPricingService blackScholesPricingService;
    private final GreeksCalculatorService greeksCalculatorService;

    /**
     * Calculates the price and key sensitivities (Greeks) of a financial option using the Black-Scholes model.
     * It also includes the input parameters, the model used, and the volatility for reference in the response.
     *
     * @param optionRequestDto the request object containing the details of the option to be priced,
     *                         including underlying price, strike price, time to maturity, risk-free rate,
     *                         volatility, and option type (CALL or PUT)
     * @return an OptionPricingResponseDto containing the calculated price, Greeks (delta, gamma, vega),
     *         the pricing model used, the volatility utilized during calculation, and the input parameters
     */
    public OptionPricingResponseDto calculate(OptionRequestDto optionRequestDto) {
        double price = blackScholesPricingService.calculatePrice(optionRequestDto);
        Greeks greeks = greeksCalculatorService.calculate(optionRequestDto);

        return OptionPricingResponseDto.builder()
                .price(price)
                .delta(greeks.delta())
                .gamma(greeks.gamma())
                .vega(greeks.vega())
                .volatilityUsed(optionRequestDto.volatility())
                .pricingModel(PricingModel.BLACK_SCHOLES)
                .input(optionRequestDto)
                .build();

    }
}
