package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Entity.MenuDetailEntity;
import Irumping.IrumOrder.Entity.MenuEntity;
import Irumping.IrumOrder.Service.CategoryService;
import Irumping.IrumOrder.Service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;
    private final CategoryService categoryService;

    @PostMapping("/createMenu")
    public ResponseEntity<String> addMenu(MenuRequest menuRequest) {
        MenuEntity menu = new MenuEntity();
        menu.setName(menuRequest.getName());
        menu.setPrice(menuRequest.getPrice());
        menu.setCategory(categoryService.findCategoryById(menuRequest.getCategoryId()));
        MenuDetailEntity menuDetail = new MenuDetailEntity();
        menuDetail.setUseCup(menuRequest.getUseCup());
        menuDetail.setAddShot(menuRequest.isAddShot());
        menuDetail.setAddVanilla(menuRequest.isAddVanilla());
        menuDetail.setAddHazelnut(menuRequest.isAddHazelnut());
        menuDetail.setLight(menuRequest.isLight());
        menuService.createMenu(menu, menuDetail);
        return ResponseEntity.ok("메뉴 추가 완료");
    }

    @DeleteMapping("/deleteMenu")
    public ResponseEntity<String> deleteMenu(Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok("메뉴 삭제 완료");
    }

    @GetMapping("/getMenu")
    public ResponseEntity<String> getMenu(Long categoryId) {  // 메뉴를 카테고리ID로 검색
        return ResponseEntity.ok(menuService.findMenuByCategory(categoryId).toString());
    }


}

