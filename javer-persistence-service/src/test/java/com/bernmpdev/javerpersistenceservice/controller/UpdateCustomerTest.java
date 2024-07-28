package com.bernmpdev.javerpersistenceservice.controller;

import com.bernmpdev.javerpersistenceservice.exception.CustomerNotFoundException;
import com.bernmpdev.javerpersistenceservice.mock.CustomerMock;
import com.bernmpdev.javerpersistenceservice.model.dto.CustomerDto;
import com.bernmpdev.javerpersistenceservice.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
@DisplayName("[CONTROLLER] - Update customer tests")
@WithMockUser(username = "ADMIN", roles = {"ADMIN"})
public class UpdateCustomerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Test
    @DisplayName("01 - Update customer - Success")
    void updateCustomer_Success() throws Exception {
        CustomerDto customerDto = CustomerMock.createCustomerDto();
        CustomerDto updatedCustomerDto = new CustomerDto(
                1L,
                "JoãoAtualizado",
                "12345678901",
                11987654321L,
                true,
                0F,
                2000.0F
        );

        Mockito.when(customerService.updateCustomer(Mockito.anyLong(), Mockito.any(CustomerDto.class)))
                .thenReturn(updatedCustomerDto);

        mockMvc.perform(put("/customer/1")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(customerDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(updatedCustomerDto.nome()))
                .andExpect(jsonPath("$.saldoCc").value(updatedCustomerDto.saldoCc()));
    }

    @Test
    @DisplayName("02 - Update customer - Not Found")
    void updateCustomer_NotFound() throws Exception {
        CustomerDto customerDto = CustomerMock.createCustomerDto();

        Mockito.when(customerService.updateCustomer(Mockito.anyLong(), Mockito.any(CustomerDto.class)))
                .thenThrow(new CustomerNotFoundException());

        mockMvc.perform(put("/customer/1")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(customerDto))
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));
    }

    @Test
    @DisplayName("03 - Update customer - Validation Error")
    void updateCustomer_ValidationError() throws Exception {
        CustomerDto invalidCustomerDto = new CustomerDto(
                1L,
                "J",
                "123",
                119L,
                true,
                -1F,
                -1000.0F
        );

        mockMvc.perform(put("/customer/1")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("Nome deve ter entre 2 e 150 carácteres"))
                .andExpect(jsonPath("$.cpf").value("CPF deve ter 11 dígitos"))
                .andExpect(jsonPath("$.telefone").value("Telefone deve ser um número válido"))
                .andExpect(jsonPath("$.scoreCredito").value("Score de crédito deve ser maior ou igual a zero"))
                .andExpect(jsonPath("$.saldoCc").value("Saldo da conta corrente deve ser maior ou igual a zero"));
    }
}
