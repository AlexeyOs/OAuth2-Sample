package com.example.res.server.controllers;

import com.example.res.server.ResourceServerApplication;

import com.example.res.server.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ResourceServerApplication.class)
public class PublicResourceCustomerControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private String customerCreatedId;


    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        addCustomer();
    }

    void addCustomer() throws Exception {
        String customerJson = "{\n" +
                "    \"title\" :\"test1\",\n" +
                "    \"isDeleted\" : false,\n" +
                "    \"createdAt\":\"2020-08-16 09:45:12.000\"\n" +
                "}";
        ResultActions resultActions = this.mockMvc.perform(post("/api/v1/customers")
                .content(customerJson)
                .header("Content-Type","application/json")
        )
                .andDo(print())
                .andExpect(status().isCreated());

        String urlForGetCustomer = resultActions.andReturn().getResponse().getHeader("Location");
        assertNotNull(urlForGetCustomer);
        String customerId = urlForGetCustomer.substring(urlForGetCustomer.lastIndexOf("/") + 1);
        assertNotNull(customerId);
        customerCreatedId = customerId;
    }

    @Test
    public void getCustomer() throws Exception {
        this.mockMvc.perform(get("/api/v1/customers/" + customerCreatedId))
                .andDo(print())
                .andExpect((ResultMatcher) jsonPath("$.title", is("test1")))
                .andExpect((ResultMatcher) jsonPath("$.isDeleted", is(false)))
                .andExpect((ResultMatcher) jsonPath("$.createdAt", is("2020-08-16 09:45:12.000")))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCustomers() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/api/v1/customers"))
                 .andDo(print())
                 .andExpect(status().isOk());

        String jsonAsString = resultActions.andReturn().getResponse().getContentAsString();
        List<Customer> customers = objectMapper.readValue(jsonAsString, objectMapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
        assertTrue(customers.size() > 0);
    }
}