package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.entity.CategoryEntity;
import Irumping.IrumOrder.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 클래스 설명: 카테고리 관련 API를 제공하는 컨트롤러
 * 카테고리 추가, 삭제, 조회 기능을 제공
 *
 * 작성자: 주영은
 * 마지막 수정일: 2024-12-04
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 추가하는 메소드
     *
     * @param category 추가할 카테고리의 이름
     * @return ResponseEntity.ok("카테고리 추가 완료")
     */
    @Operation(
            summary = "카테고리 추가",
            description = "새로운 카테고리를 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "카테고리 추가 완료"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    @PostMapping("/createCategory")
    public ResponseEntity<String> createCategory(
            @Parameter(name = "category", description = "추가할 카테고리 이름", example = "Electronics")
            @RequestParam("category") String category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok("카테고리 추가 완료");
    }


    /**
     * 카테고리 삭제
     * 카테고리 ID로 카테고리를 삭제
     *
     * @param categoryId 삭제할 카테고리의 ID
     * @return ResponseEntity.ok("카테고리 삭제 완료")
     */
    @Operation(
            summary = "카테고리 삭제",
            description = "카테고리 ID로 카테고리를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "카테고리 삭제 완료"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    @DeleteMapping("/deleteCategory")
    public ResponseEntity<String> deleteCategory(
            @Parameter(name = "categoryId", description = "삭제할 카테고리의 ID", example = "1")
            @RequestParam("categoryId") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("카테고리 삭제 완료");
    }


    /**
     * 전체 카테고리 조회
     * 모든 카테고리를 조회합니다.
     *
     * @return ResponseEntity.ok(categories)
     */
    @Operation(
            summary = "전체 카테고리 조회",
            description = "모든 카테고리를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "전체 카테고리 조회 완료"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    @GetMapping("/getAllCategory")
    public ResponseEntity<List<CategoryEntity>> getAllCategory() {
        List<CategoryEntity> categories = categoryService.findAll();  // 전체 카테고리 조회
        return ResponseEntity.ok(categories);  // List<CategoryEntity>를 JSON 형식으로 반환
    }

    /**
     * 카테고리 조회
     * 카테고리 ID로 원하는 카테고리를 조회
     *
     * @param categoryId 조회할 카테고리의 ID
     * @return 조회된 카테고리(CategoryEntity)
     */
    @Operation(
            summary = "카테고리 조회",
            description = "카테고리 ID로 카테고리를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "카테고리 조회 완료"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
            }
    )
    @GetMapping("/getCategory")
    public ResponseEntity<CategoryEntity> getCategory(
            @Parameter(name = "categoryId", description = "조회할 카테고리의 ID", example = "1")
            @RequestParam("categoryId") Integer categoryId) {
        CategoryEntity category = categoryService.findCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }
}
