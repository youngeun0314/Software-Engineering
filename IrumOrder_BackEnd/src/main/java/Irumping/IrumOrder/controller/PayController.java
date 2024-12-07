package Irumping.IrumOrder.controller;
/**
 * 클래스 설명: 결제 처리와 관련된 API 요청을 처리하는 컨트롤러 클래스.
 * 결제 준비 및 승인과 같은 주요 작업을 수행한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-01
 */

import Irumping.IrumOrder.dto.*;
import Irumping.IrumOrder.exception.CustomExceptions.InvalidInputException;
import Irumping.IrumOrder.exception.CustomExceptions.PayErrorException;
import Irumping.IrumOrder.exception.CustomExceptions.UserIdMismatchException;
import Irumping.IrumOrder.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Payment API", description = "Payment processing APIs for managing payment operations")
@RestController
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order/pay")
public class PayController {

    private final PayService payService;


    /**
     * 메서드 설명: 결제 준비를 처리하며, 카카오페이 API를 호출하여 TID를 반환.
     *
     * @param payOrderForm 사용자의 결제 요청 정보 (주문 ID, 사용자 ID 등 포함)
     * @return PayReadyResponse 결제 준비 응답 데이터 (TID 및 리다이렉트 URL 포함)
     */
    @Operation(
            summary = "결제 요청 준비"
            , description = "결제 요청에 필요한 tid를 얻는다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 준비 완료"),
            @ApiResponse(responseCode = "400", description = "User ID mismatch"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping("/ready")
    public @ResponseBody PayReadyResponse payReady(@RequestBody PayOrderForm payOrderForm){
        if(payOrderForm == null){
            throw new IllegalArgumentException("null order form.");
        }
        if (payOrderForm.getUser_id() == null || payOrderForm.getOrder_id() == null) {
            throw new InvalidInputException("Missing user ID or order ID in the input.");
        }
        if (payService.isUserValid(payOrderForm.getUser_id(), payOrderForm.getOrder_id())) {
            throw new UserIdMismatchException("User ID does not match with the order ID.");
        }


        log.info("주문 상품 이름: " + payOrderForm.getItem_name());
        log.info("주문 금액: " + payOrderForm.getTotalPrice());

        PayReadyResponse payReadyResponse = payService.payReady(payOrderForm);

        payService.saveTransaction(payOrderForm.getOrder_id(), payOrderForm.getUser_id(), payReadyResponse.getTid());

        log.info("결제 고유번호 : " +payReadyResponse.getTid());


        if (payReadyResponse.getTid() == null) {
            log.error("KakaoPay 결제 준비 응답이 올바르지 않습니다: {}", payReadyResponse);
            throw new PayErrorException("결제 준비 응답이 올바르지 않습니다.");
        }

        return payReadyResponse;
    }


    /**
     * 메서드 설명: 사용자 인증 후 결제 승인을 처리.
     *
     * @param payApproveRequest 결제 승인 요청 정보 (사용자 ID, 주문 ID 등 포함)
     * @return PayApproveResponse 결제 승인 응답 데이터 (승인 시간, TID 등 포함)
     */
    @Operation(
            summary = "Complete Payment",
            description = "Approves a payment after user authorization.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payment approval successful"),
                    @ApiResponse(responseCode = "400", description = "Invalid input provided"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/approval")
    public @ResponseBody PayApproveResponse payCompleted(@RequestBody PayApproveRequest payApproveRequest){

        if (payService.isUserValid(payApproveRequest.getUserId(), payApproveRequest.getOrderId())) {
            throw new UserIdMismatchException("User ID does not match with the order ID.");
        }
        String tid = payService.getTransactionTid(payApproveRequest.getUserId(), payApproveRequest.getOrderId());
        log.info("결제승인 요청을 인증하는 토큰: " + payApproveRequest.getPg_token());
        log.info("결제 고유번호: " + tid);

        PayApproveResponse payApproveResponse = payService.payApprove(tid, payApproveRequest);

        log.info("결제 완료, response : "+payApproveResponse);

        return payApproveResponse;
    }


}
