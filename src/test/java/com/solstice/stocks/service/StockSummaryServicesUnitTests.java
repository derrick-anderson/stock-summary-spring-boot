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

    //Mock Objects for Assertions

    private StockQuote highQuote = new StockQuote(
            "AAPL",
            new BigDecimal(1500),
            180000,
            new Date()
            );
    private StockQuote lowQuote = new StockQuote(
            "AAPL",
            new BigDecimal(1200),
            150000,
            new Date()
            );
    private StockQuote openQuote = new StockQuote(
            "AAPL",
            new BigDecimal(100.00),
            100000,
            new Date()
            );
    private StockQuote closeQuote = new StockQuote(
            "AAPL",
            new BigDecimal(85.75),
            130000,
            new Date()
    );
    private List<StockQuote> quoteList = new ArrayList<StockQuote>(){{
        add(highQuote);
        add(lowQuote);
    }};
    StockSummary aSummary = new StockSummary(
            "AAPL",
            new BigDecimal(100.00),
            new BigDecimal(50.00),
            new BigDecimal(150.00),
            new BigDecimal(85.75),
            525600
    );


    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

    }

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

        when(stockSymbolRepository.monthlySummaryQuery(any(String.class), any(int.class),any(int.class))).thenReturn(aSummary);

        StockSummary newSummary = stockServices.getSummary("AAPL", "2018-06");

        assertEquals("AAPL", newSummary.getSymbol());
        assertEquals(new BigDecimal(100.00), newSummary.getOpen_price());
        assertEquals(new BigDecimal(50.00), newSummary.getLow_price());
        assertEquals(new BigDecimal(150.00), newSummary.getHigh_price());
        assertEquals(new BigDecimal(85.75), newSummary.getClosing_price());
        assertEquals(Integer.valueOf(525600), newSummary.getVolume());

    }


    @Test
    public void testGetTotalVolumeForDate(){

        when(stockQuoteRepository.getTotalVolumeForDate(any(), any(), any())).thenReturn(quoteList);

        Integer totalVolume = stockServices.getTotalVolume("AAPL", "2018-06-22");

        assertEquals(Integer.valueOf(330000), totalVolume);


        Integer totalVolume2 = stockServices.getTotalVolume("AAPL", "2018-06");

        assertEquals(Integer.valueOf(330000), totalVolume2);
    }


    @Test
    public void testGetHighPriceForDate(){

        when(stockQuoteRepository.getHighPriceForDate(any(), any(), any())).thenReturn(highQuote);

        BigDecimal highPrice = stockServices.getHighPrice("AAPL", "2018-06-22");

        assertEquals(new BigDecimal(1500), highPrice);

        highPrice = stockServices.getHighPrice("AAPL", "2018-06");

        assertEquals(new BigDecimal(1500), highPrice);
    }


    @Test
    public void testGetLowPriceForDate(){

        when(stockQuoteRepository.getLowPriceForDate(any(), any(), any())).thenReturn(lowQuote);

        BigDecimal lowPrice;

        lowPrice = stockServices.getLowPrice("AAPL", "2018-06-22");

        assertEquals(new BigDecimal(1200), lowPrice);
    }


    @Test
    public void testGetOpenPriceForDate(){

        when(stockQuoteRepository.getOpenPriceForDate(any(), any(), any())).thenReturn(openQuote);

        BigDecimal openPrice = stockServices.getOpenPrice("AAPL", "2018-06-22");

        assertEquals(new BigDecimal(100.00), openPrice);
    }


    @Test
    public void testGetClosePriceForDate(){

        when(stockQuoteRepository.getClosePriceForDate(any(), any(), any())).thenReturn(closeQuote);

        BigDecimal closePrice = stockServices.getClosePrice("AAPL", "2018-06-22");

        assertEquals(new BigDecimal(85.75), closePrice);
    }

}
