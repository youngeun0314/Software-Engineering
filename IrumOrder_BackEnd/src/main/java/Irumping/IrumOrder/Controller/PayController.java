package Irumping.IrumOrder.Controller;


import Irumping.IrumOrder.Dto.*;
import Irumping.IrumOrder.Service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


//TODO : DB에 결제 완료로 상태 변경하는 로직 추가
//TODO : 보안기능 추가(결제정보 일치 여부 확인 등)

@Tag(name = "payment")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order/pay")
public class PayController {

    private final PayService payService;


    //TODO : (무슨 데이터를 프론트에서 받아와야할지 + 무슨 데이터 줘야할지) + payService에 어떤 데이터 줘야할지 고치기
    @Operation(
            summary = "결제 요청 준비"
            , description = "결제 요청에 필요한 tid를 얻는다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 준비 완료"),
            @ApiResponse(responseCode = "500", description = "value 입력 필요")
            })
    @PostMapping("/ready")
    public @ResponseBody PayReadyResponse payReady(@RequestBody PayOrderForm payOrderForm){
        if(payOrderForm == null){
            throw new IllegalArgumentException("유효하지 않은 주문 데이터입니다.");
        }

//        String menuName = payOrderForm.getMenuName();
//        int totalPrice = payOrderForm.getTotalPrice();
//        String user_id = payOrderForm.getUser_id();

        log.info("주문 상품 이름: " + payOrderForm.getItem_name());
        log.info("주문 금액: " + payOrderForm.getTotalPrice());

        PayReadyResponse payReadyResponse = payService.payReady(payOrderForm);

        PaySessionUtils.addAttribute("tid", payReadyResponse.getTid());
        log.info("결제 고유번호 : " +payReadyResponse.getTid());


        //TODO : 응답 결과에 따른 return값 분리
        return payReadyResponse;
    }


    @GetMapping("/approval")
    public @ResponseBody PayApproveResponse payCompleted(@RequestParam("pg_token") String pgToken, @RequestParam("user_id") String user_id){
        String tid = PaySessionUtils.getStringAttributeValue("tid");
        log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
        log.info("결제 고유번호: " + tid);

        PayApproveResponse payApproveResponse = payService.payApprove(tid, pgToken, user_id);

        log.info("결제 완료, response : "+payApproveResponse);

        return payApproveResponse;
    }
    //TODO : 결제 실패 / 취소 시 반환값 작성 필요


}
