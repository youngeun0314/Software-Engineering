package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.entity.CategoryEntity;
import Irumping.IrumOrder.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @GetMapping("/getAllCategory")
//    public ResponseEntity<String> getAllCategory() {
//        return ResponseEntity.ok(categoryService.findAll().toString());
//    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<List<CategoryEntity>> getAllCategory() {
        List<CategoryEntity> categories = categoryService.findAll();  // 전체 카테고리 조회
        return ResponseEntity.ok(categories);  // List<CategoryEntity>를 JSON 형식으로 반환
    }

    @GetMapping("/getCategory")
    public ResponseEntity<String> getCategory(Long category) {
        return ResponseEntity.ok(categoryService.findCategoryById(category).toString());
    }

//    @GetMapping("/getCategory")
//    public ResponseEntity<CategoryEntity> getCategory(Long categoryId) {
//        CategoryEntity category = categoryService.findCategoryById(categoryId);  // 카테고리 조회
//        return ResponseEntity.ok(category);  // CategoryEntity를 JSON 형식으로 반환
//    }
}
