package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.dto.*;
import Irumping.IrumOrder.entity.MenuDetailEntity;
import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Order Management", description = "API for managing orders")
@RestController
@RequestMapping("/orders/{userId}")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 주문 생성
    @Operation(summary = "Create Order", description = "Creates a new order based on provided details.")
    @PostMapping("/order")
    public OrderResponseDto create(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponseDto;
        orderResponseDto = orderService.createOrder(orderRequestDto);
        return orderResponseDto;
    }

    @GetMapping("/orderCheck")
    public ResponseEntity<List<OrdersCheckResponseDto>> getAllOrdersByUserId(@RequestParam(name="userId") Integer userId) {
        List<OrdersCheckResponseDto> responseDtos = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

}
