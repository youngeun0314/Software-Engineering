package Irumping.IrumOrder.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalesControllerTest {

    @LocalServerPort
    private int port=8080;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetYesterdaySales_Success() {
        // Given: Prepare the API endpoint and parameters
        String baseUrl = "http://localhost:" + port + "/api/sales";
        String date = "2024-12-02";
        Integer userId = 8;

        // When: Send the GET request
        String url = baseUrl + "?date=" + date + "&userId=" + userId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then: Validate the response
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("totalSales");
    }


    @Test
    void testGetYesterdaySales_UnauthorizedUser() {
        // Given: Unauthorized userId
        String baseUrl = "http://localhost:" + port + "/api/sales";
        String date = "2024-12-02";
        Integer unauthorizedUserId = 2; // User ID not allowed to access data

        // When: Send the GET request
        String url = baseUrl + "?date=" + date + "&userId=" + unauthorizedUserId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Then: Validate the response
        assertThat(response.getStatusCodeValue()).isEqualTo(403); // Forbidden
    }
}
