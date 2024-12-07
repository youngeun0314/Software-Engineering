package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findByOrderStatus(OrderStatus orderStatus);
    public OrderEntity findByOrderId(int id);
    List<OrderEntity> findByUserId(int userId);


}
