package com.github.pdhbe.stocksymbolmaxprofitcalculator.business;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stock.StockDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class MaxProfitCalculator {
    private static final int FIRST_DAY = 0;
    private static final int SECOND_DAY = 1;

    public MaxProfitDto calculate(String stockSymbol, List<StockDto> dailyStockList) throws MaxProfitException {
        if (dailyStockList.size() < 2) {
            throw new MaxProfitException("It should be at least two days.");
        }

        dailyStockList.sort(Comparator.comparing(StockDto::getDate));

        double minLowPrice = dailyStockList.get(FIRST_DAY).getLowPrice();
        int buyDayForMaxProfit = FIRST_DAY;
        double maxProfit = dailyStockList.get(SECOND_DAY).getHighPrice() - dailyStockList.get(FIRST_DAY).getLowPrice();
        int sellDayForMaxProfit = SECOND_DAY;

        for (int today = SECOND_DAY; today < dailyStockList.size(); today++) {
            double todayHighPrice = dailyStockList.get(today).getHighPrice();
            double todayLowPrice = dailyStockList.get(today).getLowPrice();
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
            throw new MaxProfitException("Cannot make a profit because all prices are same.");
        }
        if (maxProfit < 0) {
            throw new MaxProfitException("Cannot make a profit because the price keeps falling.");
        }

        return MaxProfitDto.builder()
                .stockSymbol(stockSymbol)
                .buyDate(dailyStockList.get(buyDayForMaxProfit).getDate())
                .sellDate(dailyStockList.get(sellDayForMaxProfit).getDate())
                .maxProfit(maxProfit).build();
    }
}
