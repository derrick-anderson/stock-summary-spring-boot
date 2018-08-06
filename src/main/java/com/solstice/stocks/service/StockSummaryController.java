package com.solstice.stocks.service;

import com.solstice.stocks.model.StockSummary;
import org.springframework.web.bind.annotation.*;

@RestController("/")
public class StockSummaryController {

    private StockServices stockServices;

    public StockSummaryController(StockServices stockServices) {

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
    public StockSummary getSummary(@PathVariable("STOCK") String stock_in, @PathVariable("DATE") String date_in){

        return stockServices.getSummary(stock_in, date_in);

    }
}
