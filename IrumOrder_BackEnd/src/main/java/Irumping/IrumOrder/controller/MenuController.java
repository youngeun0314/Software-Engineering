package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.entity.MenuDetailEntity;
import Irumping.IrumOrder.entity.MenuEntity;
import Irumping.IrumOrder.service.CategoryService;
import Irumping.IrumOrder.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;
    private final CategoryService categoryService;

    @PostMapping("/createMenu")
    public ResponseEntity<String> createMenu(MenuRequest menuRequest) {
        menuService.createMenu(menuRequest);
        return ResponseEntity.ok("메뉴 추가 완료");
    }
//    public ResponseEntity<String> addMenu(MenuRequest menuRequest) {
//        MenuEntity menu = new MenuEntity();
//        menu.setName(menuRequest.getName());
//        menu.setPrice(menuRequest.getPrice());
//        menu.setCategoryId(menuRequest.getCategoryId());
////        menu.setCategoryId(categoryService.findCategoryById(menuRequest.getCategoryId()));
//        MenuDetailEntity menuDetail = new MenuDetailEntity();
//        menuDetail.setUseCup(menuRequest.getUseCup());
//        menuDetail.setAddShot(menuRequest.isAddShot());
//        menuDetail.setAddVanilla(menuRequest.isAddVanilla());
//        menuDetail.setAddHazelnut(menuRequest.isAddHazelnut());
//        menuDetail.setLight(menuRequest.isLight());
//        return ResponseEntity.ok("메뉴 추가 완료");
//    }

    @DeleteMapping("/deleteMenu")
    public ResponseEntity<String> deleteMenu(Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok("메뉴 삭제 완료");
    }

//    @GetMapping("/getMenu")
//    public ResponseEntity<String> getMenu(Long categoryId) {  // 메뉴를 카테고리ID로 검색
//        return ResponseEntity.ok(menuService.findMenuByCategory(categoryId).toString());
//    }

    @GetMapping("/getMenu")
    public ResponseEntity<List<MenuEntity>> getMenu(Long categoryId) {
        List<MenuEntity> menus = (List<MenuEntity>) menuService.findMenuByCategory(categoryId);
        return ResponseEntity.ok(menus);
    }


}

