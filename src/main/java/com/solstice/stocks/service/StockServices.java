package com.solstice.stocks.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solstice.stocks.data.StockSymbolRepository;
import com.solstice.stocks.model.StockQuote;
import com.solstice.stocks.model.StockSummary;
import com.solstice.stocks.model.StockSymbol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;


@Component
@PropertySource("classpath:application.properties")
public class StockServices {

    private StockSymbolRepository symbolRepository;
    private ObjectMapper mapper = new ObjectMapper();

    @Value("${datasource.url}")
    private URL dataUrl;

    public StockServices(StockSymbolRepository repository) {

        this.symbolRepository = repository;

    }

    public List<StockQuote> getAllStocks(){

        List<StockQuote> stock_list = null;

        try {

            stock_list = mapper.readValue(dataUrl, new TypeReference<List<StockQuote>>() {
            });

        }
        catch( Exception e ){

            System.err.println(e.getMessage());

        }
        return stock_list;
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

    public StockSummary getSummary(String stock_in, String date_in){

        String[] dateSet = date_in.split("-");

        if(dateSet.length == 3) {

            int year_in = Integer.valueOf(dateSet[0]);
            int month_in = Integer.valueOf(dateSet[1]);
            int day_in = Integer.valueOf(dateSet[2]);
            return symbolRepository.dailySummaryQuery(stock_in.toUpperCase(), year_in, month_in, day_in);

        }
        else if( dateSet.length == 2){

            int year_in = Integer.valueOf(dateSet[0]);
            int month_in = Integer.valueOf(dateSet[1]);
            return symbolRepository.monthlySummaryQuery(stock_in.toUpperCase(), year_in, month_in);
        }
        else return new StockSummary();
    }

}