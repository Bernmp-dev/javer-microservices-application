package com.bernmpdev.javerpersistenceservice.controller;

import com.bernmpdev.javerpersistenceservice.exception.CustomerNotFoundException;
import com.bernmpdev.javerpersistenceservice.mock.CustomerMock;
import com.bernmpdev.javerpersistenceservice.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
@DisplayName("[CONTROLLER] - Calculate credit score tests")
@WithMockUser(username = "ADMIN", roles = {"ADMIN"})
public class calculateCreditScoreTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("01 - Calculate credit score - Success")
    void calculateCreditScoreSuccess() throws Exception {
        var customer = CustomerMock.createCustomerEntity();

        Mockito.when(customerService.getCreditScore(Mockito.anyLong()))
                .thenReturn(customer.getScoreCredito());

        mockMvc.perform(get("/customer/1/calculateCreditScore")
                        .header("X-Origin-Header", "javer-proxy-service"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(customer.getScoreCredito())));
    }

    @Test
    @DisplayName("02 - Calculate credit score - Customer Not Found")
    void calculateCreditScore_CustomerNotFound() throws Exception {
        Mockito.when(customerService.getCreditScore(Mockito.anyLong()))
                .thenThrow(new CustomerNotFoundException());

        mockMvc.perform(get("/customer/1/calculateCreditScore")
                        .header("X-Origin-Header", "javer-proxy-service"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));
    }

    @Test
    @DisplayName("03 - Calculate credit score - Internal Server Error")
    void calculateCreditScore_InternalServerError() throws Exception {
        Mockito.when(customerService.getCreditScore(Mockito.anyLong()))
                .thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/customer/1/calculateCreditScore")
                        .header("X-Origin-Header", "javer-proxy-service"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error"));
    }

    @Test
    @DisplayName("04 - Calculate credit score - Invalid ID Format")
    void calculateCreditScore_InvalidIdFormat() throws Exception {
        mockMvc.perform(get("/customer/invalid_id/calculateCreditScore")
                        .header("X-Origin-Header", "javer-proxy-service"))
                .andExpect(status().isInternalServerError());
    }
}
