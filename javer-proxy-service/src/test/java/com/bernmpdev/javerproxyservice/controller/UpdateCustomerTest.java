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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@DisplayName("[CONTROLLER] - Customer tests")
@WithMockUser(username = "ADMIN", roles = {"ADMIN"})
public class UpdateCustomerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerProxy customerProxy;

    @Autowired
    private ObjectMapper objMapper;

    @Test
    @DisplayName("01 - Update customer - Success")
    void updateCustomer_Success() throws Exception {
        var customer = CustomerMock.createCustomerEntity();
        var responseBody = objMapper.writeValueAsString(customer);

        Mockito.when(customerProxy.updateCustomer(Mockito.anyLong(), Mockito.any()))
                .thenReturn(customer);

        mockMvc.perform(put("/customer/1")
                        .contentType("application/json")
                        .content(responseBody)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    @DisplayName("02 - Update customer - Not Found")
    void updateCustomer_NotFound() throws Exception {
        var customer = CustomerMock.createCustomerEntity();
        var requestBody = objMapper.writeValueAsString(customer);

        Mockito.when(customerProxy.updateCustomer(Mockito.anyLong(), Mockito.any()))
                .thenThrow(new FeignException.NotFound(
                        null,
                        Request.create(
                                Request.HttpMethod.PUT,
                                "/customer/1",
                                Collections.emptyMap(),
                                null,
                                StandardCharsets.UTF_8
                        ),
                        null, null));

        mockMvc.perform(put("/customer/1")
                        .contentType("application/json")
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("03 - Update customer - Validation Error")
    void updateCustomer_ValidationError() throws Exception {
        var invalidCustomer = CustomerMock.createCustomerEntityWithoutNome();
        var requestBody = objMapper.writeValueAsString(invalidCustomer);

        Mockito.when(customerProxy.updateCustomer(Mockito.anyLong(), Mockito.any()))
                .thenThrow(new FeignException.BadRequest(
                        null,
                        Request.create(
                                Request.HttpMethod.PUT,
                                "/customer/1",
                                Collections.emptyMap(),
                                null,
                                StandardCharsets.UTF_8
                        ),
                        null, null));

        mockMvc.perform(put("/customer/1")
                        .contentType("application/json")
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("04 - Update customer - Unauthorized")
    void updateCustomer_Unauthorized() throws Exception {
        var customer = CustomerMock.createCustomerEntity();
        var requestBody = objMapper.writeValueAsString(customer);

        mockMvc.perform(put("/customer/1")
                        .with(httpBasic("wronguser", "wrongpassword"))
                        .contentType("application/json")
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }
}
