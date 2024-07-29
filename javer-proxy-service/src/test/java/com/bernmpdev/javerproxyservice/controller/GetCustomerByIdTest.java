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
public class GetCustomerByIdTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerProxy customerProxy;

    @Autowired
    private ObjectMapper objMapper;

    @Test
    @DisplayName("01 - Get customer by ID - Success")
    void getCustomerById_Success() throws Exception {
        var customer = CustomerMock.createCustomerEntity();
        var responseBody = objMapper.writeValueAsString(customer);

        Mockito.when(customerProxy.getCustomerById(1L))
                .thenReturn(customer);

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    @DisplayName("02 - Get customer by ID - Not Found")
    void getCustomerById_NotFound() throws Exception {
        Mockito.when(customerProxy.getCustomerById(1L))
                .thenThrow(new FeignException.NotFound(
                        null,
                        Request.create(
                                Request.HttpMethod.GET,
                                "/customer/1",
                                Collections.emptyMap(),
                                null,
                                StandardCharsets.UTF_8
                        ),
                        null, null));

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("03 - Get customer by ID - Internal Server Error")
    void getCustomerById_InternalServerError() throws Exception {
        Mockito.when(customerProxy.getCustomerById(1L))
                .thenThrow(new FeignException.InternalServerError(
                        null,
                        Request.create(
                                Request.HttpMethod.GET,
                                "/customer/1",
                                Collections.emptyMap(),
                                null,
                                StandardCharsets.UTF_8
                        ),
                        null, null));

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("04 - Get customer by ID - Unauthorized")
    void getCustomerById_Unauthorized() throws Exception {
        mockMvc.perform(get("/customer/1")
                        .with(httpBasic("wronguser", "wrongpassword")))
                .andExpect(status().isUnauthorized());
    }
}
