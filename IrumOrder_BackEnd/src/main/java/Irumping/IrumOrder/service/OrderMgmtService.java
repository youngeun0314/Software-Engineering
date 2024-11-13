package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderMgmtService {
//
//    private final OrderRepository orderRepository;
//
//    public OrderMgmtService(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }
//
//    // 주문 상태 업데이트
//    @Transactional
//    public Long updateOrderStatus(Long orderId, OrderStatus status) {
//        // 주문 엔티티 조회
//        OrderEntity orderEntity = orderRepository.findById(orderId);
//        if (orderEntity == null) {
//            throw new IllegalArgumentException("주문이 존재하지 않습니다.");
//            /* Exception handler 필요하다~~
//            {
//                result: "fail"
//                message: "주문이 존재하지 않습니다."
//            }
//             */
//        }
//        // 주문 상태 업데이트
//        orderEntity.setOrderStatus(status);
//        return orderId;
//    }
}
