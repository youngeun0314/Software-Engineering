package Irumping.IrumOrder.Controller;


import Irumping.IrumOrder.Dto.PayApproveResponse;
import Irumping.IrumOrder.Dto.PayOrderForm;
import Irumping.IrumOrder.Dto.PayReadyResponse;
import Irumping.IrumOrder.Dto.PaySessionUtils;
import Irumping.IrumOrder.Service.PayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "kakaoPay")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order/pay")
public class PayController {

    private final PayService payService;
    @PostMapping("/ready")
    public @ResponseBody PayReadyResponse payReady(@RequestBody PayOrderForm payOrderForm){
        if(payOrderForm == null || payOrderForm.getName()==null || payOrderForm.getTotalPrice() <= 0){
            throw new IllegalArgumentException("유효하지 않은 주문 데이터입니다.");
        }

        String name = payOrderForm.getName();
        int totalPrice = payOrderForm.getTotalPrice();

        log.info("주문 상품 이름: " + name);
        log.info("주문 금액: " + totalPrice);

        PayReadyResponse payReadyResponse = payService.payReady(name, totalPrice);

        PaySessionUtils.addAttribute("tid", payReadyResponse.getTid());
        log.info("결제 고유번호 : " +payReadyResponse.getTid());

        return payReadyResponse;
    }

    @GetMapping("/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken){
        String tid = PaySessionUtils.getStringAttributeValue("tid");
        log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
        log.info("결제 고유번호: " + tid);

        PayApproveResponse payApproveResponse = payService.payApprove(tid, pgToken);

        return "redirect:/order/completed";
    }
}
