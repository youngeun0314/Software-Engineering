package Irumping.IrumOrder.service;

import Irumping.IrumOrder.controller.MenuRequest;
import Irumping.IrumOrder.entity.*;
import Irumping.IrumOrder.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * MenuService 테스트
 *
 * 작성자 : 주영은
 * 마지막 수정일 : 2024-12-09
 */
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMenu_shouldSaveMenuAndReturnMenuId() {
        // Given
        MenuRequest menuRequest = new MenuRequest();
        menuRequest.setName("Test Menu");
        menuRequest.setPrice(10000);
        menuRequest.setCategoryId(1);

        // Mocking: 카테고리 찾기
        CategoryEntity mockCategory = new CategoryEntity();
        mockCategory.setId(1);
        mockCategory.setName("Test Category");

        when(categoryRepository.findCategoryById(1)).thenReturn(mockCategory);

        // Mocking: MenuEntity 저장
        doAnswer(invocation -> {
            MenuEntity savedMenu = invocation.getArgument(0);
            savedMenu.setMenuId(1); // ID 설정
            return null;
        }).when(menuRepository).saveMenu(any(MenuEntity.class));

        // When
        Integer menuId = menuService.createMenu(menuRequest);

        // Then
        assertNotNull(menuId);
        assertEquals(1, menuId);

        verify(categoryRepository, times(1)).findCategoryById(1);
        verify(menuRepository, times(1)).saveMenu(any(MenuEntity.class));
    }

    @Test
    void deleteMenu_shouldCallDeleteMethod() {
        // Given
        doNothing().when(menuRepository).deleteMenu(1);

        // When
        Integer deletedMenuId = menuService.deleteMenu(1);

        // Then
        assertEquals(1, deletedMenuId);
        verify(menuRepository, times(1)).deleteMenu(1);
    }

    @Test
    void findMenuByCategory_shouldReturnMenuList() {
        // Given
        CategoryEntity mockCategory = new CategoryEntity();
        mockCategory.setId(1);
        mockCategory.setName("Test Category");

        MenuEntity mockMenu = new MenuEntity("Test Menu", 10000, mockCategory);
        mockMenu.setMenuId(1);

        when(menuRepository.findByCategory(1)).thenReturn(Collections.singletonList(mockMenu));

        // When
        List<MenuEntity> menus = menuService.findMenuByCategory(1);

        // Then
        assertNotNull(menus);
        assertEquals(1, menus.size());
        assertEquals("Test Menu", menus.get(0).getName());

        verify(menuRepository, times(1)).findByCategory(1);
    }

    @Test
    void findMenuByMenuId_shouldReturnMenuEntity() {
        // Given
        CategoryEntity mockCategory = new CategoryEntity();
        mockCategory.setId(1);
        mockCategory.setName("Test Category");

        MenuEntity mockMenu = new MenuEntity("Test Menu", 10000, mockCategory);
        mockMenu.setMenuId(1);

        when(menuRepository.findMenuById(1)).thenReturn(mockMenu);

        // When
        MenuEntity result = menuService.findMenuByMenuId(1);

        // Then
        assertNotNull(result);
        assertEquals("Test Menu", result.getName());
        assertEquals(10000, result.getPrice());

        verify(menuRepository, times(1)).findMenuById(1);
    }
}
