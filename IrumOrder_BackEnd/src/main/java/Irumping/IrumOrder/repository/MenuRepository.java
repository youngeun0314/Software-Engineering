package Irumping.IrumOrder.repository;

import Irumping.IrumOrder.entity.MenuEntity;

import java.util.List;
import java.util.Optional;

/**
 * 클래스 설명: 메뉴 정보를 DB에 저장하고 조회하는 인터페이스
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
public interface MenuRepository {

    MenuEntity findMenuById(Integer menuId);

    // 이름으로 메뉴 검색
    Optional<MenuEntity> findByName(String name);

    // 카테고리에 따라 검색
    List<MenuEntity> findByCategory(Integer categoryId);

    public void saveMenu(MenuEntity menu);

    public void deleteMenu(Integer menuId);

}
