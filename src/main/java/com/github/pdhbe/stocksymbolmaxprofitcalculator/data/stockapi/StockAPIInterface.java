package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dto.StockDto;

import java.util.List;

public interface StockAPIInterface {
    List<StockDto> getDailyStockList(String stockSymbol);
}
