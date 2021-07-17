package com.github.pdhbe.stocksymbolmaxprofitcalculator.infrastructure.twelve;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class TwelveResponseDto {
    @JsonProperty("values")
    private List<TwelveDto> twelveDtoList;
}
