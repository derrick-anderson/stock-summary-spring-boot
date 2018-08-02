package com.solstice.stocks;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name="stock_quotes")
@NamedNativeQuery(
        query = "SELECT symbol, max(price) as high_price, min(price) as low_price, sum(volume) as volume, "+
                "(SELECT price FROM stock_quotes WHERE symbol = :symbol_in AND day=:day_in and month=:month_in and year=:year_in order by date DESC LIMIT 1) as closing_price , "+
                "(SELECT price FROM stock_quotes WHERE symbol = :symbol_in AND day=:day_in and month=:month_in and year=:year_in order by date ASC LIMIT 1) as open_price " +
                "from stock_quotes WHERE symbol=:symbol_in AND day=:day_in and month=:month_in and year=:year_in group by symbol",
        resultClass = StockSummary.class,
        name="summaryCall"
)
public class StockQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private BigDecimal price;
    private int volume;
    private Date date;
    private String year;
    private String month;
    private String day;

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

    public Date getDate() {
        return date;

    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    //Adds custom date method writer
    public void setDate(Date date) {
        this.date = date;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        this.year = String.valueOf(calendar.get(Calendar.YEAR));
        int thisMonth = calendar.get(Calendar.MONTH)+1;
        if (thisMonth < 10) {
            this.month =  "0" + thisMonth;
        }else{
            this.month = String.valueOf(thisMonth); }
        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (thisDay < 10){
            this.day = "0" + thisDay;
        }else{
            this.day = String.valueOf(thisDay);
        }

    }


}
