package com.github.pdhbe.stocksymbolmaxprofit.business;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.business.MaxProfitCalculator;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock.StockDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MaxProfitCalculatorTest {
    private static final MaxProfitCalculator MAX_PROFIT_CALCULATOR = new MaxProfitCalculator();

    @Test
    @DisplayName("이틀 미만의 주식 정보 리스트 입력")
    void lessThanTwoDays() {
        ArrayList<StockDto> stockDtoList = new ArrayList<>();
        stockDtoList.add(StockDto.builder()
                .date(LocalDate.of(2021, 6, 2))
                .lowPrice(2)
                .highPrice(2)
                .build());

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            MAX_PROFIT_CALCULATOR.calculateMaxProfit(stockDtoList);
        });
        assertEquals("It should be at least two days.", illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("값이 모두 같은 경우")
    void allPricesSame() {
        ArrayList<StockDto> stockDtoList = new ArrayList<>();
        stockDtoList.add(StockDto.builder()
                .date(LocalDate.of(2021, 6, 1))
                .lowPrice(1)
                .highPrice(1)
                .build());

        stockDtoList.add(StockDto.builder()
                .date(LocalDate.of(2021, 6, 2))
                .lowPrice(1)
                .highPrice(1)
                .build());

        stockDtoList.add(StockDto.builder()
                .date(LocalDate.of(2021, 6, 3))
                .lowPrice(1)
                .highPrice(1)
                .build());

        Exception exception = assertThrows(Exception.class, () -> {
            MAX_PROFIT_CALCULATOR.calculateMaxProfit(stockDtoList);
        });
        assertEquals("Cannot make a profit because All prices are same.", exception.getMessage());
    }

    @Test
    @DisplayName("값이 계속 떨어지는 경우")
    void keepFalling() {
        ArrayList<StockDto> stockDtoList = new ArrayList<>();
        stockDtoList.add(StockDto.builder()
                .date(LocalDate.of(2021, 6, 1))
                .lowPrice(3)
                .highPrice(3)
                .build());

        stockDtoList.add(StockDto.builder()
                .date(LocalDate.of(2021, 6, 2))
                .lowPrice(2)
                .highPrice(2)
                .build());

        stockDtoList.add(StockDto.builder()
                .date(LocalDate.of(2021, 6, 3))
                .lowPrice(1)
                .highPrice(1)
                .build());

        Exception exception = assertThrows(Exception.class, () -> {
            MAX_PROFIT_CALCULATOR.calculateMaxProfit(stockDtoList);
        });
        assertEquals("Cannot make a profit because the price keeps falling.", exception.getMessage());
    }

//    @Test
//    @DisplayName("두 API의 같은 주식 리스트에 대하여, 같은 결과를 출력하는지 테스트")
//    void test() throws Exception {
//        YahooFinanceAPI yahooFinanceAPI = new YahooFinanceAPI();
//        TwelveDataAPI twelveDataAPI = new TwelveDataAPI();
//
//        List<StockDto> yahooStockList = yahooFinanceAPI.getDailyStockInfoList("AAPL");
//        List<StockDto> twelveStockList = twelveDataAPI.getDailyStockInfoList("AAPL");
//
//        maxProfitProcessor.setDailyStockInfoList(yahooStockList);
//        String yahooResult = maxProfitProcessor.toString();
////        STring yaResult = maxProfitProcessor.calculateDSIL(yahooResult);
//        maxProfitProcessor.setDailyStockInfoList(twelveStockList);
//        String twelveResult = maxProfitProcessor.toString();
//
//        assertEquals(yahooResult,twelveResult);
//    }
}