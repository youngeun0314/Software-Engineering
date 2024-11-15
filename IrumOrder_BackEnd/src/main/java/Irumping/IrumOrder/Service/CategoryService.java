package Irumping.IrumOrder.Service;

import Irumping.IrumOrder.Entity.CategoryEntity;
import Irumping.IrumOrder.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository repository;

    @Transactional
    public Long createCategory(String categoryName) {
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryName);
        repository.saveCategory(category);
        return category.getId();
    }

    public CategoryEntity findCategoryById(Long categoryId) {
        return repository.findCategoryById(categoryId);
    }

    @Transactional
    public Long deleteCategory(Long categoryId) {
        repository.deleteCategory(categoryId);
        return categoryId;
    }

    public List<CategoryEntity> findAll() {
        return repository.findAll();
    }
}
