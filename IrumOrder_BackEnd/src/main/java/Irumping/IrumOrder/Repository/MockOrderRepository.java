package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MockOrderRepository implements OrderRepository{


    @Override
    public OrderEntity findById(int id) {
        return null;
    }
}
