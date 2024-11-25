package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderEntity;

public interface OrderRepository {

    public OrderEntity findByOrderId(int id);
}
