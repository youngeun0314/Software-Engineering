package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findByOrderStatus(OrderStatus orderStatus);
}
