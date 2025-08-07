package com.sallyvnge.optionpricingapi.controller;

import com.sallyvnge.optionpricingapi.dto.OptionPricingResponseDto;
import com.sallyvnge.optionpricingapi.dto.OptionRequestDto;
import com.sallyvnge.optionpricingapi.service.OptionPricingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/price")
@RequiredArgsConstructor
public class OptionPricingController {

    private final OptionPricingService optionPricingService;

    @Operation(
            summary = "Price a European option",
            description = "Returns the price and Greeks (Delta, Gamma, Vega) for a given option using the Black-Scholes model"
    )
    @PostMapping
    public ResponseEntity<OptionPricingResponseDto> computeOptionPrice(@Valid @RequestBody OptionRequestDto optionRequestDto) {
        OptionPricingResponseDto response = optionPricingService.calculate(optionRequestDto);
        return ResponseEntity.ok(response);
    }
}
