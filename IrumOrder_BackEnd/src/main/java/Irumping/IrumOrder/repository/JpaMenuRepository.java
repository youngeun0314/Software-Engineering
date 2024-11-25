package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.MenuEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaMenuRepository implements MenuRepository {

    private final EntityManager em;

    public JpaMenuRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public MenuEntity findMenuById(Long menuId) {
        return em.find(MenuEntity.class, menuId);
    }

    @Override
    public Optional<MenuEntity> findByName(String name) {
        // jpql
        String jpql = "select m from MenuEntity m where m.name = :name";
        List<MenuEntity> result = em.createQuery(jpql, MenuEntity.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<MenuEntity> findByCategory(Long categoryId) {
        return em.createQuery("select m from MenuEntity m where m.category.id = :categoryId", MenuEntity.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public void saveMenu(MenuEntity menu) {
        em.persist(menu);
    }

    @Override
    public void deleteMenu(Long menuId) {
        MenuEntity menu = em.find(MenuEntity.class, menuId);
        em.remove(menu);
    }
}
