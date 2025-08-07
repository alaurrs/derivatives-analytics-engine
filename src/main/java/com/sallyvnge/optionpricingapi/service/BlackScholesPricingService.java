package com.sallyvnge.optionpricingapi.service;

import com.sallyvnge.optionpricingapi.dto.OptionRequestDto;
import com.sallyvnge.optionpricingapi.util.BlackScholesUtil;
import com.sallyvnge.optionpricingapi.util.NormalDistributionUtil;
import org.springframework.stereotype.Service;

@Service
public class BlackScholesPricingService {

    /**
     * Calculates the price of a European option (call or put) using the Black-Scholes model.
     *
     * @param optionRequestDto the details of the option, including underlying price, strike price,
     *                         time to maturity, risk-free rate, volatility, and option type (CALL or PUT)
     * @return the calculated price of the option
     */
    public double calculatePrice(OptionRequestDto optionRequestDto) {
        double S = optionRequestDto.underlyingPrice();
        double K = optionRequestDto.strikePrice();
        double T = optionRequestDto.timeToMaturity();
        double r = optionRequestDto.riskFreeRate();
        double sigma = optionRequestDto.volatility();

        double d1 = BlackScholesUtil.computeD1(S, K, T, r, sigma);
        double d2 = BlackScholesUtil.computeD2(d1, sigma, T);

        return switch (optionRequestDto.optionType()) {
            case CALL ->
                    S * NormalDistributionUtil.cumulativeDistribution(d1) - K * Math.exp(-r * T) * NormalDistributionUtil.cumulativeDistribution(d2);
            case PUT -> K * Math.exp(-r * T) * NormalDistributionUtil.cumulativeDistribution(-d2)
                    - S * NormalDistributionUtil.cumulativeDistribution(-d1);
        };
    }
}
