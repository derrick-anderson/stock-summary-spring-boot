package com.solstice.stocks;

import com.solstice.stocks.data.StockQuoteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class StockSummaryController {

    private StockQuoteRepository repository;
    private StockServices stockServices;

    public StockSummaryController(StockQuoteRepository repository, StockServices stockServices) {

        this.repository = repository;
        this.stockServices = stockServices;

    }

    @GetMapping("/")
    public String home(){

        return "Welcome to the APP!";

    }

    @PostMapping("/load")
    public void load_data(){

        stockServices.loadStocks();

    }

    /*
    @GetMapping("/{STOCK}/getall")
    public String getAllStocks(@PathVariable("STOCK") String symbol){

        return stockServices.printStocks(repository.findAllBySymbol(symbol));

    }*/

    @GetMapping("/{STOCK}/{DATE}")
    public String getSummary(@PathVariable("STOCK") String stock_in, @PathVariable("DATE") String date_in){

        return stockServices.printSummary(stockServices.getSummary(stock_in, date_in));

    }
}
