package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dailyStockInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class YahooFinanceAPI extends StockAPI {
    public YahooFinanceAPI() {
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
        return Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data?symbol="+this.stockSymbol+"&region=US")
                .header("x-rapidapi-key", "e5fab0f5dbmsha8d96ae52186c72p13eeaejsn3f1d1394b597")
                .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .asString();
    }

    @Override
    protected void parseResponseToDailyStockInfoList() throws UnirestException, IllegalArgumentException {
        JSONObject jsonObject = new JSONObject(this.getResponseFromAPI().getBody());
        if (!jsonObject.has("prices")) {
            throw new IllegalArgumentException("InValid Input. Please Input Valid US Stock Symbol");
        }

        JSONArray jsonStockInfoList = jsonObject.getJSONArray("prices");
        for (int i = 0; i < jsonStockInfoList.length(); i++) {
            JSONObject jsonStockInfo = jsonStockInfoList.getJSONObject(i);
            if (!jsonStockInfo.has("low")) {
                continue;
            }
            dailyStockInfo dailyStockInfo = this.convertJsonStockInfoToDailyStockInfo(jsonStockInfo);
            this.dailyStockInfoList.add(dailyStockInfo);
        }
    }

    private LocalDate convertUnixSecondsToLocalDate(Long unixSeconds) {
        return LocalDate.ofInstant(Instant.ofEpochSecond(unixSeconds), ZoneId.of("GMT-4"));
    }

    private dailyStockInfo convertJsonStockInfoToDailyStockInfo(JSONObject jsonStockInfo){
        return dailyStockInfo.builder()
                .date(convertUnixSecondsToLocalDate(jsonStockInfo.getLong("date")))
                .lowPrice(jsonStockInfo.getDouble("low"))
                .highPrice(jsonStockInfo.getDouble("high"))
                .build();
    }

//    private HttpResponse<String> getResponseFromAPI() throws UnirestException {
//        return Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data?symbol=" + this.stockSymbol + "&region=US")
//                .header("x-rapidapi-key", "e5fab0f5dbmsha8d96ae52186c72p13eeaejsn3f1d1394b597")
//                .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
//                .asString();
//    }
}


//    private void makeDailyStockInfoList() throws UnirestException, IllegalArgumentException, JsonProcessingException {
//        HierarchicalOuter hierarchicalOuter = this.parseResponseBodyToHierarchicalOuter(this.getResponseBodyFromAPI());
//        this.convertHierarchicalInnerListToDailyStockInfoList(hierarchicalOuter.getHierarchicalInnerList());
//    }
//
//    private void convertHierarchicalInnerListToDailyStockInfoList(ArrayList<HierarchicalInner> hierarchicalInnerList) {
//        this.dailyStockInfoList = new ArrayList<>();
//        for (HierarchicalInner hierarchicalInner : hierarchicalInnerList) {
//            this.convertUnixSecondsToLocalDate(hierarchicalInner);
//            DailyStockInfo dailyStockInfo = modelMapper.map(hierarchicalInner, DailyStockInfo.class);
//            this.dailyStockInfoList.add(dailyStockInfo);
//        }
//    }

//    private void convertUnixSecondsToLocalDate(HierarchicalInner hierarchicalInner) {
//        long unixSeconds = hierarchicalInner.getUnixSeconds();
//        LocalDate localDate = LocalDate.ofInstant(Instant.ofEpochSecond(unixSeconds), ZoneId.of("GMT-4"));
//        hierarchicalInner.setDate(localDate);
//    }

//    @JsonIgnoreProperties(ignoreUnknown = true)
//    @Getter
//    static class HierarchicalOuter {
//        @JsonProperty("prices")
//        private ArrayList<HierarchicalInner> hierarchicalInnerList;
//    }
//
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    @Getter
//    static class HierarchicalInner {
//        @JsonProperty("date")
//        private long unixSeconds;
//        @JsonProperty("low")
//        private double lowPrice;
//        @JsonProperty("high")
//        private double highPrice;
//        @JsonIgnore
//        private LocalDate date;
//
//        public void setDate(LocalDate date) {
//            this.date = date;
//        }
//    }

//    private HierarchicalOuter parseResponseBodyToHierarchicalOuter(String responseBody) throws JsonProcessingException {
//        return objectMapper.readValue(responseBody, HierarchicalOuter.class);
//    }