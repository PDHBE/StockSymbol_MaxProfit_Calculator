package com.github.pdhbe.stocksymbolmaxprofitcalculator.infra.yahoo;

import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.model.StockDto;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.model.StockDao;
import com.github.pdhbe.stocksymbolmaxprofitcalculator.domain.model.StockException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class YahooDao implements StockDao {
    private final RestTemplate restTemplate;

    @Override
    public List<StockDto> stocks(String stockSymbol) throws StockException {
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

    private List<YahooDto> getYahooDtoList(ResponseEntity<YahooResponseDto> yahooResponse) {
        return yahooResponse.getBody().getYahooDtoList();
    }

    private ResponseEntity<YahooResponseDto> getYahooResponse(String stockSymbol) throws StockException {
        String url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data" +
                "?symbol=" + stockSymbol + "&region=US";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("x-rapidapi-key", "e5fab0f5dbmsha8d96ae52186c72p13eeaejsn3f1d1394b597");
        httpHeaders.set("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<YahooResponseDto> yahooResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity, YahooResponseDto.class);
        if (yahooResponse.getBody().getYahooDtoList() == null) {
            throw new StockException("Invalid Stock Symbol. Input Again.");
        }
        return yahooResponse;
    }
}