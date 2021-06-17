package com.github.pdhbe.stocksymbolmaxprofit.data.stockapi;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dailyStockInfo;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi.StockAPI;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi.TwelveDataAPI;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi.YahooFinanceAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockAPITest {
    private static final StockAPI yahooFinanceAPI = new YahooFinanceAPI();
    private static final StockAPI twelveDataAPI = new TwelveDataAPI();

//    @Test
//    @DisplayName("yahooAPI, 유효하지 않은 심볼 입력")
//    void yahooAPI_inValidSymbol() {
//        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
//            yahooFinanceAPI.getDailyStockInfoList("1234");
//        });
//        assertEquals("InValid Input. Please Input Valid US Stock Symbol", illegalArgumentException.getMessage());
//
//        illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
//            yahooFinanceAPI.getDailyStockInfoList("-100");
//        });
//        assertEquals("InValid Input. Please Input Valid US Stock Symbol", illegalArgumentException.getMessage());
//
//        illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
//            yahooFinanceAPI.getDailyStockInfoList("abcde");
//        });
//        assertEquals("InValid Input. Please Input Valid US Stock Symbol", illegalArgumentException.getMessage());
//
//        illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
//            yahooFinanceAPI.getDailyStockInfoList("hello!");
//        });
//        assertEquals("InValid Input. Please Input Valid US Stock Symbol", illegalArgumentException.getMessage());
//    }

    @Test
    @DisplayName("twelveAPI, 유효하지 않은 심볼 입력")
    void twelveAPI_inValidSymbol() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            twelveDataAPI.getDailyStockInfoList("1234");
        });
        assertEquals("InValid Input. Please Input Valid US Stock Symbol", illegalArgumentException.getMessage());

        illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            twelveDataAPI.getDailyStockInfoList("-100");
        });
        assertEquals("InValid Input. Please Input Valid US Stock Symbol", illegalArgumentException.getMessage());

        illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            twelveDataAPI.getDailyStockInfoList("abcde");
        });
        assertEquals("InValid Input. Please Input Valid US Stock Symbol", illegalArgumentException.getMessage());

        illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            twelveDataAPI.getDailyStockInfoList("hello!");
        });
        assertEquals("InValid Input. Please Input Valid US Stock Symbol", illegalArgumentException.getMessage());
    }

//    @Test
//    @DisplayName("yahooAPI, 유효한 심볼 입력")
//    void yahooAPI_ValidSymbol() throws Exception {
//        List<StockDto> dailyStockInfoList = yahooFinanceAPI.getDailyStockInfoList("AAPL");
//        assertTrue(dailyStockInfoList.size() >= 180);
//    }

    @Test
    @DisplayName("twelveAPI, 유효한 심볼 입력")
    void twelveAPI_ValidSymbol() throws Exception {
        List<dailyStockInfo> dailyStockInfoList = twelveDataAPI.getDailyStockInfoList("GOOG");
        assertTrue(dailyStockInfoList.size() >= 180);
    }
}