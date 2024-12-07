package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.MenuEntity;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {

    MenuEntity findMenuById(Integer menuId);

    // 이름으로 메뉴 검색
    Optional<MenuEntity> findByName(String name);

    // 카테고리에 따라 검색
    List<MenuEntity> findByCategory(Integer categoryId);

    public void saveMenu(MenuEntity menu);

    public void deleteMenu(Integer menuId);

}
