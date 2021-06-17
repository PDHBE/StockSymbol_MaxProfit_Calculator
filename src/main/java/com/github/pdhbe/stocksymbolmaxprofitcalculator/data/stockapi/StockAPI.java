package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dailyStockInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.List;

public abstract class StockAPI implements StockAPIInterface {
    protected String stockSymbol;
    protected List<dailyStockInfo> dailyStockInfoList;

    protected abstract HttpResponse<String> getResponseFromAPI() throws UnirestException;
    protected abstract void parseResponseToDailyStockInfoList() throws UnirestException, IllegalArgumentException;
}