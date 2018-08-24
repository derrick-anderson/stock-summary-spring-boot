package com.solstice.stocks.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stock_symbols")
public class StockSymbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    @OneToMany()
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "stock_symbol_id")
    private List<StockQuote> stockQuotes;

    public StockSymbol() {
    }

    public StockSymbol(String symbol, List<StockQuote> stockQuotes) {
        this.symbol = symbol;
        this.stockQuotes = stockQuotes;
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

    public List<StockQuote> getStockQuotes() {
        return stockQuotes;
    }

    public void setStockQuotes(List<StockQuote> stockQuotes) {
        this.stockQuotes = stockQuotes;
    }
}
