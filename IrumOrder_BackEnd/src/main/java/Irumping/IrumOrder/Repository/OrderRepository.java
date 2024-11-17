package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.OrderEntity;

public interface OrderRepository {

    public OrderEntity findByOrderId(int id);
}
