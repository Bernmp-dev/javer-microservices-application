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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
@DisplayName("[CONTROLLER] - Get customer by ID tests")
@WithMockUser(username = "ADMIN", roles = {"ADMIN"})
public class GetCustomerByIdTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("01 - Get customer by ID - Success")
    void getCustomerById_Success() throws Exception {
        var customerDto = CustomerMock.createCustomerDto();

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customerDto);

        mockMvc.perform(get("/customer/1")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cpf").value(customerDto.cpf()))
                .andExpect(jsonPath("$.nome").value(customerDto.nome()));
    }

    @Test
    @DisplayName("02 - Get customer by ID - Not Found")
    void getCustomerById_NotFound() throws Exception {
        Mockito.when(customerService.getCustomerById(1L)).thenThrow(new CustomerNotFoundException());

        mockMvc.perform(get("/customer/1")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));
    }

    @Test
    @DisplayName("03 - Get customer by ID - Internal Server Error")
    void getCustomerById_InternalServerError() throws Exception {
        Mockito.when(customerService.getCustomerById(1L)).thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/customer/1")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error"));
    }
}
