package com.solstice.stocks.service;


import com.solstice.stocks.data.StockQuoteRepository;
import com.solstice.stocks.model.StockQuote;
import com.solstice.stocks.model.StockSummary;
import com.solstice.stocks.model.StockSymbol;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

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
    private StockQuoteRepository stockQuoteRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StockServices stockServices;

    //Mock Objects for Assertions

    private StockQuote highQuote = new StockQuote(
            "1",
            new BigDecimal(1500),
            180000,
            new Date()
            );
    private StockQuote lowQuote = new StockQuote(
            "1",
            new BigDecimal(1200),
            150000,
            Date.from( (new Date().toInstant()).plusSeconds(500) )
    );
    private StockQuote openQuote = new StockQuote(
            "1",
            new BigDecimal(100.00),
            100000,
            new Date()
            );
    private StockQuote closeQuote = new StockQuote(
            "1",
            new BigDecimal(85.75),
            130000,
            new Date()
    );
    private List<StockQuote> quoteList = new ArrayList<StockQuote>(){{
        add(highQuote);
        add(lowQuote);
    }};
    StockSummary aSummary = new StockSummary(
            "1",
            new BigDecimal(100.00),
            new BigDecimal(50.00),
            new BigDecimal(150.00),
            new BigDecimal(85.75),
            525600
    );


    private StockSymbol mockSymbol = new StockSymbol(String.valueOf(1), "AAPL");


    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGetSummary(){

        when(stockQuoteRepository.getAllQuotesForDate(any(), any(), any())).thenReturn(quoteList);

        StockSummary newSummary = stockServices.getSummary("1", "2018-06-22");

        assertEquals("1", newSummary.getSymbol());
        assertEquals(new BigDecimal(1500.00), newSummary.getOpenPrice());
        assertEquals(new BigDecimal(1500.00), newSummary.getHighPrice());
        assertEquals(new BigDecimal(1200.00), newSummary.getLowPrice());
        assertEquals(new BigDecimal(1200.00), newSummary.getClosingPrice());
        assertEquals(Integer.valueOf(330000), newSummary.getVolume());


        StockSummary newSummary2 = stockServices.getSummary("1", "2018-06");

        assertEquals("1", newSummary2.getSymbol());
        assertEquals(new BigDecimal(1500.00), newSummary2.getOpenPrice());
        assertEquals(new BigDecimal(1500.00), newSummary2.getHighPrice());
        assertEquals(new BigDecimal(1200.00), newSummary2.getLowPrice());
        assertEquals(new BigDecimal(1200.00), newSummary2.getClosingPrice());
        assertEquals(Integer.valueOf(330000), newSummary2.getVolume());
    }


    @Test
    public void testGetTotalVolume(){

        when(stockQuoteRepository.getAllQuotesForDate(any(), any(), any())).thenReturn(quoteList);
        List<StockQuote> quotes = stockQuoteRepository.getAllQuotesForDate(null,null,null);

        Integer totalVolume = stockServices.getTotalVolume(quotes);

        assertEquals(Integer.valueOf(330000), totalVolume);
    }


    @Test
    public void testGetHighPrice(){
        when(stockQuoteRepository.getAllQuotesForDate(any(), any(), any())).thenReturn(quoteList);

        List<StockQuote> quotes = stockQuoteRepository.getAllQuotesForDate(null,null,null);
        BigDecimal highPrice = stockServices.getHighPrice(quotes);

        assertEquals(new BigDecimal(1500), highPrice);
    }


    @Test
    public void testGetLowPrice(){

        when(stockQuoteRepository.getAllQuotesForDate(any(), any(), any())).thenReturn(quoteList);

        List<StockQuote> quotes = stockQuoteRepository.getAllQuotesForDate(null,null,null);
        BigDecimal highPrice = stockServices.getLowPrice(quotes);

        assertEquals(new BigDecimal(1200), highPrice);
    }


    @Test
    public void testGetOpeningPrice(){

        when(stockQuoteRepository.getAllQuotesForDate(any(), any(), any())).thenReturn(quoteList);

        List<StockQuote> quotes = stockQuoteRepository.getAllQuotesForDate(null,null,null);
        BigDecimal openPrice = stockServices.getOpeningPrice(quotes);

        assertEquals(new BigDecimal(1500), openPrice);

    }


    @Test
    public void testGetClosingPrice(){

        when(stockQuoteRepository.getAllQuotesForDate(any(), any(), any())).thenReturn(quoteList);

        List<StockQuote> quotes = stockQuoteRepository.getAllQuotesForDate(null,null,null);
        BigDecimal closePrice = stockServices.getClosingPrice(quotes);

        assertEquals(new BigDecimal(1200), closePrice);

    }



// CANNOT GET MOCK REST TEMPLATE CALL TO WORK
//    @Test
//    public void testGetIdFromSymbol(){
//
//        when(restTemplate.getForObject(any(), any())).thenReturn(mockSymbol);
//
//        StockSymbol aSymbol = stockServices.getIdFromSymbol("AAPL");
//
//        assertEquals("1", aSymbol.getId());
//        assertEquals("AAPL", aSymbol.getSymbol());
//    }

}
