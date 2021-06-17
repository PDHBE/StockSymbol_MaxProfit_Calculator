package com.github.pdhbe.stocksymbolmaxprofitcalculator.business;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dailyStockInfo;

import java.util.Comparator;
import java.util.List;

public class MaxProfitProcessor {
    private static final int FIRST_DAY = 0;
    private static final int SECOND_DAY = 1;
    private static final int LIMIT_OF_DAY = 180;

    private List<dailyStockInfo> dailyStockInfoList;
    private dailyStockInfo buyDateOfMaxProfit;
    private dailyStockInfo sellDateOfMaxProfit;
    private int lastDay;

    public void setDailyStockInfoList(List<dailyStockInfo> dailyStockInfoList) throws Exception {
        if (dailyStockInfoList.size() < 2) {
            throw new IllegalArgumentException("It should be at least two days.");
        }
        this.dailyStockInfoList = dailyStockInfoList;
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

        double minLowPrice = this.dailyStockInfoList.get(FIRST_DAY).getLowPrice();
        int buyDayForMaxProfit = FIRST_DAY;
        double maxProfit = this.dailyStockInfoList.get(SECOND_DAY).getHighPrice() - this.dailyStockInfoList.get(FIRST_DAY).getLowPrice();
        int sellDayForMaxProfit = SECOND_DAY;

        for (int today = SECOND_DAY; today < this.lastDay; today++) {
            double todayHighPrice = this.dailyStockInfoList.get(today).getHighPrice();
            double todayLowPrice = this.dailyStockInfoList.get(today).getLowPrice();
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

        this.buyDateOfMaxProfit = this.dailyStockInfoList.get(buyDayForMaxProfit);
        this.sellDateOfMaxProfit = this.dailyStockInfoList.get(sellDayForMaxProfit);
    }

    private void cutDailyStockInfoListToLate180Dates() {
        if (this.dailyStockInfoList.size() < 180) {
            this.lastDay = this.dailyStockInfoList.size();
            return;
        }
        this.lastDay = LIMIT_OF_DAY;
        this.dailyStockInfoList = this.dailyStockInfoList.subList(FIRST_DAY, this.lastDay);
    }

    private void orderDailyStockInfoListByDateASC() {
        this.dailyStockInfoList.sort(Comparator.comparing(dailyStockInfo::getDate));
    }
}
