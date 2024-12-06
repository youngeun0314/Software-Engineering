package Irumping.IrumOrder.service;

import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.event.PickUpAlarmEvent;
import Irumping.IrumOrder.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OrderMgmtService {

    private final OrderRepository repository;

    private final ApplicationEventPublisher eventPublisher;

    public OrderMgmtService(OrderRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    // 주문 상태 업데이트
    @Transactional
    public String updateOrderStatus(int orderId, OrderStatus newStatus) {
        OrderEntity orderEntity = repository.findByOrderId(orderId);
        if (orderEntity == null) {
            return "fail: 주문이 존재하지 않습니다.";
        }
        // 주문 상태 업데이트
        orderEntity.setOrderStatus(newStatus);
        repository.save(orderEntity);

        // READY_FOR_PICKUP 상태일 경우 이벤트 발행
        if (newStatus == OrderStatus.READY_FOR_PICKUP) {
            log.info("주문 ID {}에 대해 READY_FOR_PICKUP 상태 이벤트를 발행합니다.", orderId);
            eventPublisher.publishEvent(new PickUpAlarmEvent(this, orderEntity));
        }
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
