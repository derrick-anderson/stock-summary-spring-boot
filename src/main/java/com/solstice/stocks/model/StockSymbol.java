package com.solstice.stocks.model;

import org.springframework.stereotype.Component;

@Component
public class StockSymbol {

    private Long id;
    private String symbol;

    public StockSymbol() {
    }

    public StockSymbol(Long id, String symbol) {
        this.symbol = symbol;
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
