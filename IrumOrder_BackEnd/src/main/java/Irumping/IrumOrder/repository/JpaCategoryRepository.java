package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.CategoryEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaCategoryRepository implements CategoryRepository {

    private final EntityManager em;

    public JpaCategoryRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<CategoryEntity> findAll() {
        return em.createQuery("select c from CategoryEntity c", CategoryEntity.class)
                .getResultList();
    }

    @Override
    public CategoryEntity findCategoryById(Long categoryId) {
        return em.find(CategoryEntity.class, categoryId);
    }

    @Override
    public void saveCategory(CategoryEntity category) {
        em.persist(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        CategoryEntity category = em.find(CategoryEntity.class, categoryId);
        em.remove(category);
    }
}
