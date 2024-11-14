package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {

    public OrderEntity findById(int id);
}
