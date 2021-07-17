package com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.service;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.model.StockDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MaxProfitCalculationService {
    private static final int FIRST_DAY = 0;
    private static final int SECOND_DAY = 1;

    public MaxProfitDto calculate(String stockSymbol, List<StockDto> dailyStockDtoList) throws MaxProfitException {
        if (dailyStockDtoList.size() < 2) {
            throw new LessThanTwoDaysException();
        }

        dailyStockDtoList.sort(Comparator.comparing(StockDto::getDate));

        double minLowPrice = dailyStockDtoList.get(FIRST_DAY).getLowPrice();
        int buyDayForMaxProfit = FIRST_DAY;
        double maxProfit = dailyStockDtoList.get(SECOND_DAY).getHighPrice() - dailyStockDtoList.get(FIRST_DAY).getLowPrice();
        int sellDayForMaxProfit = SECOND_DAY;

        for (int today = SECOND_DAY; today < dailyStockDtoList.size(); today++) {
            double todayHighPrice = dailyStockDtoList.get(today).getHighPrice();
            double todayLowPrice = dailyStockDtoList.get(today).getLowPrice();
            int dayOfMinLowPrice = 0;

            if (todayHighPrice - minLowPrice > maxProfit) {
                maxProfit = todayHighPrice - minLowPrice;
                sellDayForMaxProfit = today;
                buyDayForMaxProfit = dayOfMinLowPrice;
            }

            if (todayLowPrice < minLowPrice) {
                minLowPrice = todayLowPrice;
                dayOfMinLowPrice = today;
            }
        }

        if (maxProfit == 0) {
            throw new ZeroProfitException();
        }
        if (maxProfit < 0) {
            throw new MinusProfitException();
        }

        return MaxProfitDto.builder()
                .stockSymbol(stockSymbol)
                .buyDate(dailyStockDtoList.get(buyDayForMaxProfit).getDate())
                .sellDate(dailyStockDtoList.get(sellDayForMaxProfit).getDate())
                .maxProfit(maxProfit)
                .build();
    }
}
