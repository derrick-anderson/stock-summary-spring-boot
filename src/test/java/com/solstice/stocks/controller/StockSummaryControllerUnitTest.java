package com.solstice.stocks.controller;

import com.solstice.stocks.model.StockSummary;
import com.solstice.stocks.service.StockServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(StockSummaryController.class)
public class StockSummaryControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockServices stockServices;

    @InjectMocks
    private StockSummaryController controller;

    //MockObjects
    StockSummary mockMonthlySummary = new StockSummary(
            "1",
            new BigDecimal("180.00"),
            new BigDecimal("100.00"),
            new BigDecimal("210.00"),
            new BigDecimal("150.00"),
            18956456
    );

    StockSummary mockDailySummary = new StockSummary(
            "1",
            new BigDecimal("180.00"),
            new BigDecimal("120.00"),
            new BigDecimal("190.00"),
            new BigDecimal("175.00"),
            189564
    );



    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetMonthlySummary() throws Exception{

        when(stockServices.getSummary(anyString(),anyString())).thenReturn(mockMonthlySummary);

        mockMvc.perform(get("/1/2018-06"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol", is("1")))
                .andExpect(jsonPath("$.openPrice", is(180.0)))
                .andExpect(jsonPath("$.lowPrice", is(100.0)))
                .andExpect(jsonPath("$.highPrice", is(210.0)))
                .andExpect(jsonPath("$.closingPrice", is(150.0)))
                .andExpect(jsonPath("$.volume", is(18956456)))
                .andReturn();
    }

    @Test
    public void testGetDailySummary() throws Exception{

        when(stockServices.getSummary(anyString(),anyString())).thenReturn(mockDailySummary);

        mockMvc.perform(get("/1/2018"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol", is("1")))
                .andExpect(jsonPath("$.openPrice", is(180.0)))
                .andExpect(jsonPath("$.lowPrice", is(120.0)))
                .andExpect(jsonPath("$.highPrice", is(190.0)))
                .andExpect(jsonPath("$.closingPrice", is(175.0)))
                .andExpect(jsonPath("$.volume", is(189564)))
                .andReturn();

    }
}
