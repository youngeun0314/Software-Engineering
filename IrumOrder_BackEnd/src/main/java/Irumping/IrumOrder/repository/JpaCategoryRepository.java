package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.CategoryEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 클래스 설명: 카테고리 정보를 DB에 저장하고 조회하는 클래스
 * 카테고리 정보를 DB에 저장하고 조회한다.
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
@Repository
public class JpaCategoryRepository implements CategoryRepository {

    private final EntityManager em;

    public JpaCategoryRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * DB의 모든 카테고리 정보를 조회한다.
     * @return DB의 모든 카테고리 정보
     */
    @Override
    public List<CategoryEntity> findAll() {
        return em.createQuery("select c from CategoryEntity c", CategoryEntity.class)
                .getResultList();
    }

    /**
     * DB에서 카테고리 아이디로 카테고리 정보를 조회한다.
     *
     * @param categoryId 카테고리 아이디
     * @return 카테고리 정보
     */
    @Override
    public CategoryEntity findCategoryById(Integer categoryId) {
        return em.find(CategoryEntity.class, categoryId);
    }

    /**
     * 카테고리 정보를 DB에 저장한다.
     *
     * @param category 카테고리 정보
     */
    @Override
    public void saveCategory(CategoryEntity category) {
        em.persist(category);
    }

    /**
     * 카테고리 정보를 DB에서 삭제한다.
     *
     * @param categoryId 카테고리 아이디
     */
    @Override
    public void deleteCategory(Integer categoryId) {
        CategoryEntity category = em.find(CategoryEntity.class, categoryId);
        em.remove(category);
    }
}
