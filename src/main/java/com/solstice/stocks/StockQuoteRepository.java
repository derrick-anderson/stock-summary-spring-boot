package com.solstice.stocks;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.util.List;

@Repository
public interface StockQuoteRepository extends CrudRepository<StockQuote, Long> {

    List<StockQuote> findAllBySymbol(String symbol);

    List<StockQuote> findAllBySymbolAndYearAndMonthAndDay(String symbol, String year, String month, String day);

    List<StockQuote> findAllBySymbolAndYearAndMonth(String symbol, String year, String month);

    @Query(name="summaryCall")
    StockSummary getSummaryByDay(@Param("symbol_in") String symbol_in, @Param("day_in") String day_in,
                                 @Param("month_in") String month_in, @Param("year_in") String year_in);
}
