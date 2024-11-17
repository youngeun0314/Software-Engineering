package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.OrderStatus;
import Irumping.IrumOrder.Repository.JpaOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderMgmtService {

    private final JpaOrderRepository repository;

    // 주문 상태 업데이트
    @Transactional
    public String updateOrderStatus(int orderId, OrderStatus status) {
        OrderEntity orderEntity = repository.findByOrderId(orderId);
        if (orderEntity == null) {
            return "fail: 주문이 존재하지 않습니다.";
        }
        orderEntity.setOrderStatus(status);
        return "success";
    }

    // 주문 확인
    public OrderEntity checkOrder(int orderId) {
        return repository.findByOrderId(orderId);
    }

//    // 주문 상태 업데이트
//    public ResponseEntity<?> updateOrderStatus(int orderId, OrderStatus status) {
//        // 주문 엔티티 조회
//        OrderEntity orderEntity = repository.findById(orderId);
//        if (orderEntity == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("{\"result\":\"fail\", \"message\":\"주문이 존재하지 않습니다.\"}");
//        }
//        // 주문 상태 업데이트
//        orderEntity.setOrderStatus(status);
//        return ResponseEntity.ok("{\"result\":\"success\", \"orderId\":" + orderId + "}");
//    }
//
//    // 주문 확인
//    public ResponseEntity<?> checkOrder(int orderId) {
//        // 주문 엔티티 조회
//        OrderEntity orderEntity = repository.findById(orderId);
//        if (orderEntity == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("{\"result\":\"fail\", \"message\":\"주문이 존재하지 않습니다.\"}");
//        }
//        return ResponseEntity.ok(orderEntity);
//    }
}
