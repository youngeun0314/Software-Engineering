//package Irumping.IrumOrder.repository;
//
//import Irumping.IrumOrder.entity.OrderEntity;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//@RequiredArgsConstructor
//@Repository
//public class JpaOrderRepository implements OrderRepository{
//
//    private final EntityManager em;
//
//    @Override
//    public OrderEntity findByOrderId(int orderId) {
//        return em.find(OrderEntity.class, orderId);
//    }
//}
