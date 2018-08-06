package com.solstice.stocks;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name="stock_quotes")
@NamedNativeQueries({
        @NamedNativeQuery(

                query = "SELECT symbol, max(price) AS high_price, min(price) AS low_price, sum(volume) AS volume, " +
                        "(SELECT price FROM stock_quotes WHERE symbol = :symbol_in AND day=:day_in AND month=:month_in AND year=:year_in ORDER BY date DESC LIMIT 1) AS closing_price , " +
                        "(SELECT price FROM stock_quotes WHERE symbol = :symbol_in AND day=:day_in AND month=:month_in AND year=:year_in ORDER BY date ASC LIMIT 1) AS open_price " +
                        "from stock_quotes WHERE symbol=:symbol_in AND day=:day_in AND month=:month_in AND year=:year_in group by symbol",
                resultClass = StockSummary.class,
                name = "StockQuote.dailySummaryQuery",
                resultSetMapping = "summaryMapper"

        ),
        @NamedNativeQuery(

                query = "SELECT symbol, max(price) AS high_price, min(price) AS low_price, sum(volume) AS volume, " +
                        "(SELECT price FROM stock_quotes WHERE symbol = :symbol_in  AND month=:month_in AND year=:year_in ORDER BY date DESC LIMIT 1) AS closing_price , " +
                        "(SELECT price FROM stock_quotes WHERE symbol = :symbol_in  AND month=:month_in AND year=:year_in ORDER BY date ASC LIMIT 1) AS open_price " +
                        "from stock_quotes WHERE symbol=:symbol_in AND month=:month_in AND year=:year_in group by symbol",
                resultClass = StockSummary.class,
                name = "StockQuote.monthlySummaryQuery",
                resultSetMapping = "summaryMapper"

        )
})
@SqlResultSetMapping(

    name="summaryMapper",
    classes = @ConstructorResult(

        targetClass = StockSummary.class,

        columns = {

            @ColumnResult(name="symbol"),
            @ColumnResult(name = "open_price", type = BigDecimal.class),
            @ColumnResult(name = "low_price", type = BigDecimal.class),
            @ColumnResult(name = "high_price", type = BigDecimal.class),
            @ColumnResult(name = "closing_price", type = BigDecimal.class),
            @ColumnResult(name = "volume", type=Integer.class)
        }
    )
)
public class StockQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private BigDecimal price;
    private int volume;
    private Date date;
    private int year;
    private int month;
    private int day;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    //Adds custom date method writer

    public void setDate(Date date) {

        this.date = date;

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        this.year = calendar.get(Calendar.YEAR);

        this.month = calendar.get(Calendar.MONTH)+1;

        this.day = calendar.get(Calendar.DAY_OF_MONTH);

    }


}
