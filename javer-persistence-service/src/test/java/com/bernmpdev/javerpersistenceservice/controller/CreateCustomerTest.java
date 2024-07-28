package com.bernmpdev.javerpersistenceservice.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
@DisplayName("[CONTROLLER] - Create customer tests")
@WithMockUser(username = "ADMIN", roles = {"ADMIN"})
public class CreateCustomerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Test
    @DisplayName("00 - Create customer - Success")
    void createCustomer_Success() throws Exception {
        CustomerDto customerDto = CustomerMock.createCustomerDto();
        Mockito.when(customerService.createCustomer(Mockito.any(CustomerDto.class)))
                .thenReturn(customerDto);

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(customerDto))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cpf").value(customerDto.cpf()))
                .andExpect(jsonPath("$.nome").value(customerDto.nome()));
    }

    @Test
    @DisplayName("01 - Create customer - Fail")
    void createCustomer_Fail() throws Exception {
        CustomerDto customerDto = CustomerMock.createCustomerDto();
        Mockito.when(customerService.createCustomer(Mockito.any(CustomerDto.class)))
                .thenReturn(customerDto);

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("02 - Create customer - Validation Error: Nome é obrigatório")
    void createCustomer_NomeObrigatorio() throws Exception {
        CustomerDto invalidCustomerDto = CustomerMock.createCustomerDtoWithoutNome();

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("Nome é obrigatório"));
    }

    @Test
    @DisplayName("03 - Create customer - Validation Error: CPF é obrigatório")
    void createCustomer_CpfObrigatorio() throws Exception {
        CustomerDto invalidCustomerDto = CustomerMock.createCustomerDtoWithoutCpf();

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.cpf").value("CPF é obrigatório"));
    }

    @Test
    @DisplayName("04 - Create customer - Validation Error: Telefone é obrigatório")
    void createCustomer_TelefoneObrigatorio() throws Exception {
        CustomerDto invalidCustomerDto = CustomerMock.createCustomerDtoWithoutTelefone();

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.telefone").value("Telefone é obrigatório"));
    }

    @Test
    @DisplayName("05 - Create customer - Validation Error: Correntista é obrigatório")
    void createCustomer_CorrentistaObrigatorio() throws Exception {
        CustomerDto invalidCustomerDto = CustomerMock.createCustomerDtoWithoutCorrentista();

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.correntista").value("Correntista é obrigatório"));

    }

    @Test
    @DisplayName("06 - Create customer - Validation Error: Saldo da conta corrente é obrigatório")
    void createCustomer_SaldoCcObrigatorio() throws Exception {
        CustomerDto invalidCustomerDto = CustomerMock.createCustomerDtoWithoutSaldoCc();

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.saldoCc").value("Saldo da conta corrente é obrigatório"));
    }

    @Test
    @DisplayName("07 - Create customer - Validation Error: Nome deve ter entre 2 e 150 caracteres")
    void createCustomer_NomeTamanho() throws Exception {
        CustomerDto invalidCustomerDto = CustomerMock.createCustomerDtoWithShortNome();

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("Nome deve ter entre 2 e 150 carácteres"));
    }

    @Test
    @DisplayName("08 - Create customer - Validation Error: Nome deve conter apenas letras")
    void createCustomer_NomeApenasLetras() throws Exception {
        CustomerDto invalidCustomerDto = CustomerMock.createCustomerDtoWithInvalidNome();

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("Nome deve conter apenas letras"));
    }

    @Test
    @DisplayName("09 - Create customer - Validation Error: CPF deve ter 11 dígitos")
    void createCustomer_CpfTamanho() throws Exception {
        CustomerDto invalidCustomerDto = CustomerMock.createCustomerDtoWithInvalidCpf();

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.cpf").value("CPF deve ter 11 dígitos"));
    }

    @Test
    @DisplayName("10 - Create customer - Validation Error: Telefone deve conter 10 ou 11 dígitos")
    void createCustomer_TelefoneTamanho() throws Exception {
        CustomerDto invalidCustomerDto = CustomerMock.createCustomerDtoWithShortTelefone();

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.telefone").value("Telefone deve ser um número válido"));
    }

    @Test
    @DisplayName("11 - Create customer - Validation Error: Score de crédito deve ser maior ou igual a zero")
    void createCustomer_ScoreCreditoNegativo() throws Exception {
        CustomerDto invalidCustomerDto = CustomerMock.createCustomerDtoWithNegativeScore();

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.scoreCredito")
                        .value("Score de crédito deve ser maior ou igual a zero"));
    }

    @Test
    @DisplayName("12 - Create customer - Validation Error: Saldo da conta corrente deve ser maior ou igual a zero")
    void createCustomer_SaldoCcNegativo() throws Exception {
        CustomerDto invalidCustomerDto = CustomerMock.createCustomerDtoWithNegativeSaldoCc();

        mockMvc.perform(post("/customer")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(invalidCustomerDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.saldoCc")
                        .value("Saldo da conta corrente deve ser maior ou igual a zero"));
    }
}
