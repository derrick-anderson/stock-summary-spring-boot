package com.solstice.stocks.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name="stock_quotes")
public class StockQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private BigDecimal price;
    private int volume;

    //@JsonFormat( shape= JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY hh:mm:ss")
    private Date date;
//    private int year;
//    private int month;
//    private int day;

//    @ManyToOne
//    @JoinColumn(name = "stock_symbol_id")
//    private StockSymbol stockSymbol;

    public StockQuote() {
    }

    public StockQuote(String symbol, BigDecimal price, int volume, Date date) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
        this.date = date;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Date getDate() {  return date;  }

//    public int getYear() {
//        return year;
//    }
//
//    public void setYear(int year) {
//        this.year = year;
//    }
//
//    public int getMonth() {
//        return month;
//    }
//
//    public void setMonth(int month) {
//        this.month = month;
//    }
//
//    public int getDay() {
//        return day;
//    }
//
//    public void setDay(int day) {
//        this.day = day;
//    }

    //Adds custom date method writer

    public void setDate(Date date) {

        this.date = date;

//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(date);
//
//        this.year = calendar.get(Calendar.YEAR);
//
//        this.month = calendar.get(Calendar.MONTH)+1;
//
//        this.day = calendar.get(Calendar.DAY_OF_MONTH);

    }
//
//    public StockSymbol getStockSymbol() {
//        return stockSymbol;
//    }
//
//    public void setStockSymbol(StockSymbol stockSymbol) {
//        this.stockSymbol = stockSymbol;
//    }
}


