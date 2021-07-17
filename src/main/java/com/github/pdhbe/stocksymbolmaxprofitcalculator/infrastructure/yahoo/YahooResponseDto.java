package com.github.pdhbe.stocksymbolmaxprofitcalculator.infrastructure.yahoo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class YahooResponseDto {
    @JsonProperty("prices")
    private List<YahooDto> yahooDtoList;
}
