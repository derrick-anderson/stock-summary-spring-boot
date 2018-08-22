package com.solstice.stocks.data;

import com.solstice.stocks.model.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockQuoteRepository extends JpaRepository<StockQuote, Long> {

    //todo: Implement Query
    @Query("SELECT s FROM StockQuote s")
    List<StockQuote> getTotalVolumeForDate(String symbol, String dateIn, String dateFormat);

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

    //todo: Create get closing price

}