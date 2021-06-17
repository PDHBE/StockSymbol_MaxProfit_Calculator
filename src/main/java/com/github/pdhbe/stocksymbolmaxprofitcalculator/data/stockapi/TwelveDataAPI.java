package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dailyStockInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TwelveDataAPI extends StockAPI {
    public TwelveDataAPI() {
        this.dailyStockInfoList = new ArrayList<>();
    }

    @Override
    public List<dailyStockInfo> getDailyStockInfoList(String stockSymbol) throws Exception {
        this.stockSymbol = stockSymbol;
        this.parseResponseToDailyStockInfoList();
        return this.dailyStockInfoList;
    }

    @Override
    protected HttpResponse<String> getResponseFromAPI() throws UnirestException {
        return Unirest.get("https://twelve-data1.p.rapidapi.com/time_series?symbol=" + this.stockSymbol + "&interval=1day&outputsize=180&format=json")
                .header("x-rapidapi-key", "e5fab0f5dbmsha8d96ae52186c72p13eeaejsn3f1d1394b597")
                .header("x-rapidapi-host", "twelve-data1.p.rapidapi.com")
                .asString();
    }

    @Override
    protected void parseResponseToDailyStockInfoList() throws UnirestException, IllegalArgumentException {
        JSONObject jsonObject = new JSONObject(this.getResponseFromAPI().getBody());
        if(jsonObject.getString("status").equals("error")){
            throw new IllegalArgumentException("InValid Input. Please Input Valid US Stock Symbol");
        }

        JSONArray jsonStockInfoList = jsonObject.getJSONArray("values");
        for (int i = 0; i < jsonStockInfoList.length(); i++) {
            JSONObject jsonStockInfo = jsonStockInfoList.getJSONObject(i);
            dailyStockInfo dailyStockInfo = convertJsonStockInfoToDailyStockInfo(jsonStockInfo);
            this.dailyStockInfoList.add(dailyStockInfo);
        }
    }

    private dailyStockInfo convertJsonStockInfoToDailyStockInfo(JSONObject jsonStockInfo) {
        return dailyStockInfo.builder()
                .date(convertStringDateToLocalDate(jsonStockInfo.getString("datetime")))
                .lowPrice(jsonStockInfo.getDouble("low"))
                .highPrice(jsonStockInfo.getDouble("high"))
                .build();
    }

    private LocalDate convertStringDateToLocalDate(String date) {
        return LocalDate.parse(date);
    }
}
