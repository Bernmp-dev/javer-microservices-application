package com.bernmpdev.javerpersistenceservice.controller;

import com.bernmpdev.javerpersistenceservice.exception.CustomerNotFoundException;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
@DisplayName("[CONTROLLER] - Delete customer tests")
@WithMockUser(username = "ADMIN", roles = {"ADMIN"})
public class DeleteCustomerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("01 - Delete customer - Success")
    void deleteCustomer_Success() throws Exception {
        Mockito.doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/customer/1")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("02 - Delete customer - Not Found")
    void deleteCustomer_NotFound() throws Exception {
        Mockito.doThrow(new CustomerNotFoundException()).when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/customer/1")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(result -> 
                    System.out.println("Response: " + result.getResponse().getContentAsString()));
    }

    @Test
    @DisplayName("03 - Delete customer - Internal Server Error")
    void deleteCustomer_InternalServerError() throws Exception {
        Mockito.doThrow(new RuntimeException("Internal server error")).when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/customer/1")
                        .header("X-Origin-Header", "javer-proxy-service")
                        .with(csrf()))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> 
                    System.out.println("Response: " + result.getResponse().getContentAsString()));
    }
}
