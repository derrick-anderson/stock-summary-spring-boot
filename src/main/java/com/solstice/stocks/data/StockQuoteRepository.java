package com.solstice.stocks.data;

import com.solstice.stocks.model.StockQuote;
import com.solstice.stocks.model.StockSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockQuoteRepository extends CrudRepository<StockQuote, Long> {


    List<StockQuote> findAllBySymbol(String symbol);

    @Query(nativeQuery = true)
    StockSummary dailySummaryQuery(@Param("symbol_in") String symbol_in, @Param("year_in") int year_in,
                                   @Param("month_in") int month_in, @Param("day_in") int day_in);

    @Query(nativeQuery = true)
    StockSummary monthlySummaryQuery(@Param("symbol_in") String symbol_in,
                                    @Param("year_in") int year_in, @Param("month_in") int month_in);

}
