package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.OrderEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaOrderRepository implements OrderRepository{

    private EntityManager em;

    @Override
    public OrderEntity findById(int orderId) {
        return em.find(OrderEntity.class, orderId);
    }
}
