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

/**
 * 클래스 설명: OrderMgmtController는 주문 관리와 관련된 요청을 처리하는 컨트롤러입니다.
 *             직원이 주문 상태를 확인하고, 주문 상태를 업데이트하는 기능을 제공합니다.
 *
 * 주요 기능:
 * - 주문 상태 업데이트: 주문 상태를 조리 단계에 따라 변경.
 * - 주문 확인: 특정 주문 정보를 확인.
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-08
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/orderMgmt")
public class OrderMgmtController {


    private final OrderMgmtService orderMgmtService;

    /**
     * 주문 상태를 업데이트하는 메서드입니다.
     * 직원이 주문 관리 시스템에서 주문 상태를 조리 단계에 따라 업데이트할 수 있습니다.
     *
     * @param orderId 상태를 업데이트할 주문 ID
     * @param status  변경할 주문 상태 (OrderStatus Enum 값)
     * @return 업데이트 결과를 포함한 HTTP 응답 객체
     *         - 성공 시: JSON 형태의 성공 메시지와 주문 ID 반환
     *         - 실패 시: 404 상태 코드와 실패 메시지 반환
     */
    @PostMapping("/orderStatus")
    public ResponseEntity<?>  updateOrderStatus(@RequestParam(name="orderId") int orderId, @RequestParam(name="status") OrderStatus status) {
        String result = orderMgmtService.updateOrderStatus(orderId, status);
        if (result.startsWith("fail")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"result\":\"fail\", \"message\":\"주문이 존재하지 않습니다.\"}");
        }
        return ResponseEntity.ok("{\"result\":\"success\", \"orderId\":" + orderId + "}");
    }

    /**
     * 특정 주문 정보를 확인하는 메서드입니다.
     * 직원이 주문 관리 시스템에서 주문 ID를 통해 주문 정보를 조회할 수 있습니다.
     *
     * @param orderId 확인할 주문 ID
     * @return 주문 정보를 포함한 HTTP 응답 객체
     *         - 성공 시: OrderEntity 객체 반환
     *         - 실패 시: 404 상태 코드와 실패 메시지 반환
     */
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