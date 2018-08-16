package com.taotao.portal.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-*.xml" ,"classpath:spring/springmvc.xml"})
@WebAppConfiguration
@Transactional
public class CartControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void addCartItem() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/add/536563"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void showSuccess() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/success"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void showCart() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/delete/536563"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void deleteCartItem() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/cart/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }
}