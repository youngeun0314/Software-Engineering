package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.dto.*;
import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 클래스 설명: 주문 관리를 위한 컨트롤러.
 * 주문 생성, 조회와 같은 기능을 제공한다.
 *
 * 작성자: 김은지
 * 마지막 수정일: 2024-12-08
 */
@Tag(name = "Order Management", description = "API for managing orders")
@RestController
@RequestMapping("/orders/{userId}")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 새로운 주문을 생성하는 메서드.
     *
     * @param orderRequestDto 생성할 주문 정보
     * @return ResponseEntity<OrderResponseDto> 생성된 주문의 정보
     */
    @Operation(
            summary = "Create Order",
            description = "Creates a new order based on provided details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping("/order")
    public ResponseEntity<OrderResponseDto> create(
            @Parameter(description = "Details of the order to be created") @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDto);
    }

    /**
     * 특정 사용자의 모든 주문을 조회하는 메서드.
     *
     * @param userId 사용자 ID
     * @return ResponseEntity<List<OrdersCheckResponseDto>> 사용자의 모든 주문 정보
     */
    @Operation(
            summary = "Get all orders by user ID",
            description = "Fetches all orders associated with a specific user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched orders"),
                    @ApiResponse(responseCode = "404", description = "User or orders not found")
            }
    )
    @GetMapping("/orderCheck")
    public ResponseEntity<List<OrdersCheckResponseDto>> getAllOrdersByUserId(
            @Parameter(description = "ID of the user whose orders are to be fetched", example = "123")
            @RequestParam(name = "userId") Integer userId) {
        List<OrdersCheckResponseDto> responseDtos = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }
}
