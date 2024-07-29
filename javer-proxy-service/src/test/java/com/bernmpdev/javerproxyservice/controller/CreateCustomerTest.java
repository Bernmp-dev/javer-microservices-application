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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@DisplayName("[CONTROLLER] - Customer tests")
@WithMockUser(username = "ADMIN", roles = {"ADMIN"})
public class CreateCustomerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerProxy customerProxy;

    @Autowired
    private ObjectMapper objMapper;

    @Test
    @DisplayName("01 - Create customer - Success")
    void createCustomer_Success() throws Exception {
        var customer = CustomerMock.createCustomerEntity();
        var responseBody = objMapper.writeValueAsString(customer);

        Mockito.when(customerProxy.createCustomer(Mockito.any()))
                .thenReturn(customer);

        mockMvc.perform(post("/customer")
                        .contentType("application/json")
                        .content(responseBody)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    @DisplayName("02 - Create customer - Conflict")
    void createCustomer_Conflict() throws Exception {
        var customer = CustomerMock.createCustomerEntity();
        var requestBody = objMapper.writeValueAsString(customer);

        Mockito.when(customerProxy.createCustomer(Mockito.any()))
                .thenThrow(new FeignException.Conflict(
                        null,
                        Request.create(
                                Request.HttpMethod.POST,
                                "/customer",
                                Collections.emptyMap(),
                                null,
                                StandardCharsets.UTF_8
                        ),
                        null, null));

        mockMvc.perform(post("/customer")
                        .contentType("application/json")
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("03 - Create customer - Validation Error")
    void createCustomer_ValidationError() throws Exception {
        var invalidCustomer = CustomerMock.createCustomerEntityWithoutNome();
        var requestBody = objMapper.writeValueAsString(invalidCustomer);

        Mockito.when(customerProxy.createCustomer(Mockito.any()))
                .thenThrow(new FeignException.BadRequest(
                        null,
                        Request.create(
                                Request.HttpMethod.POST,
                                "/customer",
                                Collections.emptyMap(),
                                null,
                                StandardCharsets.UTF_8
                        ),
                        null, null));

        mockMvc.perform(post("/customer")
                        .contentType("application/json")
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("04 - Create customer - Unauthorized")
    void createCustomer_Unauthorized() throws Exception {
        var customer = CustomerMock.createCustomerEntity();
        var requestBody = objMapper.writeValueAsString(customer);

        mockMvc.perform(post("/customer")
                        .with(httpBasic("wronguser", "wrongpassword"))
                        .contentType("application/json")
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }
}
