package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository  extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByOrderIdAndUserId(int orderId, int userId); // 주문 ID와 사용자 ID로 주문 조회

}