package Irumping.IrumOrder.service;

import Irumping.IrumOrder.entity.CategoryEntity;
import Irumping.IrumOrder.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * CategoryService 테스트
 *
 * 작성자 : 주영은
 * 마지막 수정일 : 2024-12-09
 */
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCategory_shouldSaveCategoryAndReturnId() {
        // Given
        CategoryEntity category = new CategoryEntity();
        category.setName("Electronics");
        doAnswer(invocation -> {
            CategoryEntity savedCategory = invocation.getArgument(0);
            savedCategory.setId(1); // Mock 저장 후 ID 설정
            return null;
        }).when(categoryRepository).saveCategory(any(CategoryEntity.class));

        // When
        Integer categoryId = categoryService.createCategory("Electronics");

        // Then
        assertNotNull(categoryId);
        assertEquals(1, categoryId);
        verify(categoryRepository, times(1)).saveCategory(any(CategoryEntity.class));
    }

    @Test
    void findCategoryById_shouldReturnCategory() {
        // Given
        Integer categoryId = 1;
        CategoryEntity category = new CategoryEntity();
        category.setId(1);
        category.setName("Electronics");
        when(categoryRepository.findCategoryById(categoryId)).thenReturn(category);

        // When
        CategoryEntity result = categoryService.findCategoryById(categoryId);

        // Then
        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        verify(categoryRepository, times(1)).findCategoryById(categoryId);
    }

    @Test
    void deleteCategory_shouldCallDeleteMethod() {
        // Given
        Integer categoryId = 1;
        doNothing().when(categoryRepository).deleteCategory(categoryId);

        // When
        Integer result = categoryService.deleteCategory(categoryId);

        // Then
        assertEquals(1, result);
        verify(categoryRepository, times(1)).deleteCategory(categoryId);
    }

    @Test
    void findAll_shouldReturnCategoryList() {
        // Given
        CategoryEntity category1 = new CategoryEntity();
        category1.setId(1);
        category1.setName("Electronics");

        CategoryEntity category2 = new CategoryEntity();
        category2.setId(2);
        category2.setName("Books");

        List<CategoryEntity> categories = Arrays.asList(category1, category2);
        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<CategoryEntity> result = categoryService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }
}
