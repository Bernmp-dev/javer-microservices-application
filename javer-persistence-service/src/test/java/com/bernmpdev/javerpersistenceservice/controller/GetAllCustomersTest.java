package com.bernmpdev.javerpersistenceservice.controller;

import com.bernmpdev.javerpersistenceservice.mock.CustomerMock;
import com.bernmpdev.javerpersistenceservice.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
@DisplayName("[CONTROLLER] - Get all customers tests")
@WithMockUser(username = "ADMIN", roles = {"ADMIN"})
public class GetAllCustomersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("01 - Get all customers - Success")
    void getAllCustomers_Success() throws Exception {
        var customerDtoList = CustomerMock.CustomerDtoList();

        Mockito.when(customerService.getAllCustomers()).thenReturn(customerDtoList);

        mockMvc.perform(get("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(customerDtoList.size()))
                .andExpect(jsonPath("$[0].cpf").value(customerDtoList.get(0).cpf()))
                .andExpect(jsonPath("$[1].cpf").value(customerDtoList.get(1).cpf()));
    }

    @Test
    @DisplayName("02 - Get all customers - Empty List")
    void getAllCustomers_EmptyList() throws Exception {
        Mockito.when(customerService.getAllCustomers()).thenReturn(List.of());

        mockMvc.perform(get("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("03 - Get all customers - Exception Handling")
    void getAllCustomers_ExceptionHandling() throws Exception {
        Mockito.when(customerService.getAllCustomers()).thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error"));
    }
}
