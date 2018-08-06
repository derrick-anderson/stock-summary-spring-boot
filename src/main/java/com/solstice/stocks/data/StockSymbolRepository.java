package com.solstice.stocks.data;

import com.solstice.stocks.model.StockSymbol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockSymbolRepository extends CrudRepository<StockSymbol, Long> {



}