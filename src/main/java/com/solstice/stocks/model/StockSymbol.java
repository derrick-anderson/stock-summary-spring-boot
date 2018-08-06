package com.solstice.stocks.model;

import com.solstice.stocks.model.StockQuote;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "stock_symbols")
public class StockSymbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticker")
    private String symbol;

    @OneToMany()
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "stock_symbol_id")
    private List<StockQuote> stockQuotes;

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

}
