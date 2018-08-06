package com.solstice.stocks;

import javax.persistence.*;

@Entity
@Table(name = "stock_symbols")
public class StockSymbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticker")
    private String symbol;

    @Column(name = "company_name")
    private String name;

    //todo: Update this to use a Text Column IF NECESSARY!
    @Column(name = "company_description")
    private String desc;

    @ManyToOne()
    @JoinColumn(name = "stock_quotes_id")
    private StockQuote stockQuote;

    public StockSymbol() {
    }

    public StockSymbol(String symbol, String name, String desc) {
        this.symbol = symbol;
        this.name = name;
        this.desc = desc;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
