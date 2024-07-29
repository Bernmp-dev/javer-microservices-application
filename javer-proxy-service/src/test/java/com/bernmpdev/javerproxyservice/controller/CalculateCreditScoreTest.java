package com.bernmpdev.javerproxyservice.controller;

import com.bernmpdev.javerproxyservice.proxy.CustomerProxy;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
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
@DisplayName("[CONTROLLER] - Calculate Credit Score tests")
@WithMockUser(username = "ADMIN", roles = {"ADMIN"})
public class CalculateCreditScoreTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerProxy customerProxy;

    @Test
    @DisplayName("01 - Calculate credit score - Success")
    void calculateCreditScore_Success() throws Exception {
        Mockito.when(customerProxy.calculateCreditScore(Mockito.anyLong()))
                .thenReturn(100.0F);

        mockMvc.perform(get("/customer/1/calculateCreditScore"))
                .andExpect(status().isOk())
                .andExpect(content().string("100.0"));
    }

    @Test
    @DisplayName("02 - Calculate credit score - Not Found")
    void calculateCreditScore_NotFound() throws Exception {
        Request request = Request.create(Request.HttpMethod.GET, "/customer/1/calculateCreditScore", Collections.emptyMap(), null, StandardCharsets.UTF_8, new RequestTemplate());

        Mockito.when(customerProxy.calculateCreditScore(Mockito.anyLong()))
                .thenThrow(new FeignException.NotFound(null, request, null, null));

        mockMvc.perform(get("/customer/1/calculateCreditScore"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("03 - Calculate credit score - Unauthorized")
    void calculateCreditScore_Unauthorized() throws Exception {
        mockMvc.perform(get("/customer/1/calculateCreditScore")
                        .with(httpBasic("wronguser", "wrongpassword")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("04 - Calculate credit score - Internal Server Error")
    void calculateCreditScore_InternalServerError() throws Exception {
        Request request = Request.create(Request.HttpMethod.GET, "/customer/1/calculateCreditScore", Collections.emptyMap(), null, StandardCharsets.UTF_8, new RequestTemplate());

        Mockito.when(customerProxy.calculateCreditScore(Mockito.anyLong()))
                .thenThrow(new FeignException.InternalServerError(null, request, null, null));

        mockMvc.perform(get("/customer/1/calculateCreditScore"))
                .andExpect(status().isInternalServerError());
    }
}
