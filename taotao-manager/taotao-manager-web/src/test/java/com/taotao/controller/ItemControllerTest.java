package com.taotao.controller;

import com.taotao.common.utils.JsonUtil;
import com.taotao.pojo.TbItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-*.xml" ,"classpath:spring/springmvc.xml"})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback = true)
public class ItemControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getItemById() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/item/536563")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(536563))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void getItemList() throws Exception  {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/item/list")
                .param("page","2")
                .param("rows","10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void createItem() throws Exception  {
        TbItem item = new TbItem();
        item.setCid(2L);
        item.setPrice(1L);
        item.setNum(999);
        item.setTitle("题目啊");

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/item/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.objectToJson(item))
                .param("page","2")
                .param("rows","10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

}