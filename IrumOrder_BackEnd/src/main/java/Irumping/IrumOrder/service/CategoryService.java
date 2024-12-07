package Irumping.IrumOrder.service;

import Irumping.IrumOrder.entity.CategoryEntity;
import Irumping.IrumOrder.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 클래스 설명: 카테고리 정보 관리를 제공하는 클래스
 * 카테고리 정보를 DB에 저장하고 조회한다.
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository repository;

    /**
     * 카테고리 생성
     *
     * @param categoryName 카테고리 이름
     * @return 저장된 카테고리의 ID
     */
    @Transactional
    public Integer createCategory(String categoryName) {
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryName);
        repository.saveCategory(category);
        return category.getId();
    }

    /**
     * 카테고리 조회
     * 카테고리 ID로 카테고리 정보를 조회한다.
     *
     * @param categoryId 조회할 카테고리의 ID
     * @return 카테고리 정보
     */
    public CategoryEntity findCategoryById(Integer categoryId) {
        return repository.findCategoryById(categoryId);
    }

    /**
     * 카테고리 삭제
     * 카테고리 ID로 카테고리 정보를 삭제한다.
     *
     * @param categoryId 삭제할 카테고리의 ID
     * @return 삭제된 카테고리의 ID
     */
    @Transactional
    public Integer deleteCategory(Integer categoryId) {
        repository.deleteCategory(categoryId);
        return categoryId;
    }

    /**
     * 모든 카테고리 조회
     * 저장된 모든 카테고리 정보를 조회한다.
     *
     * @return DB의 모든 카테고리 정보
     */
    public List<CategoryEntity> findAll() {
        return repository.findAll();
    }
}
