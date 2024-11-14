package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.OrderStatus;
import Irumping.IrumOrder.Repository.MockOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderMgmtService {

    private final MockOrderRepository repository;

    // 주문 상태 업데이트
    @Transactional
    public int updateOrderStatus(int orderId, OrderStatus status) {
        // 주문 엔티티 조회
        OrderEntity orderEntity = repository.findById(orderId);
        if (orderEntity == null) {
            throw new IllegalArgumentException("주문이 존재하지 않습니다.");
            /* Exception handler 필요하다~~
            {
                result: "fail"
                message: "주문이 존재하지 않습니다."
            }
             */
        }
        // 주문 상태 업데이트
        orderEntity.setOrderStatus(status);
        return orderId;
    }
}
