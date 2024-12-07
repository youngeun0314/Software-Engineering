package Irumping.IrumOrder.service;
/**
 * 클래스 설명: 결제 처리와 관련된 API 요청을 처리하는 서비스 클래스.
 * 주요 로직 처리
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-01
 */

import Irumping.IrumOrder.dto.PayApproveRequest;
import Irumping.IrumOrder.dto.PayApproveResponse;
import Irumping.IrumOrder.dto.PayOrderForm;
import Irumping.IrumOrder.dto.PayReadyResponse;
import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.exception.CustomExceptions.PayErrorException;
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
import java.util.Optional;

@Slf4j
@Service
public class PayService {
    @Value("${kakao.pay.secret-key}")
    private String secretKey;

    @Value("${kakao.pay.approval-url}")
    private String approvalUrl;

    @Value("${kakao.pay.cancel-url}")
    private String cancelUrl;

    @Value("${kakao.pay.fail-url}")
    private String failUrl;

    @Autowired
    private PayRepository payRepository;

    /**
     * 결제 준비를 처리하는 메서드.
     * 사용자의 결제 요청 정보를 바탕으로 카카오페이 API를 호출하여 결제 준비 데이터를 반환.
     *
     * @param payOrderForm 사용자의 결제 요청 정보 (주문 ID, 사용자 ID, 금액 등 포함)
     * @return PayReadyResponse 결제 준비 응답 데이터 (TID 및 리다이렉트 URL 포함)
     * @throws PayErrorException 결제 준비 과정에서 발생하는 오류
     * @throws Exception 이외 예기치 못한 오류
     */
    public PayReadyResponse payReady(PayOrderForm payOrderForm){
        OrderEntity order = payRepository.findByOrderIdAndUserId(payOrderForm.getOrder_id(),payOrderForm.getUser_id());
        if(order.getTotalPrice() != payOrderForm.getTotalPrice()){
            throw new PayErrorException("유효하지 않은 금액입니다. ");
        }
        Map<String, String> parameters = getParamsForReady(payOrderForm);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";
        try{
            RestTemplate template = new RestTemplate();
            ResponseEntity<PayReadyResponse> responseEntity = template.postForEntity(url, requestEntity, PayReadyResponse.class);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("KakaoPay API 호출 실패: URL={}, Params={}, Error={}", url, parameters, e.getMessage(), e);
            throw new PayErrorException("KakaoPay 결제 준비 중 문제가 발생했습니다. 다시 시도해주세요.");
        }catch (Exception e) {
            log.error("KakaoPay API 호출 중 예기치 못한 오류 발생: {}", e.getMessage(), e);
            throw new PayErrorException("결제 처리 중 문제가 발생했습니다. 관리자에게 문의하세요.");
        }

    }

    /**
     * 결제 승인을 처리하는 메서드.
     * 사용자의 결제 요청 정보를 바탕으로 카카오페이 API를 호출하여 결제 준비 데이터를 반환.
     *
     * @param tid, PayApproveRequest 사용자의 결제 승인 요청 정보 (tid, 주문 ID, 사용자 ID 등 포함)
     * @return PayApproveResponse 결제 승인 응답 데이터 (승인시각 등 포함)
     * @throws PayErrorException 결제 승인 과정에서 발생하는 오류
     * @throws Exception 이외 예기치 못한 오류
     */
    public PayApproveResponse payApprove(String tid, PayApproveRequest payApproveRequest){
        HttpEntity<Map<String, String>> requestEntity = getParamsForApprove(tid, payApproveRequest.getPg_token(), payApproveRequest.getUserId(), payApproveRequest.getOrderId());
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";

        try {
            RestTemplate template = new RestTemplate();
            PayApproveResponse approveResponse = template.postForObject(url, requestEntity, PayApproveResponse.class);
            log.info("결제 승인 성공: Response={}", approveResponse);

            assert approveResponse != null;
            OrderEntity order = payRepository.findByOrderIdAndUserId(Integer.parseInt(approveResponse.getPartner_order_id()), Long.parseLong(approveResponse.getPartner_user_id()));
            order.setPayment(LocalDateTime.parse(approveResponse.getApproved_at()));
            payRepository.save(order);
            return approveResponse;
        }  catch (RestClientException ex) {
            log.error("결제 승인 실패: URL={}, Params={}, Error={}", url, requestEntity.getBody(), ex.getMessage(), ex);
            throw new PayErrorException("결제 승인이 실패했습니다. 관리자에게 문의하세요.");
        } catch (Exception ex) {
            log.error("결제 승인 중 예기치 못한 오류 발생: {}", ex.getMessage(), ex);
            throw new PayErrorException("결제 처리 중 문제가 발생했습니다. 관리자에게 문의하세요.");
        }

    }

