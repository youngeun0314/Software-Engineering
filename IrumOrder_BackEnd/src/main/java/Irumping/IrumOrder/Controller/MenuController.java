package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Entity.MenuDetailEntity;
import Irumping.IrumOrder.Service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/createMenu")
    public ResponseEntity<String> addMenu(String menuName, Long price, Long categoryId, MenuDetailEntity menuDetail) {
        menuService.createMenu(menuName, price, categoryId, menuDetail);
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

