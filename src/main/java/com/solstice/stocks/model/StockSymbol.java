package com.solstice.stocks.model;

import com.solstice.stocks.model.StockQuote;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@NamedNativeQueries({
        @NamedNativeQuery(

                query = "SELECT symbol, max(q.price) AS high_price, min(q.price) AS low_price, sum(q.volume) AS volume, " +
                        "(SELECT q.price FROM stock_symbols JOIN stock_quotes q ON q.stock_symbol_id = stock_symbol_id WHERE symbol = :symbol_in AND q.day=:day_in AND q.month=:month_in AND q.year=:year_in ORDER BY q.date DESC LIMIT 1) AS closing_price , " +
                        "(SELECT q.price FROM stock_symbols JOIN stock_quotes q ON q.stock_symbol_id = stock_symbol_id WHERE symbol = :symbol_in AND q.day=:day_in AND q.month=:month_in AND q.year=:year_in ORDER BY q.date ASC LIMIT 1) AS open_price " +
                        "from stock_symbols JOIN stock_quotes q ON q.stock_symbol_id = stock_symbol_id WHERE symbol=:symbol_in AND q.day=:day_in AND q.month=:month_in AND q.year=:year_in group by symbol",
                resultClass = StockSummary.class,
                name = "StockSymbol.dailySummaryQuery",
                resultSetMapping = "summaryMapper"

        ),
        @NamedNativeQuery(

                query = "SELECT symbol, max(q.price) AS high_price, min(q.price) AS low_price, sum(q.volume) AS volume, " +
                        "(SELECT q.price FROM stock_symbols JOIN stock_quotes q ON q.stock_symbol_id = stock_symbol_id WHERE symbol = :symbol_in AND q.month=:month_in AND q.year=:year_in ORDER BY q.date DESC LIMIT 1) AS closing_price , " +
                        "(SELECT q.price FROM stock_symbols JOIN stock_quotes q ON q.stock_symbol_id = stock_symbol_id WHERE symbol = :symbol_in AND q.month=:month_in AND q.year=:year_in ORDER BY q.date ASC LIMIT 1) AS open_price " +
                        "from stock_symbols JOIN stock_quotes q ON q.stock_symbol_id = stock_symbol_id WHERE symbol=:symbol_in AND q.month=:month_in AND q.year=:year_in group by symbol",
                resultClass = StockSummary.class,
                name = "StockSymbol.monthlySummaryQuery",
                resultSetMapping = "summaryMapper"

        )
})
@SqlResultSetMapping(

        name="summaryMapper",
        classes = @ConstructorResult(

                targetClass = StockSummary.class,

                columns = {
                        @ColumnResult(name = "symbol"),
                        @ColumnResult(name = "open_price", type = BigDecimal.class),
                        @ColumnResult(name = "low_price", type = BigDecimal.class),
                        @ColumnResult(name = "high_price", type = BigDecimal.class),
                        @ColumnResult(name = "closing_price", type = BigDecimal.class),
                        @ColumnResult(name = "volume", type=Integer.class)
                }
        )
)


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
