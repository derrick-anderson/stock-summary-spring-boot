package com.solstice.stocks.service;


import com.solstice.stocks.data.StockQuoteRepository;
import com.solstice.stocks.data.StockSymbolRepository;
import com.solstice.stocks.model.StockQuote;
import com.solstice.stocks.model.StockSummary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StockSummaryServicesUnitTests {

    @Mock
    private StockSymbolRepository stockSymbolRepository;

    @Mock
    private StockQuoteRepository stockQuoteRepository;

    @InjectMocks
    private StockServices stockServices;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

    }

    //Mock Objects for Assertions
    StockQuote highQuote = new StockQuote(
            "AAPL",
            new BigDecimal(1500),
            180000,
            new Date()
            );



    @Test
    public void testDailySummaryReturn(){

        StockSummary aSummary = new StockSummary();
        aSummary.setSymbol("AAPL");
        aSummary.setOpen_price(new BigDecimal(100.00));
        aSummary.setHigh_price(new BigDecimal(150.00));
        aSummary.setLow_price(new BigDecimal(50.00));
        aSummary.setClosing_price(new BigDecimal(85.75));
        aSummary.setVolume(525600);

        when(stockSymbolRepository.dailySummaryQuery(any(String.class), any(int.class), any(int.class),any(int.class))).thenReturn(aSummary);

        StockSummary newSummary = stockServices.getSummary("AAPL", "2018-06-22");

        assertEquals("AAPL", newSummary.getSymbol());
        assertEquals(new BigDecimal(100.00), newSummary.getOpen_price());
        assertEquals(new BigDecimal(150.00), newSummary.getHigh_price());
        assertEquals(new BigDecimal(50.00), newSummary.getLow_price());
        assertEquals(new BigDecimal(85.75), newSummary.getClosing_price());
        assertEquals(Integer.valueOf(525600), newSummary.getVolume());

    }


    @Test
    public void testMonthlySummaryReturn(){
        StockSummary aSummary = new StockSummary();
        aSummary.setSymbol("AAPL");
        aSummary.setOpen_price(new BigDecimal(100.00));
        aSummary.setHigh_price(new BigDecimal(150.00));
        aSummary.setLow_price(new BigDecimal(50.00));
        aSummary.setClosing_price(new BigDecimal(85.75));
        aSummary.setVolume(525600);

        when(stockSymbolRepository.monthlySummaryQuery(any(String.class), any(int.class),any(int.class))).thenReturn(aSummary);

        StockSummary newSummary = stockServices.getSummary("AAPL", "2018-06");

        assertEquals("AAPL", newSummary.getSymbol());
        assertEquals(new BigDecimal(100.00), newSummary.getOpen_price());
        assertEquals(new BigDecimal(150.00), newSummary.getHigh_price());
        assertEquals(new BigDecimal(50.00), newSummary.getLow_price());
        assertEquals(new BigDecimal(85.75), newSummary.getClosing_price());
        assertEquals(Integer.valueOf(525600), newSummary.getVolume());

    }


    @Test
    public void testGetTotalVolumeForDate(){

        List<StockQuote> quoteList = new ArrayList<StockQuote>();
        StockQuote quote1 = new StockQuote();
        quote1.setSymbol("AAPL");
        quote1.setPrice(new BigDecimal(1500));
        quote1.setVolume(150000);
        quote1.setDate(new Date());
        quoteList.add(quote1);

        StockQuote quote2 = new StockQuote();
        quote2.setSymbol("AAPL");
        quote2.setPrice(new BigDecimal(1200));
        quote2.setVolume(180000);
        quote2.setDate(new Date());
        quoteList.add(quote2);

        when(stockQuoteRepository.getTotalVolumeForDate(any(), any(), any())).thenReturn(quoteList);

        Integer totalVolume = stockServices.getTotalVolume("AAPL", "2018-06-22");

        assertEquals(Integer.valueOf(330000), totalVolume);


        Integer totalVolume2 = stockServices.getTotalVolume("AAPL", "2018-06");

        assertEquals(Integer.valueOf(330000), totalVolume2);
    }


    @Test
    public void testGetHighPriceForDate(){
//        StockQuote aQuote = new StockQuote();
//        aQuote.setSymbol("AAPL");
//        aQuote.setPrice(new BigDecimal(1500));
//        aQuote.setVolume(150000);
//        aQuote.setDate(new Date());

        when(stockQuoteRepository.getHighPriceForDate(any(), any(), any())).thenReturn(highQuote);

        BigDecimal highPrice = stockServices.getHighPrice("AAPL", "2018-06-22");

        assertEquals(new BigDecimal(1500), highPrice);

        highPrice = stockServices.getHighPrice("AAPL", "2018-06");

        assertEquals(new BigDecimal(1500), highPrice);
    }


    @Test
    public void testGetLowPriceForDate(){
        StockQuote aQuote = new StockQuote();
        aQuote.setSymbol("AAPL");
        aQuote.setPrice(new BigDecimal(1200));
        aQuote.setVolume(180000);
        aQuote.setDate(new Date());

        when(stockQuoteRepository.getLowPriceForDate(any(), any(), any())).thenReturn(aQuote);

        BigDecimal lowPrice = new BigDecimal(0);

        lowPrice = stockServices.getLowPrice("AAPL", "2018-06-22");

        assertEquals(new BigDecimal(1200), lowPrice);
    }


    @Test
    public void testGetOpenPriceForDate(){

    }


    //todo: Write Test for get closing price

}
