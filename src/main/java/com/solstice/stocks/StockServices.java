package com.solstice.stocks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;


@Component
public class StockServices {

    private StockQuoteRepository repository;
    private ObjectMapper mapper = new ObjectMapper();

    public StockServices(StockQuoteRepository repository) {

        this.repository = repository;

    }

    public List<StockQuote> getAllStocks(){

        List<StockQuote> stock_list = null;

        try {

            stock_list = mapper.readValue(new URL("https://bootcamp-training-files.cfapps.io/week2/week2-stocks.json"), new TypeReference<List<StockQuote>>() {
            });

        }
        catch( Exception e ){

            System.err.println(e.getMessage());

        }
        return stock_list;
    }


    public void loadStocks(){

        repository.saveAll(getAllStocks());

    }


    public String printStocks(List<StockQuote> stockQuotes) {

        try {

            return mapper.writeValueAsString(stockQuotes);

        }
        catch (Exception e) {

            return "ISSUE";

        }
    }


    public StockSummary getSummary(String stock_in, String date_in){

        String[] dateSet = date_in.split("-");

        if(dateSet.length == 3) {

            int year_in = Integer.valueOf(dateSet[0]);
            int month_in = Integer.valueOf(dateSet[1]);
            int day_in = Integer.valueOf(dateSet[2]);
            return repository.dailySummaryQuery(stock_in.toUpperCase(), year_in, month_in, day_in);

        }
        else if( dateSet.length == 2){

            int year_in = Integer.valueOf(dateSet[0]);
            int month_in = Integer.valueOf(dateSet[1]);
            return repository.monthlySummaryQuery(stock_in.toUpperCase(), year_in, month_in);

        }
        else return new StockSummary();
    }


    public String printSummary(StockSummary summary){

        try{
            return mapper.writeValueAsString(summary);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }

    }
}