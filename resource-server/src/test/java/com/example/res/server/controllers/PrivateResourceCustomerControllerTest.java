package com.example.res.server.controllers;

import com.example.res.server.ResourceServerApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ResourceServerApplication.class)
public class PrivateResourceCustomerControllerTest {
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
    public void getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/oauth/token";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class, 1);
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
}
