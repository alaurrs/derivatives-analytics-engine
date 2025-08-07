package com.sallyvnge.optionpricingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sallyvnge.optionpricingapi.dto.OptionRequestDto;
import com.sallyvnge.optionpricingapi.model.OptionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OptionPricingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_option_pricing_response_when_posting_valid_request() throws Exception {
        // Given
        OptionRequestDto request = OptionRequestDto.builder()
                .underlyingPrice(100.0)
                .strikePrice(100.0)
                .timeToMaturity(1.0)
                .riskFreeRate(0.05)
                .volatility(0.2)
                .optionType(OptionType.CALL)
                .build();

        // When
        mockMvc.perform(post("/api/v1/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.delta").exists())
                .andExpect(jsonPath("$.gamma").exists())
                .andExpect(jsonPath("$.vega").exists())
                .andExpect(jsonPath("$.input").exists())
                .andExpect(jsonPath("$.pricingModel").value("BLACK_SCHOLES"));
    }

    @Test
    void should_return_bad_request_given_invalid_input() throws Exception {
        // Given: missing volatility and invalid strike price
        String invalidJson = """
        {
            "underlyingPrice": 100.0,
            "strikePrice": -50.0,
            "timeToMaturity": 1.0,
            "riskFreeRate": 0.05,
            "optionType": "CALL"
        }
    """;

        // When
        mockMvc.perform(post("/api/v1/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.fields").exists());
    }
}
