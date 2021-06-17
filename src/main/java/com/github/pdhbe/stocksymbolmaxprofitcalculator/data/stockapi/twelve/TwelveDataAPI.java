package com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi.twelve;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.dto.StockDto;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.data.stockapi.StockAPIInterface;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TwelveDataAPI implements StockAPIInterface {
    private static final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<StockDto> getDailyStockList(String stockSymbol) {
        return convertToStockDtoList(getTwelveDtoList(getTwelveResponse(stockSymbol)));
    }

    private List<StockDto> convertToStockDtoList(List<TwelveDto> twelveDtoList) {
        List<StockDto> stockDtoList = new ArrayList<>();
        twelveDtoList.forEach(twelveDto -> {
            stockDtoList.add(
                    StockDto.builder()
                    .date(LocalDate.parse(twelveDto.getDatetime()))
                    .lowPrice(Double.parseDouble(twelveDto.getLow()))
                    .highPrice(Double.parseDouble(twelveDto.getHigh()))
                    .build()
            );
        });
        return stockDtoList;
    }

    private List<TwelveDto> getTwelveDtoList(ResponseEntity<TwelveResponse> twelveResponse) {
        return twelveResponse.getBody().getTwelveDtoList();
    }

    private ResponseEntity<TwelveResponse> getTwelveResponse(String stockSymbol) {
        String url = "https://twelve-data1.p.rapidapi.com/time_series" +
                "?symbol=" + stockSymbol + "&interval=1day&outputsize=180&format=json";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("x-rapidapi-key", "e5fab0f5dbmsha8d96ae52186c72p13eeaejsn3f1d1394b597");
        httpHeaders.set("x-rapidapi-host", "twelve-data1.p.rapidapi.com");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, TwelveResponse.class);
    }
}
