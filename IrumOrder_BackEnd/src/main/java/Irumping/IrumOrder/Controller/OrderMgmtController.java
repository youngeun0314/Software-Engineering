package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Entity.OrderStatus;
import Irumping.IrumOrder.Service.OrderMgmtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class OrderMgmtController {

    private final OrderMgmtService orderMgmtService;

    // 클릭하면
    @PostMapping("/orderStatus")
    public String updateOrderStatus(int orderId, OrderStatus status) {
        // 주문 상태 업데이트
        orderMgmtService.updateOrderStatus(orderId, status);
        return "success";
    }
}
