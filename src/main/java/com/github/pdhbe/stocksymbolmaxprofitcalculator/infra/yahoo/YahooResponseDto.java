package com.github.pdhbe.stocksymbolmaxprofitcalculator.infra.yahoo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class YahooResponseDto {
    @JsonProperty("prices")
    private List<YahooDto> yahooDtoList;
}
