package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock;

import java.util.List;

public interface StockDtoFetcher {
    List<StockDto> getDailyStockList(String stockSymbol);
}
