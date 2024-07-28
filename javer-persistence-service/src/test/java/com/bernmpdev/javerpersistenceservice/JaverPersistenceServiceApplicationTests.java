package com.bernmpdev.javerpersistenceservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("[APPLICATION] - JaverPersistenceService Application Tests")
class JaverPersistenceServiceApplicationTests {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    @DisplayName("01 - Context Loads Successfully")
    void contextLoads() {
        Assertions.assertNotNull(datasourceUrl);
    }

    @Test
    @DisplayName("02 - Customer Service Bean Loaded")
    void testCustomerServiceBeanLoaded() {
        Assertions.assertTrue(webApplicationContext.containsBean("customerService"));
    }

    @Test
    @DisplayName("03 - Customer Repository Bean Loaded")
    void testCustomerRepositoryBeanLoaded() {
        Assertions.assertTrue(webApplicationContext.containsBean("customerRepository"));
    }

    @Test
    @DisplayName("04 - Datasource URL is Correct")
    void testDatasourceUrl() {
        Assertions.assertEquals("jdbc:h2:mem:testdb", datasourceUrl);
    }
}
