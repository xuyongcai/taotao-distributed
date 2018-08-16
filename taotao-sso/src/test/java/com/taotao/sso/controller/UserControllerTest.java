package com.taotao.sso.controller;

import com.taotao.common.utils.JsonUtil;
import com.taotao.pojo.TbUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
//@Transactional
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void checkData() throws Exception {
        String username = "zhangsan";
        String phone = "13488888888";
        String email = "aa@a";

        String result1 = mockMvc.perform(MockMvcRequestBuilders.get("/user/check/"+ username +"/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        String result2 = mockMvc.perform(MockMvcRequestBuilders.get("/user/check/"+ phone +"/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        String result3 = mockMvc.perform(MockMvcRequestBuilders.get("/user/check/"+ email +"/3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    @Test
    public void createUser() throws Exception {
        TbUser user = new TbUser();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("123@163.com");
        user.setPhone("10086");

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.objectToJson(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void userLogin() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username","admin")
                .param("password","admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void getUserByToken() throws Exception {
        //此token为临时的，需要先登陆，再从控制台那复制返回的token
        String token = "0150d96c-8c6b-48af-a8e8-8c1584870049";

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/token/" + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }
}