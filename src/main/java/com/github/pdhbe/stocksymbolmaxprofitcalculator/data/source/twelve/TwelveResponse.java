package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.source.twelve;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TwelveResponse {
    @JsonProperty("values")
    private List<TwelveDto> twelveDtoList;
}
