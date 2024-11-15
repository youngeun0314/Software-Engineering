package Irumping.IrumOrder.Repository;

import Irumping.IrumOrder.Entity.CategoryEntity;
import Irumping.IrumOrder.Entity.MenuDetailEntity;
import Irumping.IrumOrder.Entity.MenuEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    public List<CategoryEntity> findAll();

    CategoryEntity findCategoryById(Long categoryId);

    public void saveCategory(CategoryEntity category);

    public void deleteCategory(Long categoryId);
}
