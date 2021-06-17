package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi.yahoo;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dto.StockDto;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi.StockAPIInterface;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class YahooFinanceAPI implements StockAPIInterface {
    private static final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<StockDto> getDailyStockList(String stockSymbol) {
        return convertToStockDtoList(getYahooDtoList(getYahooResponse(stockSymbol)));
    }

    private List<StockDto> convertToStockDtoList(List<YahooDto> yahooDtoList) {
        List<StockDto> stockDtoList = new ArrayList<>();
        yahooDtoList.stream().limit(180).forEach(yahooDto -> {
            stockDtoList.add(
                    StockDto.builder()
                    .date(convertEpochSecondToLocalDate(yahooDto.getDate()))
                    .lowPrice(yahooDto.getLow())
                    .highPrice(yahooDto.getHigh())
                    .build()
            );
        });
        return stockDtoList;
    }

    private LocalDate convertEpochSecondToLocalDate(Long date) {
        return LocalDate.ofInstant(Instant.ofEpochSecond(date), ZoneId.systemDefault());
    }

    private List<YahooDto> getYahooDtoList(ResponseEntity<YahooResponse> yahooResponse) {
        return yahooResponse.getBody().getYahooDtoList();
    }

    private ResponseEntity<YahooResponse> getYahooResponse(String stockSymbol) {
        String url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data" +
                "?symbol=" + stockSymbol + "&region=US";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("x-rapidapi-key", "e5fab0f5dbmsha8d96ae52186c72p13eeaejsn3f1d1394b597");
        httpHeaders.set("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com");

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, YahooResponse.class);
    }
}