    /**
     * 결제 준비 중 httpEntity를 만들기 위한 정보를 처리하는 메서드.
     * PayOrderForm의 정보를 추출하여 string형태로 만들어 반환
     *
     * @param payOrderForm 사용자의 결제 준비 요청 정보 (tid, 주문 ID, 사용자 ID 등 포함)
     * @return Map<String, String> 정보를 string 형태로 만든 값
     */
    private Map<String, String> getParamsForReady(PayOrderForm payOrderForm){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", payOrderForm.getCid() );                                    // 가맹점 코드(테스트용)
        parameters.put("partner_order_id", String.valueOf(payOrderForm.getOrder_id()));                       // 주문번호
        parameters.put("partner_user_id", String.valueOf(payOrderForm.getUser_id()));                          // 회원 아이디
        parameters.put("item_name", payOrderForm.getItem_name());                                      // 상품명
        parameters.put("quantity", String.valueOf(payOrderForm.getQuantity()));                                        // 상품 수량
        parameters.put("total_amount", String.valueOf(payOrderForm.getTotalPrice()));             // 상품 총액
        parameters.put("tax_free_amount", String.valueOf(payOrderForm.getTax_free_amount()));                                 // 상품 비과세 금액
        parameters.put("approval_url", approvalUrl); // 결제 성공 시 URL
        parameters.put("cancel_url", cancelUrl);      // 결제 취소 시 URL
        parameters.put("fail_url", failUrl);          // 결제 실패 시 URL

        return parameters;
    }

    /**
     * 결제 승인 중 httpEntity를 만들기 위한 정보를 처리하는 메서드.
     * tid, pgToken, user_id, order_id를 string 형태로 만들어 반환
     *
     * @param tid, pgToken, user_id, order_id 사용자의 결제 준비 요청 정보 (tid, 주문 ID, 사용자 ID 등 포함)
     * @return Map<String, String> 정보를 string 형태로 만든 값
     */
    private HttpEntity<Map<String, String>> getParamsForApprove(String tid, String pgToken, long user_id, int order_id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", "TC0ONETIME");              // 가맹점 코드(테스트용)
        parameters.put("tid", tid);                       // 결제 고유번호
        parameters.put("partner_order_id", String.valueOf(order_id)); // 주문번호
        parameters.put("partner_user_id", String.valueOf(user_id));    // 회원 아이디
        parameters.put("pg_token", pgToken);              // 결제승인 요청을 인증하는 토큰

        return new HttpEntity<>(parameters, this.getHeaders());
    }

    /**
     * http 헤더를 만드는 메서드
     * 페이에 필요한 secret key 포함
     *
     *  @return HttpHeaders http헤더값
     */
    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("KAKAO_PAY_SECRET_KEY가 설정되지 않았습니다.");
        }
        headers.set("Authorization", "SECRET_KEY " + secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * 결제 준비 중 생긴 tid를 db에 저장하는 메서드
     * user_id, order_id를 통해 OrderEntity를 찾은 후, tid값 저장
     *
     * @param tid, user_id, order_id
     * @throws IllegalArgumentException 존재하지 않는 주문일 경우 발생하는 오류
     */
    public void saveTransaction(int orderId, Integer userId, String tid){
        Optional<OrderEntity> optionalOrder = Optional.ofNullable(
                payRepository.findByOrderIdAndUserId(orderId, userId)
        );

        OrderEntity order = optionalOrder.orElseThrow(
                () -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다: orderId=" + orderId + ", userId=" + userId)
        );
        order.setTid(tid);
        payRepository.save(order);
    }

    /**
     * 결제 준비 중 생긴 tid를 db에서 가져오는 메서드
     * user_id, order_id를 통해 OrderEntity를 찾은 후, tid값 반환
     *
     * @param userId, orderId
     */
    public String getTransactionTid(Integer userId, int orderId) {
        OrderEntity order = payRepository.findByOrderIdAndUserId(orderId,userId);
        return order.getTid();
    }

    /**
     * user Id와 order Id가 유효한지 검사하는 메서드
     * user_id, order_id를 통해 OrderEntity를 찾은 후, 존재하면 0, 존재하지 않으면 1 반환
     *
     * @param userId, orderId
     */
    public boolean isUserValid(long userId, int orderId) {
        OrderEntity order = payRepository.findByOrderIdAndUserId(orderId, userId);
        return order == null; // 주문 정보가 있다면 유효
    }
}
