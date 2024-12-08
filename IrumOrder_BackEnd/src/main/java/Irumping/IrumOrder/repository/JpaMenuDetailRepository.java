package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.MenuDetailEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 클래스 설명: MenuDetailEntity에 대한 데이터베이스 작업을 처리하는 JPA 레포지토리 구현체.
 * MenuDetailEntity의 저장 및 조회 기능을 제공한다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@RequiredArgsConstructor
@Repository
public class JpaMenuDetailRepository implements MenuDetailRepository {

    private final EntityManager em; // JPA EntityManager를 통해 데이터베이스 작업 처리

    /**
     * MenuDetailEntity를 저장하거나 갱신한다.
     * ID가 null이면 새로운 엔티티로 간주하여 persist, 그렇지 않으면 merge를 사용한다.
     *
     * @param menuDetailEntity 저장하거나 갱신할 MenuDetailEntity
     */
    @Override
    public void save(MenuDetailEntity menuDetailEntity) {
        if (menuDetailEntity.getMenuDetailId() == null) {
            em.persist(menuDetailEntity); // 새로운 엔티티
        } else {
            em.merge(menuDetailEntity); // 기존 엔티티 갱신
        }
    }
}
