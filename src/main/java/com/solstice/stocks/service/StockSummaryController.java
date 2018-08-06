package com.solstice.stocks.service;

import com.solstice.stocks.data.StockSymbolRepository;
import com.solstice.stocks.service.StockServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class StockSummaryController {

    private StockSymbolRepository symbolRepository;
    private StockServices stockServices;

    public StockSummaryController(StockSymbolRepository symbolRepository, StockServices stockServices) {

        this.symbolRepository = symbolRepository;
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

    @GetMapping("/{STOCK}/{DATE}")
    public String getSummary(@PathVariable("STOCK") String stock_in, @PathVariable("DATE") String date_in){

        return stockServices.printSummary(stockServices.getSummary(stock_in, date_in));

    }
}