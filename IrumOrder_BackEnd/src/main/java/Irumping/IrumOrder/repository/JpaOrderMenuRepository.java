package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderMenuEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JpaOrderMenuRepository implements OrderMenuRepository {

    private final EntityManager em;

    @Override
    public List<OrderMenuEntity> findByOrder_OrderId(Integer orderId) {
        String jpql = "SELECT om FROM OrderMenuEntity om WHERE om.order.orderId = :orderId";
        TypedQuery<OrderMenuEntity> query = em.createQuery(jpql, OrderMenuEntity.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    @Override
    public void saveAll(List<OrderMenuEntity> orderMenuOptions) {
        if (orderMenuOptions == null || orderMenuOptions.isEmpty()) {
            throw new IllegalArgumentException("The order menu options list must not be null or empty");
        }
        for (OrderMenuEntity orderMenu : orderMenuOptions) {
            if (orderMenu.getId() == null) {
                em.persist(orderMenu); // 새 엔티티는 저장
            } else {
                em.merge(orderMenu);  // 기존 엔티티는 병합
            }
        }
    }

}
