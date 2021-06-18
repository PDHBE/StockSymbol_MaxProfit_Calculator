package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.source.yahoo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class YahooResponse {
    @JsonProperty("prices")
    private List<YahooDto> yahooDtoList;
}
