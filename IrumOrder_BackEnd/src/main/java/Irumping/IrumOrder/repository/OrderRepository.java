package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderStatus;

import java.util.List;

public interface OrderRepository{
    List<OrderEntity> findByOrderStatus(OrderStatus orderStatus);
    OrderEntity findByOrderId(Integer orderId);

    List<OrderEntity> findByUserId(Integer userId);

    void save(OrderEntity order);
}
