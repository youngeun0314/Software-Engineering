package Irumping.IrumOrder.service;

import Irumping.IrumOrder.controller.MenuRequest;
import Irumping.IrumOrder.entity.CategoryEntity;
import Irumping.IrumOrder.entity.MenuEntity;
import Irumping.IrumOrder.repository.CategoryRepository;
import Irumping.IrumOrder.repository.MenuRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 클래스 설명: 메뉴 정보 관리를 제공하는 클래스
 * 메뉴 정보를 DB에 저장하고 조회한다.
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepositoy;

    @PersistenceContext
    private EntityManager em;

    /**
     * 메뉴 생성
     *
     * @param menuRequest 메뉴 정보
     * @return 저장된 메뉴의 ID
     */
    @Transactional
    public Integer createMenu(MenuRequest menuRequest) {

        // DB에서 카테고리 가져오기
        CategoryEntity category = categoryRepositoy.findCategoryById(menuRequest.getCategoryId());

        // 메뉴 엔티티 생성 및 카테고리 설정
        MenuEntity menu = new MenuEntity();
        menu.setName(menuRequest.getName());
        menu.setPrice(menuRequest.getPrice());
        menu.setCategory(category);

        // 메뉴 엔티티 저장
        menuRepository.saveMenu(menu);
        return menu.getMenuId();
    }

    /**
     * 메뉴 삭제
     * 메뉴 ID로 메뉴 정보를 삭제한다.
     *
     * @param menuId 삭제할 메뉴의 ID
     * @return 삭제된 메뉴의 ID
     */
    @Transactional
    public Integer deleteMenu(Integer menuId) {
        menuRepository.deleteMenu(menuId);
        return menuId;
    }

    /**
     * 메뉴 조회
     * 원하는 카테고리에 해당하는 메뉴 정보를 조회한다.
     *
     * @param categoryId 조회할 메뉴의 ID
     * @return 카테고리별 메뉴 정보 리스트
     */
    public List<MenuEntity> findMenuByCategory(Integer categoryId) {
        return menuRepository.findByCategory(categoryId);
    }

    /**
     * 메뉴 조회
     * 메뉴 ID로 해당 메뉴 정보를 조회한다.
     *
     * @param menuId 조회할 메뉴의 ID
     * @return 메뉴 정보
     */
    public MenuEntity findMenuByMenuId(Integer menuId) {
        return menuRepository.findMenuById(menuId);
    }

}
