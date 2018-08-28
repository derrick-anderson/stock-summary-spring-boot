package com.solstice.stocks.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solstice.stocks.data.StockQuoteRepository;
import com.solstice.stocks.model.StockQuote;
import com.solstice.stocks.model.StockSummary;
import com.solstice.stocks.model.StockSymbol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Component
@PropertySource("classpath:application.properties")
public class StockServices {

    private RestTemplate restTemplate = new RestTemplate();
    private StockQuoteRepository stockQuoteRepository;
    private ObjectMapper mapper = new ObjectMapper();

    @Value("${datasource.url}")
    private URL dataUrl;

    @Value("${stockService.url}")
    private URL stockServiceURL;


    public StockServices(StockQuoteRepository stockQuoteRepository) {
        this.stockQuoteRepository = stockQuoteRepository;
    }


    public void loadStocks() {

        stockQuoteRepository.saveAll(getAllStocks());

    }


    public List<StockQuote> getAllStocks() {

        List<StockQuote> stockList = null;

        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS'+0000'");
            mapper.setDateFormat(df);
            stockList = mapper.readValue(dataUrl, new TypeReference<List<StockQuote>>() {
            });

        } catch (Exception e) {

            System.err.println(e.getMessage());

        }
        return stockList;
    }


    public String getDateFormat(String dateIn) {
        String[] dateParts = dateIn.split("-");

        String dateFormat = "%Y-%m";
        if (dateParts.length == 3) {
            dateFormat = "%Y-%m-%d";
        }
        return dateFormat;
    }


    public StockSummary getSummary(String symbol, String dateIn) {

        String dateFormat = getDateFormat(dateIn);
        String symbolId = getIdFromSymbol(symbol).getId();

        List<StockQuote> quotes = stockQuoteRepository.getAllQuotesForDate(symbolId, dateIn, dateFormat);

        Integer totalVolume = getTotalVolume(quotes);
        BigDecimal openPrice = getOpeningPrice(quotes);
        BigDecimal highPrice = getHighPrice(quotes);
        BigDecimal lowPrice = getLowPrice(quotes);
        BigDecimal closePrice = getClosingPrice(quotes);

        StockSummary summary = new StockSummary(symbol, openPrice, lowPrice, highPrice, closePrice, totalVolume);

        return summary;

    }


    public Integer getTotalVolume(List<StockQuote> quotes){

        Integer totalVolume = quotes.stream()
                .collect(Collectors.summingInt(StockQuote::getVolume));

        return totalVolume;
    }


    public BigDecimal getHighPrice(List<StockQuote> quotes){

        Optional<StockQuote> highQuote = quotes.stream()
                .collect(Collectors.maxBy(Comparator.comparing(StockQuote::getPrice)));

        return highQuote.isPresent()? highQuote.get().getPrice(): null;
    }


    public BigDecimal getLowPrice(List<StockQuote> quotes){

        Optional<StockQuote> lowQuote = quotes.stream()
                .collect(Collectors.minBy(Comparator.comparing(StockQuote::getPrice)));

        return lowQuote.isPresent()? lowQuote.get().getPrice(): null;
    }


    public BigDecimal getOpeningPrice(List<StockQuote> quotes){

        Optional<StockQuote> openQuote = quotes.stream()
                .collect(Collectors.minBy(Comparator.comparing(StockQuote::getDate)));

        return openQuote.isPresent()? openQuote.get().getPrice(): null;

    }


    public BigDecimal getClosingPrice(List<StockQuote> quotes){

        Optional<StockQuote> closePrice = quotes.stream()
                .collect(Collectors.maxBy(Comparator.comparing(StockQuote::getDate)));

        return closePrice.isPresent()? closePrice.get().getPrice(): null;

    }


    public StockSymbol getIdFromSymbol(String symbol){

        StockSymbol symbolResult = restTemplate.getForObject(stockServiceURL +"/ids/" + symbol, StockSymbol.class);
        return symbolResult;

    }
}