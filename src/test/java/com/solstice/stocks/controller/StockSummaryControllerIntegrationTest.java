package com.solstice.stocks.controller;

import com.solstice.stocks.model.StockSummary;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockSummaryControllerIntegrationTest {

    @Autowired
    StockSummaryController stockSummaryController;

    @Before
    public void setup(){
        stockSummaryController.loadData();
    }

    @Test
    public void testGetSummaryForDay(){

        StockSummary summary = stockSummaryController.getSummary("AAPL", "2018-06-22");

        Assert.assertEquals("AAPL", summary.getSymbol());
        Assert.assertEquals(new BigDecimal("184.12"), summary.getOpen_price());
        Assert.assertEquals(new BigDecimal("189.99"), summary.getHigh_price());
        Assert.assertEquals(new BigDecimal("180.00"), summary.getLow_price());
        Assert.assertEquals(new BigDecimal("185.70"), summary.getClosing_price());
        Assert.assertEquals(Integer.valueOf("1426150"), summary.getVolume());

    }

    @Test
    public void testGetSummaryForMonth(){

        StockSummary summary = stockSummaryController.getSummary("AAPL", "2018-06");

        Assert.assertEquals("AAPL", summary.getSymbol());
        Assert.assertEquals(new BigDecimal("184.12"), summary.getOpen_price());
        Assert.assertEquals(new BigDecimal("189.99"), summary.getHigh_price());
        Assert.assertEquals(new BigDecimal("180.00"), summary.getLow_price());
        Assert.assertEquals(new BigDecimal("180.68"), summary.getClosing_price());
        Assert.assertEquals(Integer.valueOf("2154581"), summary.getVolume());

    }
}
