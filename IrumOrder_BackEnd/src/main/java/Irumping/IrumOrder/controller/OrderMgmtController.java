package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.service.OrderMgmtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orderMgmt")
public class OrderMgmtController {

    // - 직원은 주문 관리 시스템을 통해 회원이 주문한 음료를 확인하고 직원은 주문 상태를 조리 정도에 따라 업데이트한다.

    private final OrderMgmtService orderMgmtService;

    // 주문 상태 업데이트
    @PostMapping("/orderStatus")
    public ResponseEntity<?>  updateOrderStatus(@RequestParam(name="orderId") int orderId, @RequestParam(name="status") OrderStatus status) {
        String result = orderMgmtService.updateOrderStatus(orderId, status);
        if (result.startsWith("fail")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"result\":\"fail\", \"message\":\"주문이 존재하지 않습니다.\"}");
        }
        return ResponseEntity.ok("{\"result\":\"success\", \"orderId\":" + orderId + "}");
    }

    // 주문 확인
    @PostMapping("/checkOrder")
    public ResponseEntity<?> checkOrder(@RequestParam(name="orderId") int orderId) {
        OrderEntity orderEntity = orderMgmtService.checkOrder(orderId);
        if (orderEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"result\":\"fail\", \"message\":\"주문이 존재하지 않습니다.\"}");
        }
        return ResponseEntity.ok(orderEntity);
    }
}