package com.solstice.stocks.data;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.solstice.stocks.model.StockQuote;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({
        DirtiesContextTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("classpath:test-dataset.xml")
public class StockQuoteRepositoryIntegrationTest {

    @Autowired
    private StockQuoteRepository stockQuoteRepository;

    @Test
    public void testGetAllQuotesForDateDBQuery(){
        List<StockQuote> quotes = stockQuoteRepository.getAllQuotesForDate("AAPL", "2018-06-22", "%Y-%m-%d");
        Integer totalVolume = quotes.stream()
                .collect(Collectors.summingInt(StockQuote::getVolume));
        Assert.assertEquals(Integer.valueOf(560000), totalVolume );
    }

    @Test
    public void testGetHighPriceDBQuery(){
        StockQuote highQuote = stockQuoteRepository.getHighPriceForDate("AAPL", "2018-06-22", "%Y-%m-%d");
        BigDecimal highPrice = highQuote.getPrice();

        Assert.assertEquals(new BigDecimal(180000), highPrice);    }
}
