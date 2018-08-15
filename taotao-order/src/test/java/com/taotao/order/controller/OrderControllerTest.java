package com.taotao.order.controller;

import com.taotao.common.utils.JsonUtil;
import com.taotao.order.pojo.Order;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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

import java.util.ArrayList;
import java.util.List;

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
    public void createOrder() throws Exception {
        Order order = new Order();
        //构建订单
        //1.加入订单详情
        List<TbOrderItem> orderItemList = new ArrayList<>();
        TbOrderItem orderItem = new TbOrderItem();
        orderItem.setItemId("536563");
        orderItem.setNum(1);
        orderItem.setTitle("标题");
        orderItem.setPrice(9L);
        orderItem.setTotalFee(9L);
        orderItemList.add(orderItem);
        order.setOrderItems(orderItemList);

        //2.加入地址
        TbOrderShipping orderShipping = new TbOrderShipping();
        orderShipping.setReceiverName("小柴");
        orderShipping.setReceiverMobile("10086");
        orderShipping.setReceiverCity("广州");
        order.setOrderShipping(orderShipping);

        //3.添加订单参数
        order.setPayment("1");
        order.setPaymentType(1);
        order.setPostFee("10");

        //模拟mvc测试
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.objectToJson(order)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }
}