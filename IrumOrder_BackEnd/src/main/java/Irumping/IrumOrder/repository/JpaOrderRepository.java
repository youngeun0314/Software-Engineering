package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JpaOrderRepository implements OrderRepository {

    private final EntityManager em;

    @Override
    public List<OrderEntity> findByOrderStatus(OrderStatus orderStatus) {
        String jpql = "SELECT o FROM OrderEntity o WHERE o.orderStatus = :orderStatus";
        TypedQuery<OrderEntity> query = em.createQuery(jpql, OrderEntity.class);
        query.setParameter("orderStatus", orderStatus);
        return query.getResultList();
    }

    @Override
    public OrderEntity findByOrderId(Integer orderId) {
        OrderEntity orderEntity = em.find(OrderEntity.class, orderId);
        if (orderEntity == null) {
            throw new EntityNotFoundException("Order with ID " + orderId + " not found");
        }
        return orderEntity;
    }


    @Override
    public List<OrderEntity> findByUserId(Integer userId) {
        String jpql = "SELECT o FROM OrderEntity o WHERE o.userId = :userId";
        TypedQuery<OrderEntity> query = em.createQuery(jpql, OrderEntity.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public void save(OrderEntity order) {
        if (order.getOrderId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }
    }
}
