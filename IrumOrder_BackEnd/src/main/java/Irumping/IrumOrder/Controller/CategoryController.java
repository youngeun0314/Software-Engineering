package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<String> createCategory(String category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok("카테고리 추가 완료");
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<String> deleteCategory(Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("카테고리 삭제 완료");
    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<String> getAllCategory() {
        return ResponseEntity.ok(categoryService.findAll().toString());
    }

    @GetMapping("/getCategory")
    public ResponseEntity<String> getCategory(Long category) {
        return ResponseEntity.ok(categoryService.findCategoryById(category).toString());
    }
}
