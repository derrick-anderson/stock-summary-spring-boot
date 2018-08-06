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

}
