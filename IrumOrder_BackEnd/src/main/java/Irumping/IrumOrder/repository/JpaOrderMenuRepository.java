package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.OrderMenuEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 클래스 설명: 주문 메뉴 관련 데이터베이스 작업을 처리하는 JPA 레포지토리 구현체.
 * EntityManager를 사용하여 주문 메뉴 엔티티를 저장하거나 조회하는 기능을 제공한다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@RequiredArgsConstructor
@Repository
public class JpaOrderMenuRepository implements OrderMenuRepository {

    private final EntityManager em; // JPA EntityManager를 통한 데이터베이스 작업 처리

    /**
     * 주어진 OrderMenuEntity 리스트를 데이터베이스에 저장한다.
     * 새 엔티티는 persist, 기존 엔티티는 merge를 통해 저장된다.
     *
     * @param orderMenuOptions 저장할 OrderMenuEntity 리스트
     * @throws IllegalArgumentException orderMenuOptions가 null이거나 비어있는 경우 예외 발생
     */
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
