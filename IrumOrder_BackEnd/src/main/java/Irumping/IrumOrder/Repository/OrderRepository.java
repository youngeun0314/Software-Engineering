package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    // 특정 유저의 주문 내역 조회
    List<OrderEntity> findByUserId(int userId);

    // 특정 주문 상태와 픽업 시간이 NULL인 주문 조회 (픽업 예약을 하지 않은 상태의 주문만 조회)
    List<OrderEntity> findByPickUpIsNullAndOrderStatus(String orderStatus);

    // 특정 픽업 시간에 맞춰 주문 조회 (예약된 픽업 시간에 따른 조회)
    List<OrderEntity> findByPickUp(LocalTime pickUp);

    // 픽업 시간이 지정된 모든 주문을 픽업 시간 순서로 조회
    List<OrderEntity> findByPickUpIsNotNullOrderByPickUpAsc();

    // 결제 완료된 주문 조회
    List<OrderEntity> findByPaymentTrue();
}
