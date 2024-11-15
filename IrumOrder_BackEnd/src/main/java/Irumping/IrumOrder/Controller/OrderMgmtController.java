package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.OrderStatus;
import Irumping.IrumOrder.Service.OrderMgmtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderMgmtController {

    // - 직원은 주문 관리 시스템을 통해 회원이 주문한 음료를 확인하고 직원은 주문 상태를 조리 정도에 따라 업데이트한다.

    private final OrderMgmtService orderMgmtService;

    // 주문 상태 업데이트
    @PostMapping("/orderStatus")
    public ResponseEntity<?> updateOrderStatus(@RequestParam int orderId, @RequestParam OrderStatus status) {
        String result = orderMgmtService.updateOrderStatus(orderId, status);
        if (result.startsWith("fail")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"result\":\"fail\", \"message\":\"주문이 존재하지 않습니다.\"}");
        }
        return ResponseEntity.ok("{\"result\":\"success\", \"orderId\":" + orderId + "}");
    }

    // 주문 확인
    @PostMapping("/checkOrder")
    public ResponseEntity<?> checkOrder(@RequestParam int orderId) {
        OrderEntity orderEntity = orderMgmtService.checkOrder(orderId);
        if (orderEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"result\":\"fail\", \"message\":\"주문이 존재하지 않습니다.\"}");
        }
        return ResponseEntity.ok(orderEntity);
    }

//
//    private final OrderMgmtService orderMgmtService;
//
//    // 클릭하면
//    @PostMapping("/orderStatus")
//    public String updateOrderStatus(int orderId, OrderStatus status) {
//        // 주문 상태 업데이트
//        orderMgmtService.updateOrderStatus(orderId, status);
//        return "success";
//    }
//
//    // 회원이 주문한 음료 확인 로직
//    @PostMapping("/checkOrder")
//    public String checkOrder(int orderId) {
//        // 주문 엔티티 조회
//        orderMgmtService.checkOrder(orderId);
//        return "success";
//    }
}
