package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/addCategory")
    public ResponseEntity<String> addCategory(String category) {
//        categoryService.addCategory(category);
        return ResponseEntity.ok("카테고리 추가 완료");
    }

    @GetMapping("/deleteCategory")
    public ResponseEntity<String> deleteCategory(String category) {
//        categoryService.deleteCategory(category);
        return ResponseEntity.ok("카테고리 삭제 완료");
    }

    @GetMapping("/updateCategory")
    public ResponseEntity<String> updateCategory(String category) {
//        categoryService.updateCategory(category);
        return ResponseEntity.ok("카테고리 수정 완료");
    }

    @GetMapping("/getCategory")
    public ResponseEntity<String> getCategory(String category) {
//        categoryService.getCategory(category);
        return ResponseEntity.ok("카테고리 조회 완료");
    }

    // 메뉴 등록 추가 삭제 구현하기
}
