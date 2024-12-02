package Irumping.IrumOrder.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 클래스 설명: pay 관련 통합 테스트
 * 결제 준비 및 승인 테스트
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-02
 */

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PayControllerTest {

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;


    /**
     * 메서드 설명: 결제 준비를 처리 테스트.
     * order DB에 존재하는 값으로 테스트해야 함.
     * 테스트케이스 성공 후 log에 뜨는 next_redirect_url로 접속하여 카카오페이 결제를 완료한 후 나오는 pg_token으로 payCompleted 테스트 가능
     */
    @Test
    void payReady() {
        // Given: 요청 데이터 준비
        String baseUrl = "http://localhost:" + port + "/order/pay/ready";

        var requestBody = """
        {
            "cid": "TC0ONETIME",
            "order_id": 6,
            "user_id": 1,
            "item_name": "Product Name",
            "quantity": 2,
            "totalPrice": 4500,
            "tax_free_amount": 0
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When: API 호출
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        // Then: 응답 검증
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("tid", "next_redirect_pc_url", "next_redirect_mobile_url");
        log.info("Redirect URL: {}", response.getBody().toString());

    }

    /**
     * 메서드 설명: 결제 승인 테스트
     * payReady 테스트케이스 실행 후 log에 뜨는 next_redirect_url로 접속하여
     * 카카오페이 결제를 완료한 후 나오는 pg_token을 사용하여 테스트 가능
     */
    @Test
    void payCompleted() {
        // Given: 요청 데이터 준비
        String baseUrl = "http://localhost:" + port + "/order/pay/approval";

        var requestBody = """
        {
            "pg_token": "d5cb9b4322cc3642b2fb",
            "userId": 1,
            "orderId": 6,
            "cid": "TC0ONETIME"
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // When: API 호출
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        // Then: 응답 검증
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains("aid", "tid", "approved_at");
    }
}