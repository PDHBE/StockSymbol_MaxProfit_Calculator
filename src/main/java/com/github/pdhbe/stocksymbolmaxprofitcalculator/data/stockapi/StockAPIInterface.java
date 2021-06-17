package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dailyStockInfo;

import java.util.List;

public interface StockAPIInterface {
    List<dailyStockInfo> getDailyStockInfoList(String stockSymbol) throws Exception;
}
