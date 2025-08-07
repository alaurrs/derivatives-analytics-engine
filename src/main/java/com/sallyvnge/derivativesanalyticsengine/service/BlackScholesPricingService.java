package com.sallyvnge.derivativesanalyticsengine.service;

import com.sallyvnge.derivativesanalyticsengine.dto.OptionRequestDto;
import com.sallyvnge.derivativesanalyticsengine.util.NormalDistributionUtil;
import org.springframework.stereotype.Service;

@Service
public class BlackScholesPricingService {

    public double calculatePrice(OptionRequestDto optionRequestDto) {
        double S = optionRequestDto.underlyingPrice();
        double K = optionRequestDto.strikePrice();
        double T = optionRequestDto.timeToMaturity();
        double r = optionRequestDto.riskFreeRate();
        double sigma = optionRequestDto.volatility();

        double d1 = (Math.log(S / K) + (r + 0.5 * sigma * sigma) * T) / (sigma * Math.sqrt(T));
        double d2 = d1 - sigma * Math.sqrt(T);

        return switch (optionRequestDto.optionType()) {
            case CALL ->
                    S * NormalDistributionUtil.cumulativeDistribution(d1) - K * Math.exp(-r * T) * NormalDistributionUtil.cumulativeDistribution(d2);
            case PUT -> K * Math.exp(-r * T) * NormalDistributionUtil.cumulativeDistribution(-d2)
                    - S * NormalDistributionUtil.cumulativeDistribution(-d1);
        };
    }
}
