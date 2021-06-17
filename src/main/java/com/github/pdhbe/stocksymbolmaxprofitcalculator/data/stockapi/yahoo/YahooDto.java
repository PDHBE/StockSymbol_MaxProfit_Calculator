package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi.yahoo;

import lombok.Data;

@Data
public class YahooDto {
    private Long date;
    private double low;
    private double high;
}
