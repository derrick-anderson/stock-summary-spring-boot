package com.solstice.stocks.data;

import com.solstice.stocks.model.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockQuoteRepository extends JpaRepository<StockQuote, Long> {

    //todo: Implement Query
    @Query("SELECT s FROM StockQuote s JOIN s.stockSymbol t WHERE t.symbol= :symbolIn AND FUNCTION('DATE_FORMAT', s.date, :dateFormat) = :dateIn")
    List<StockQuote> getTotalVolumeForDate(@Param("symbolIn") String symbol,
                                           @Param("dateIn") String dateIn,
                                           @Param("dateFormat") String dateFormat);

    //todo: Implement Query
    @Query("SELECT s FROM StockQuote s")
    StockQuote getHighPriceForDate(String symbol, String dateIn, String dateFormat);

    //todo: Implement Query
    @Query("SELECT s FROM StockQuote s")
    StockQuote getLowPriceForDate(String symbol, String dateIn, String dateFormat);

    //Stretch Goals
    //todo: Implement Query
    @Query("SELECT s FROM StockQuote s")
    StockQuote getOpenPriceForDate(String symbol, String dateIn, String dateFormat);

    //todo: Implement Query
    @Query("SELECT s FROM StockQuote s")
    StockQuote getClosePriceForDate(String symbol, String dateIn, String dateFormat);

}