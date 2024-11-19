package Irumping.IrumOrder.service;

import Irumping.IrumOrder.dto.PayApproveResponse;
import Irumping.IrumOrder.dto.PayOrderForm;
import Irumping.IrumOrder.dto.PayReadyResponse;
import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.repository.PayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PayService {
    @Value("${kakao.pay.secret-key}")
    private String secretKey;

    @Autowired
    private PayRepository payRepository;

    public PayReadyResponse payReady(PayOrderForm payOrderForm){
        Map<String, String> parameters = getParamsForReady(payOrderForm);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate template = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";
        ResponseEntity<PayReadyResponse> responseEntity = template.postForEntity(url, requestEntity, PayReadyResponse.class);

        return responseEntity.getBody();
    }

    public PayApproveResponse payApprove(String tid, String pgToken, String userId, String orderId){
        try {
            HttpEntity<Map<String, String>> requestEntity = getParamsForApprove(tid, pgToken, userId, orderId);

            RestTemplate template = new RestTemplate();
            String url = "https://open-api.kakaopay.com/online/v1/payment/approve";
            PayApproveResponse approveResponse = template.postForObject(url, requestEntity, PayApproveResponse.class);
            log.info("결제승인 응답객체: " + approveResponse);

            assert approveResponse != null;
            OrderEntity order = payRepository.findByOrderIdAndUserId(Integer.parseInt(approveResponse.getPartner_order_id()), Integer.parseInt(approveResponse.getPartner_user_id()));
            order.setPayDatetime(LocalDateTime.parse(approveResponse.getApproved_at()));
            payRepository.save(order);
            return approveResponse;
        } catch(RestClientException ex){
            log.error("결제 승인 실패: " + ex.getMessage());

            return getPayFailedResponse(tid, userId, ex);
        }
    }

    private static PayApproveResponse getPayFailedResponse(String tid, String userId, RestClientException ex) {
        PayApproveResponse failedResponse = new PayApproveResponse();
        failedResponse.setAid(null);
        failedResponse.setTid(tid);
        failedResponse.setPartner_order_id(null);
        failedResponse.setPartner_user_id(userId);
        failedResponse.setPayment_method_type("ERROR");
        failedResponse.setItem_name(null);
        failedResponse.setCreated_at(null);
        failedResponse.setApproved_at(null);
        failedResponse.setPayload("결제 승인 실패: " + ex.getMessage());
        return failedResponse;
    }

    private Map<String, String> getParamsForReady(PayOrderForm payOrderForm){
        //TODO:하드코딩된부분 변수명 받아서->그걸 입력하는 형식으로 변경하기
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", payOrderForm.getCid() );                                    // 가맹점 코드(테스트용)
        parameters.put("partner_order_id", payOrderForm.getOrder_id());                       // 주문번호
        parameters.put("partner_user_id", payOrderForm.getUser_id());                          // 회원 아이디
        parameters.put("item_name", payOrderForm.getItem_name());                                      // 상품명
        parameters.put("quantity", String.valueOf(payOrderForm.getQuantity()));                                        // 상품 수량
        parameters.put("total_amount", String.valueOf(payOrderForm.getTotalPrice()));             // 상품 총액
        parameters.put("tax_free_amount", String.valueOf(payOrderForm.getTax_free_amount()));                                 // 상품 비과세 금액
        parameters.put("approval_url", "http://localhost:8080/order/pay/approval/"); // 결제 성공 시 URL
        parameters.put("cancel_url", "http://localhost:8080/order/pay/cancel/");      // 결제 취소 시 URL
        parameters.put("fail_url", "http://localhost:8080/order/pay/fail/");          // 결제 실패 시 URL

        return parameters;
    }

    private HttpEntity<Map<String, String>> getParamsForApprove(String tid, String pgToken, String user_id, String order_id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", "TC0ONETIME");              // 가맹점 코드(테스트용)
        parameters.put("tid", tid);                       // 결제 고유번호
        parameters.put("partner_order_id", order_id); // 주문번호
        parameters.put("partner_user_id", user_id);    // 회원 아이디
        parameters.put("pg_token", pgToken);              // 결제승인 요청을 인증하는 토큰

        return new HttpEntity<>(parameters, this.getHeaders());
    }

    private HttpHeaders getHeaders(){
        //TODO:Authorization 키: API 호출을 위해 헤더에 포함된 인증 키가 제대로 설정되어 있는지 확인하세요. 실제로 SECRET_KEY 부분은 보안상의 이유로 절대 노출되어서는 안 되며, .env 파일이나 외부 구성에서 가져와야 합니다.
        HttpHeaders headers = new HttpHeaders();
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("KAKAO_PAY_SECRET_KEY가 설정되지 않았습니다.");
        }
        headers.set("Authorization", "SECRET_KEY " + secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
