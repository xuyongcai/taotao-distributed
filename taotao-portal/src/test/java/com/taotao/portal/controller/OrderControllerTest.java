package com.taotao.portal.controller;

import com.taotao.common.utils.JsonUtil;
import com.taotao.portal.pojo.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-*.xml" ,"classpath:spring/springmvc.xml"})
@WebAppConfiguration
@Transactional
public class OrderControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void showOrderCart() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/order/order-cart"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void createOrder() throws Exception {
        Order order = new Order();
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.objectToJson(order)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }
}