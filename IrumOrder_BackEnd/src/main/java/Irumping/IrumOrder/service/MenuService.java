package Irumping.IrumOrder.service;

import Irumping.IrumOrder.entity.MenuDetailEntity;
import Irumping.IrumOrder.entity.MenuEntity;
import Irumping.IrumOrder.repository.CategoryRepository;
import Irumping.IrumOrder.repository.MenuDetailRepository;
import Irumping.IrumOrder.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuDetailRepository menuDetailRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long createMenu(MenuEntity menu, MenuDetailEntity menuDetail) {
        menuRepository.saveMenu(menu);
        menuDetail.setMenu(menu);
        menuDetailRepository.save(menuDetail);
        return menu.getMenuId();
    }

    @Transactional
    public Long deleteMenu(Long menuId) {
        menuRepository.deleteMenu(menuId);
        return menuId;
    }

    public MenuEntity findMenuByCategory(Long categoryId) {
        return menuRepository.findMenuById(categoryId);
    }


}
