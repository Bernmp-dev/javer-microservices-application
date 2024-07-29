package com.bernmpdev.javerproxyservice.controller;

import com.bernmpdev.javerproxyservice.mock.CustomerMock;
import com.bernmpdev.javerproxyservice.proxy.CustomerProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@DisplayName("[CONTROLLER] - Customer tests")
@WithMockUser(username = "ADMIN", roles = {"ADMIN"})
public class GetAllCustomersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerProxy customerProxy;

    @Autowired
    private ObjectMapper objMapper;

    @Test
    @DisplayName("01 - Get all customers - Success")
    void getAllCustomers_Success() throws Exception {
        var customers = CustomerMock.createCustomerEntityList();
        var responseBody = objMapper.writeValueAsString(customers);

        Mockito.when(customerProxy.getAllCustomers())
                .thenReturn(customers);

        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    @DisplayName("02 - Get all customers - Internal Server Error")
    void getAllCustomers_InternalServerError() throws Exception {
        Mockito.when(customerProxy.getAllCustomers())
                .thenThrow(new FeignException.InternalServerError(
                        null,
                        Request.create(
                                Request.HttpMethod.GET,
                                "/customer",
                                Collections.emptyMap(),
                                null,
                                StandardCharsets.UTF_8
                        ),
                        null, null));

        mockMvc.perform(get("/customer"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("03 - Get all customers - Unauthorized")
    void getAllCustomers_Unauthorized() throws Exception {
        mockMvc.perform(get("/customer")
                        .with(httpBasic("wronguser", "wrongpassword")))
                .andExpect(status().isUnauthorized());
    }
}
