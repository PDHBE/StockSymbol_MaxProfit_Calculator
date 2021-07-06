package com.github.pdhbe.stocksymbolmaxprofitcalculator.infra.twelve;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TwelveResponseDto {
    @JsonProperty("values")
    private List<TwelveDto> twelveDtoList;
}
