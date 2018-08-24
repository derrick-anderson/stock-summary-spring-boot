package com.solstice.stocks.model;

import java.math.BigDecimal;

public class StockSummary {

    private String symbol;
    private BigDecimal openPrice;
    private BigDecimal lowPrice;
    private BigDecimal highPrice;
    private BigDecimal closingPrice;
    private Integer volume;


    public StockSummary() {
    }

    public StockSummary(String symbol, BigDecimal openPrice, BigDecimal lowPrice, BigDecimal highPrice, BigDecimal closingPrice, Integer volume) {

        this.symbol = symbol;
        this.openPrice = openPrice;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        this.closingPrice = closingPrice;
        this.volume = volume;

    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

}