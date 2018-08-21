package com.solstice.stocks.data;

import com.solstice.stocks.model.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockQuoteRepository extends JpaRepository<StockQuote, Long> {

    //todo: Create find all by stock id, day, year, month

    //todo: Create find all by stock id, year, month
}
