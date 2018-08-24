package com.solstice.stocks.data;

import com.solstice.stocks.model.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockQuoteRepository extends JpaRepository<StockQuote, Long> {

    @Query("SELECT s FROM StockQuote s WHERE s.symbol= :symbolIn " +
            "AND FUNCTION('DATE_FORMAT', s.date, :dateFormat) = :dateIn")
    List<StockQuote> getAllQuotesForDate(@Param("symbolIn") String symbol,
                                         @Param("dateIn") String dateIn,
                                         @Param("dateFormat") String dateFormat);

}