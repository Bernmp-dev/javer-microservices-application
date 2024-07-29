package com.bernmpdev.javerproxyservice.controller;

import com.bernmpdev.javerproxyservice.proxy.CustomerProxy;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@DisplayName("[CONTROLLER] - Customer tests")
@WithMockUser(username = "ADMIN", roles = {"ADMIN"})
public class DeleteCustomerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerProxy customerProxy;

    @Test
    @DisplayName("01 - Delete customer - Success")
    void deleteCustomer_Success() throws Exception {
        Mockito.doNothing().when(customerProxy).deleteCustomer(Mockito.anyLong());

        mockMvc.perform(delete("/customer/1")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("02 - Delete customer - Not Found")
    void deleteCustomer_NotFound() throws Exception {
        Mockito.doThrow(new FeignException.NotFound(
                null,
                Request.create(
                        Request.HttpMethod.DELETE,
                        "/customer/1",
                        Collections.emptyMap(),
                        null,
                        StandardCharsets.UTF_8,
                        null
                ),
                null, null)).when(customerProxy).deleteCustomer(Mockito.anyLong());

        mockMvc.perform(delete("/customer/1")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("03 - Delete customer - Unauthorized")
    void deleteCustomer_Unauthorized() throws Exception {
        mockMvc.perform(delete("/customer/1")
                        .with(httpBasic("wronguser", "wrongpassword"))
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("04 - Delete customer - Internal Server Error")
    void deleteCustomer_InternalServerError() throws Exception {
        Mockito.doThrow(new FeignException.InternalServerError(
                null,
                Request.create(
                        Request.HttpMethod.DELETE,
                        "/customer/1",
                        Collections.emptyMap(),
                        null,
                        StandardCharsets.UTF_8,
                        null
                ),
                null, null)).when(customerProxy).deleteCustomer(Mockito.anyLong());

        mockMvc.perform(delete("/customer/1")
                        .with(csrf()))
                .andExpect(status().isInternalServerError());
    }
}
