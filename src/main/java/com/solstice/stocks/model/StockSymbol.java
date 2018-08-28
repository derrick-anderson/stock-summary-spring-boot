package com.solstice.stocks.model;

import org.springframework.stereotype.Component;

@Component
public class StockSymbol {

    private String id;
    private String symbol;

    public StockSymbol() {
    }

    public StockSymbol(String id, String symbol) {
        this.symbol = symbol;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
