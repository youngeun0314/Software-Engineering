package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.MenuEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 클래스 설명: 메뉴 정보를 DB에 저장하고 조회하는 클래스
 * 메뉴 정보를 DB에 저장하고 조회한다.
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
@Repository
public class JpaMenuRepository implements MenuRepository {

    private final EntityManager em;

    public JpaMenuRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * 메뉴ID로 메뉴 정보를 조회한다.
     *
     * @param menuId 조회할 메뉴의 ID
     * @return 메뉴 정보
     */
    @Override
    public MenuEntity findMenuById(Integer menuId) {
        return em.find(MenuEntity.class, menuId);
    }

    /**
     * 메뉴 이름으로 메뉴 정보를 조회한다.
     *
     * @param name 조회할 메뉴의 이름
     * @return 메뉴 정보
     */
    @Override
    public Optional<MenuEntity> findByName(String name) {
        // jpql
        String jpql = "select m from MenuEntity m where m.name = :name";
        List<MenuEntity> result = em.createQuery(jpql, MenuEntity.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    /**
     * 카테고리별 메뉴 정보를 조회한다.
     *
     * @param categoryId 조회할 카테고리의 ID
     * @return 카테고리별 메뉴 정보
     */
    @Override
    public List<MenuEntity> findByCategory(Integer categoryId) {
        return em.createQuery("select m from MenuEntity m where m.category.id = :categoryId", MenuEntity.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    /**
     * 메뉴를 저장한다.
     *
     * @param menu 저장할 메뉴 정보
     */
    @Override
    public void saveMenu(MenuEntity menu) {
        em.persist(menu);
    }

    /**
     * 메뉴를 삭제한다.
     *
     * @param menuId 삭제할 메뉴의 ID
     */
    @Override
    public void deleteMenu(Integer menuId) {
        MenuEntity menu = em.find(MenuEntity.class, menuId);
        em.remove(menu);
    }
}
