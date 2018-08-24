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

        StockSummary dailySummary = stockSummaryController.getSummary("1", "2018-06-22");

        Assert.assertEquals("1", dailySummary.getSymbol());
        Assert.assertEquals(new BigDecimal("188.00"), dailySummary.getOpenPrice());
        Assert.assertEquals(new BigDecimal("189.92"), dailySummary.getHighPrice());
        Assert.assertEquals(new BigDecimal("180.00"), dailySummary.getLowPrice());
        Assert.assertEquals(new BigDecimal("189.44"), dailySummary.getClosingPrice());
        Assert.assertEquals(Integer.valueOf("754610"), dailySummary.getVolume());

        StockSummary monthlySummary = stockSummaryController.getSummary("1", "2018-06");

        Assert.assertEquals("1", monthlySummary.getSymbol());
        Assert.assertEquals(new BigDecimal("188.00"), monthlySummary.getOpenPrice());
        Assert.assertEquals(new BigDecimal("189.99"), monthlySummary.getHighPrice());
        Assert.assertEquals(new BigDecimal("180.00"), monthlySummary.getLowPrice());
        Assert.assertEquals(new BigDecimal("188.82"), monthlySummary.getClosingPrice());
        Assert.assertEquals(Integer.valueOf("2287358"), monthlySummary.getVolume());
    }

}
