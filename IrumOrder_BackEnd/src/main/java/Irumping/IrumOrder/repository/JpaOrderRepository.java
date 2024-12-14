package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 클래스 설명: 주문 관련 데이터베이스 작업을 처리하는 JPA 레포지토리 구현체.
 * 주문 엔티티에 대한 조회, 저장 및 갱신 기능을 제공한다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@RequiredArgsConstructor
@Repository
public class JpaOrderRepository implements OrderRepository {

    private final EntityManager em; // JPA EntityManager를 통해 데이터베이스 작업 처리

    /**
     * 주어진 주문 상태(OrderStatus)에 해당하는 주문 엔티티 리스트를 조회한다.
     *
     * @param orderStatus 조회할 주문 상태
     * @return 주문 상태에 해당하는 OrderEntity 리스트
     */
    @Override
    public List<OrderEntity> findByOrderStatus(OrderStatus orderStatus) {
        String jpql = "SELECT o FROM OrderEntity o WHERE o.orderStatus = :orderStatus";
        TypedQuery<OrderEntity> query = em.createQuery(jpql, OrderEntity.class);
        query.setParameter("orderStatus", orderStatus);
        return query.getResultList();
    }

    /**
     * 주어진 주문 ID로 주문 엔티티를 조회한다.
     * 주문이 존재하지 않을 경우 EntityNotFoundException을 발생시킨다.
     *
     * @param orderId 조회할 주문 ID
     * @return 주어진 ID에 해당하는 OrderEntity
     * @throws EntityNotFoundException 주문 ID가 존재하지 않을 경우 예외 발생
     */
    @Override
    public OrderEntity findByOrderId(Integer orderId) {
        OrderEntity orderEntity = em.find(OrderEntity.class, orderId);
        if (orderEntity == null) {
            throw new EntityNotFoundException("Order with ID " + orderId + " not found");
        }
        return orderEntity;
    }

    /**
     * 주어진 사용자 ID로 해당 사용자의 주문 리스트를 조회한다.
     *
     * @param userId 조회할 사용자 ID
     * @return 사용자 ID에 해당하는 OrderEntity 리스트
     */
    @Override
    public List<OrderEntity> findByUserId(Integer userId) {
        String jpql = "SELECT o FROM OrderEntity o WHERE o.userId = :userId";
        TypedQuery<OrderEntity> query = em.createQuery(jpql, OrderEntity.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    /**
     * 주문 엔티티를 저장하거나 갱신한다.
     * 주문 ID가 null이면 새로운 엔티티로 간주하여 persist, 그렇지 않으면 기존 엔티티로 간주하여 merge를 사용한다.
     *
     * @param order 저장하거나 갱신할 OrderEntity
     */
    @Override
    public void save(OrderEntity order) {
        if (order.getOrderId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }
    }
}
