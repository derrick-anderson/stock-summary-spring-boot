package com.solstice.stocks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;


@Component
public class StockServices {

    private StockQuoteRepository repository;
    public StockServices(StockQuoteRepository repository) {
        this.repository = repository;
    }
    private ObjectMapper mapper = new ObjectMapper();

    public List<StockQuote> getAllStocks(){


        List<StockQuote> stock_list = null;
        try {
            stock_list = mapper.readValue(new URL("https://bootcamp-training-files.cfapps.io/week2/week2-stocks.json"), new TypeReference<List<StockQuote>>() {
            });
        }catch( Exception e ){
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
        } catch (Exception e) {
            return "ISSUE";
        }
    }

    public String printSummary(StockSummary summary){
        try{
            return mapper.writeValueAsString(summary);
        }catch (Exception e){
            return "ISSUE";
        }
    }
}
