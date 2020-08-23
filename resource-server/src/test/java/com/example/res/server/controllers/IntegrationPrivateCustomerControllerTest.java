package com.example.res.server.controllers;

import com.example.res.server.ResourceServerApplication;
import com.example.res.server.controllers.dto.TokenDto;
import com.example.res.server.dto.CustomerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ResourceServerApplication.class)
public class IntegrationPrivateCustomerControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private UUID customerCreatedId;

    private TokenDto tokenDto;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        addCustomer();
        getToken();
    }
    public void getToken() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String auth = "exampleClient" + ":" + "exampleSecret";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.US_ASCII) );
        headers.add("Authorization", "Basic " + new String( encodedAuth ));
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "client_credentials");
        map.add("username", "admin");
        map.add("password", "{noop}adminpass");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<TokenDto> response = restTemplate.postForEntity( url, request , TokenDto.class );
        tokenDto = response.getBody();
    }
    void addCustomer() throws Exception {
        String customerJson = "{\n" +
                "    \"title\" :\"testPrivate\"\n" +
                "}";
        ResultActions resultActions = this.mockMvc.perform(post("/api/v1/customers")
                .content(customerJson)
                .header("Content-Type","application/json")
        )
                .andDo(print())
                .andExpect(status().isOk());

        String jsonAsString = resultActions.andReturn().getResponse().getContentAsString();
        CustomerDto customerDto = objectMapper.readValue(jsonAsString, CustomerDto.class);
        customerCreatedId = customerDto.getId();
    }

    @Test
    public void testPutCustomer() throws Exception {
        String customerJson = "{\n" +
                "    \"title\" :\"test_V2_Public\"\n" +
                "}";
        this.mockMvc.perform(put("/api/v1/customers/" + customerCreatedId)
                .content(customerJson)
                .header("Content-Type","application/json"))
                .andDo(print())
                .andExpect((ResultMatcher) jsonPath("$.title", is("test_V2_Public")))
                .andExpect((ResultMatcher) jsonPath("$.isDeleted", is(false)))
                .andExpect((ResultMatcher) jsonPath("$.createdAt").isNotEmpty())
                .andExpect(status().isOk());
    }
}
