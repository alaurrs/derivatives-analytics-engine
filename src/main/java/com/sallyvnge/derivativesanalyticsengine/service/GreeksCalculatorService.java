package com.sallyvnge.derivativesanalyticsengine.service;

import com.sallyvnge.derivativesanalyticsengine.dto.OptionRequestDto;
import com.sallyvnge.derivativesanalyticsengine.exception.UnsupportedOrMissingOptionTypeException;
import com.sallyvnge.derivativesanalyticsengine.model.Greeks;
import com.sallyvnge.derivativesanalyticsengine.util.BlackScholesUtil;
import com.sallyvnge.derivativesanalyticsengine.util.NormalDistributionUtil;
import org.springframework.stereotype.Service;

@Service
public class GreeksCalculatorService {

    /**
     * @param optionRequestDto
     * @return Greeks values (delta, gamma, vega)
     */
    public Greeks calculate(OptionRequestDto optionRequestDto) {
        double delta, gamma, vega;

        double S = optionRequestDto.underlyingPrice();
        double K = optionRequestDto.strikePrice();
        double T = optionRequestDto.timeToMaturity();
        double r = optionRequestDto.riskFreeRate();
        double sigma = optionRequestDto.volatility();

        double d1 = BlackScholesUtil.computeD1(S, K, T, r, sigma);

        delta = switch (optionRequestDto.optionType()) {
            case CALL -> NormalDistributionUtil.cumulativeDistribution(d1);
            case PUT -> NormalDistributionUtil.cumulativeDistribution(d1) - 1;
        };

        gamma = NormalDistributionUtil.probabilityDensity(d1)/(S*sigma*Math.sqrt(T));

        vega = S*NormalDistributionUtil.probabilityDensity(d1)*Math.sqrt(T);

        return new Greeks(delta, gamma, vega);
    }
}
