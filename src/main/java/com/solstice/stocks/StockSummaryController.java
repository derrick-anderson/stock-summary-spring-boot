package com.solstice.stocks;

import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/summary")
    public String getsummary(){
        return stockServices.printSummary(repository.dailySummaryQuery("AAPL", "22","06", "2018"));
    }
}
