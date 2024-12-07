package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.CategoryEntity;

import java.util.List;

/**
 * 클래스 설명: 카테고리 정보를 DB에 저장하고 조회하는 인터페이스
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
public interface CategoryRepository {

    public List<CategoryEntity> findAll();

    CategoryEntity findCategoryById(Integer categoryId);

    public void saveCategory(CategoryEntity category);

    public void deleteCategory(Integer categoryId);
}
