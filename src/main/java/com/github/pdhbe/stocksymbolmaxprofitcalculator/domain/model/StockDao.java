package com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.model;

import java.util.List;

public interface StockDao {
    List<StockDto> stocks(String stockSymbol);
}
