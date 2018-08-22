package com.solstice.stocks.data;

import com.solstice.stocks.model.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockQuoteRepository extends JpaRepository<StockQuote, Long> {

    @Query("SELECT s FROM StockQuote s")
    List<StockQuote> getTotalVolumeForDate(String symbol, String dateIn, String dateFormat);

    //todo: Create get high price
    @Query("SELECT s FROM StockQuote s")
    StockQuote getHighPriceForDate(String symbol, String dateIn, String dateFormat);

    //todo: Create get low price
    @Query("SELECT s FROM StockQuote s")
    StockQuote getLowPriceForDate(String symbol, String dateIn, String dateFormat);

    //Stretch Goals
    //todo: Create get open price

    //todo: Create get closing price

}