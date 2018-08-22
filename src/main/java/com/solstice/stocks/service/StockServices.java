package com.solstice.stocks.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solstice.stocks.data.StockQuoteRepository;
import com.solstice.stocks.data.StockSymbolRepository;
import com.solstice.stocks.model.StockQuote;
import com.solstice.stocks.model.StockSummary;
import com.solstice.stocks.model.StockSymbol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;


@Component
@PropertySource("classpath:application.properties")
public class StockServices {

    private StockSymbolRepository symbolRepository;
    private StockQuoteRepository stockQuoteRepository;
    private ObjectMapper mapper = new ObjectMapper();

    @Value("${datasource.url}")
    private URL dataUrl;

    public StockServices(StockSymbolRepository symbolRepository, StockQuoteRepository stockQuoteRepository) {
        this.symbolRepository = symbolRepository;
        this.stockQuoteRepository = stockQuoteRepository;
    }

    public List<StockQuote> getAllStocks(){

        List<StockQuote> stockList = null;

        try {

            stockList = mapper.readValue(dataUrl, new TypeReference<List<StockQuote>>() {
            });

        }
        catch( Exception e ){

            System.err.println(e.getMessage());

        }
        return stockList;
    }


    public Set<StockSymbol> getGroupedSymbols(List<StockQuote> allQuotes){

        Set<StockSymbol> groupedSymbols = new HashSet<>();

        Map<String, List<StockQuote> > allQuotesGrouped = allQuotes.stream().collect(groupingBy(StockQuote::getSymbol));

        for( String symbol : allQuotesGrouped.keySet()){

            StockSymbol entry = new StockSymbol(symbol, allQuotesGrouped.get(symbol));
            groupedSymbols.add(entry);

        }

        return groupedSymbols;
    }

    public void loadStocks(){

        symbolRepository.saveAll(getGroupedSymbols(getAllStocks()));

    }

    public StockSummary getSummary(String stockIn, String dateIn){

        String[] dateSet = dateIn.split("-");

        if(dateSet.length == 3) {

            int yearIn = Integer.valueOf(dateSet[0]);
            int monthIn = Integer.valueOf(dateSet[1]);
            int dayIn = Integer.valueOf(dateSet[2]);
            return symbolRepository.dailySummaryQuery(stockIn.toUpperCase(), yearIn, monthIn, dayIn);

        }
        else if( dateSet.length == 2){

            int yearIn = Integer.valueOf(dateSet[0]);
            int monthIn = Integer.valueOf(dateSet[1]);
            return symbolRepository.monthlySummaryQuery(stockIn.toUpperCase(), yearIn, monthIn);
        }
        else return new StockSummary();
    }


    public Integer getTotalVolume(String symbol, String dateIn){

        List<StockQuote> quotes = new ArrayList<>();

        String[] dateParts = dateIn.split("-");

        String dateFormat = "%Y-%m";
        if(dateParts.length == 3){
            dateFormat = "%Y-%m-%d";
        }

        quotes = stockQuoteRepository.getTotalVolumeForDate(symbol, dateIn, dateFormat);

        Integer totalVolume = quotes.stream()
                .collect(Collectors.summingInt(StockQuote::getVolume));

        return totalVolume;
    }


    public BigDecimal getHighPrice(String symbol, String dateIn){

        String[] dateParts = dateIn.split("-");
        String dateFormat = "%Y-%m";
        if(dateParts.length == 3){
            dateFormat = "%Y-%m-%d";
        }

        return stockQuoteRepository.getHighPriceForDate(symbol,dateIn, dateFormat).getPrice();
    }


    public BigDecimal getLowPrice(String symbol, String dateIn){

        String[] dateParts = dateIn.split("-");
        String dateFormat = "%Y-%m";
        if(dateParts.length == 3){
            dateFormat = "%Y-%m-%d";
        }

        return stockQuoteRepository.getLowPriceForDate(symbol, dateIn, dateFormat).getPrice();
    }


    public BigDecimal getOpenPrice(String symbol, String dateIn){
        String[] dateParts = dateIn.split("-");
        String dateFormat = "%Y-%m";
        if(dateParts.length == 3){
            dateFormat = "%Y-%m-%d";
        }

        return stockQuoteRepository.getOpenPriceForDate(symbol,dateIn,dateFormat).getPrice();

    }
}