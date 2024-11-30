package Irumping.IrumOrder.service;

import Irumping.IrumOrder.controller.MenuRequest;
import Irumping.IrumOrder.entity.CategoryEntity;
import Irumping.IrumOrder.entity.MenuDetailEntity;
import Irumping.IrumOrder.entity.MenuEntity;
import Irumping.IrumOrder.repository.CategoryRepository;
import Irumping.IrumOrder.repository.MenuDetailRepository;
import Irumping.IrumOrder.repository.MenuRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepositoy;

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public Long createMenu(MenuRequest menuRequest) {
//        // 프록시 객체 생성
//        CategoryEntity category = new CategoryEntity();
//        em.getReference(CategoryEntity.class, menuRequest.getCategoryId());
//
//        // Menu 엔티티 생성 및 연관 관계 설정
//        MenuEntity newMenu = new MenuEntity();
//        newMenu.setName(menuRequest.getName());
//        newMenu.setPrice(menuRequest.getPrice());
//        newMenu.setCategory(category);
//
//        // Menu 저장
//        menuRepository.saveMenu(newMenu);
//        return newMenu.getMenuId();

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

    @Transactional
    public Long deleteMenu(Long menuId) {
        menuRepository.deleteMenu(menuId);
        return menuId;
    }

    public List<MenuEntity> findMenuByCategory(Long categoryId) {
        return menuRepository.findByCategory(categoryId);
    }

}
