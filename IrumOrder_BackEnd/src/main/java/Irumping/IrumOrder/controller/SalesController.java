package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.dto.SalesResponse;
import Irumping.IrumOrder.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
/**
 * 클래스 설명: 매출 정보를 관리하고 조회하는 컨트롤러 클래스.
 * 날짜와 사용자 ID를 기반으로 매출 데이터를 조회한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-01
 */

@Slf4j
@RestController
public class SalesController {
    @Autowired
    private SalesService salesService;

    /**
     * 어제의 매출 데이터를 조회하는 메서드.
     * 특정 날짜와 사용자 ID를 기반으로 매출 데이터를 조회.
     *
     * @param date 조회할 날짜 (yyyy-MM-dd 형식)
     * @param userId 사용자 ID
     * @return SalesResponse 총 매출 데이터를 담은 응답 객체
     * @throws IllegalAccessException 접근 권한이 없는 경우 발생
     */
    @Operation(
            summary = "Get Yesterday's Sales",
            description = "Fetches the total sales data for a specific date and user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved sales data"),
                    @ApiResponse(responseCode = "400", description = "Invalid input parameters"),
                    @ApiResponse(responseCode = "403", description = "User not authorized to access this data"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/api/sales")
    public ResponseEntity<SalesResponse> getYesterdaySales(@Parameter(description = "The date for which to fetch sales data (yyyy-MM-dd)", example = "2023-11-30")
                                                               @RequestParam LocalDate date,

                                                           @Parameter(description = "The user ID requesting the data", example = "12345")
                                                               @RequestParam Integer userId
    )throws IllegalAccessException {
        log.info("Fetching sales for date: {} and userId: {}", date, userId);
        int totalSales = salesService.getTotalSalesByDate(date, userId);
        return ResponseEntity.status(HttpStatus.OK).body(new SalesResponse(totalSales));
    }
}
