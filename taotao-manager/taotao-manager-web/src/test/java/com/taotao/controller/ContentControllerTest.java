package com.taotao.controller;

import com.taotao.common.utils.JsonUtil;
import com.taotao.pojo.TbContent;
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
public class ContentControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void insertContent() throws Exception {
        TbContent content = new TbContent();
        content.setCategoryId(2L);
        content.setContent("内容内容啊");
        content.setTitle("title");
        content.setSubTitle("subtitle");
        content.setTitleDesc("标题详情呢");

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/content/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.objectToJson(content))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

    }
}