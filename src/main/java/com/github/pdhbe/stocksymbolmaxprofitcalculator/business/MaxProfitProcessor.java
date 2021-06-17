package com.github.pdhbe.stocksymbolmaxprofitcalculator.business;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dto.StockDto;

import java.util.Comparator;
import java.util.List;

public class MaxProfitProcessor {
    private static final int FIRST_DAY = 0;
    private static final int SECOND_DAY = 1;
    private static final int LIMIT_OF_DAY = 180;

    private List<StockDto> stockDtoList;
    private StockDto buyDateOfMaxProfit;
    private StockDto sellDateOfMaxProfit;
    private int lastDay;

    public void setDailyStockInfoList(List<StockDto> stockDtoList) throws Exception {
        if (stockDtoList.size() < 2) {
            throw new IllegalArgumentException("It should be at least two days.");
        }
        this.stockDtoList = stockDtoList;
        this.competeMaxProfit();
    }

    @Override
    public String toString() {
        return "buy date : " + this.buyDateOfMaxProfit.getDate() + ", buy price : " + this.buyDateOfMaxProfit.getLowPrice() + "\n"
                + "sell date : " + this.sellDateOfMaxProfit.getDate() + ", sell price : " + this.sellDateOfMaxProfit.getHighPrice() + "\n"
                + "max profit : " + (this.sellDateOfMaxProfit.getHighPrice() - this.buyDateOfMaxProfit.getLowPrice());
    }

    private void competeMaxProfit() throws Exception {
        this.cutDailyStockInfoListToLate180Dates();
        this.orderDailyStockInfoListByDateASC();

        double minLowPrice = this.stockDtoList.get(FIRST_DAY).getLowPrice();
        int buyDayForMaxProfit = FIRST_DAY;
        double maxProfit = this.stockDtoList.get(SECOND_DAY).getHighPrice() - this.stockDtoList.get(FIRST_DAY).getLowPrice();
        int sellDayForMaxProfit = SECOND_DAY;

        for (int today = SECOND_DAY; today < this.lastDay; today++) {
            double todayHighPrice = this.stockDtoList.get(today).getHighPrice();
            double todayLowPrice = this.stockDtoList.get(today).getLowPrice();
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
            throw new Exception("Cannot make a profit because All prices are same.");
        }
        if (maxProfit < 0) {
            throw new Exception("Cannot make a profit because the price keeps falling.");
        }

        this.buyDateOfMaxProfit = this.stockDtoList.get(buyDayForMaxProfit);
        this.sellDateOfMaxProfit = this.stockDtoList.get(sellDayForMaxProfit);
    }

    private void cutDailyStockInfoListToLate180Dates() {
        if (this.stockDtoList.size() < 180) {
            this.lastDay = this.stockDtoList.size();
            return;
        }
        this.lastDay = LIMIT_OF_DAY;
        this.stockDtoList = this.stockDtoList.subList(FIRST_DAY, this.lastDay);
    }

    private void orderDailyStockInfoListByDateASC() {
        this.stockDtoList.sort(Comparator.comparing(StockDto::getDate));
    }
}
