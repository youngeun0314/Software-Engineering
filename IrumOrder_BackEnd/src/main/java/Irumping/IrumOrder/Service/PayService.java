package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Dto.PayApproveResponse;
import Irumping.IrumOrder.Dto.PayReadyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PayService {

    public PayReadyResponse payReady(String name, int totalPrice, String user_id){
        Map<String, String> parameters = getParamsForReady(name, totalPrice, user_id);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate template = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";
        ResponseEntity<PayReadyResponse> responseEntity = template.postForEntity(url, requestEntity, PayReadyResponse.class);

        return responseEntity.getBody();
    }

    public PayApproveResponse payApprove(String tid, String pgToken, String user_id){
        HttpEntity<Map<String, String>> requestEntity = getParamsForApprove(tid, pgToken, user_id);

        RestTemplate template = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";
        PayApproveResponse approveResponse = template.postForObject(url, requestEntity, PayApproveResponse.class);
        log.info("결제승인 응답객체: " + approveResponse);

        return approveResponse;
    }

    private HttpEntity<Map<String, String>> getParamsForApprove(String tid, String pgToken, String user_id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", "TC0ONETIME");              // 가맹점 코드(테스트용)
        parameters.put("tid", tid);                       // 결제 고유번호
        parameters.put("partner_order_id", "1234567890"); // 주문번호
        parameters.put("partner_user_id", user_id);    // 회원 아이디
        parameters.put("pg_token", pgToken);              // 결제승인 요청을 인증하는 토큰

        return new HttpEntity<>(parameters, this.getHeaders());
    }

    private Map<String, String> getParamsForReady(String name, int totalPrice, String user_id){
        //TODO:하드코딩된부분 변수명 받아서->그걸 입력하는 형식으로 변경하기
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", "TC0ONETIME");                                    // 가맹점 코드(테스트용)
        parameters.put("partner_order_id", "1234567890");                       // 주문번호
        parameters.put("partner_user_id", user_id);                          // 회원 아이디
        parameters.put("item_name", name);                                      // 상품명
        parameters.put("quantity", "1");                                        // 상품 수량
        parameters.put("total_amount", String.valueOf(totalPrice));             // 상품 총액
        parameters.put("tax_free_amount", "0");                                 // 상품 비과세 금액
        parameters.put("approval_url", "http://localhost:8080/order/pay/approval/"); // 결제 성공 시 URL
        parameters.put("cancel_url", "http://localhost:8080/order/pay/cancel/");      // 결제 취소 시 URL
        parameters.put("fail_url", "http://localhost:8080/order/pay/fail/");          // 결제 실패 시 URL

        return parameters;
    }

    private HttpHeaders getHeaders(){
        //TODO:Authorization 키: API 호출을 위해 헤더에 포함된 인증 키가 제대로 설정되어 있는지 확인하세요. 실제로 SECRET_KEY 부분은 보안상의 이유로 절대 노출되어서는 안 되며, .env 파일이나 외부 구성에서 가져와야 합니다.
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY DEVEBD88BF4BD9F5D072AAC257CC4B7D70CA7FB4");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
