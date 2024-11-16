package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.CategoryEntity;

import java.util.List;

public interface CategoryRepository {

    public List<CategoryEntity> findAll();

    CategoryEntity findCategoryById(Long categoryId);

    public void saveCategory(CategoryEntity category);

    public void deleteCategory(Long categoryId);
}
