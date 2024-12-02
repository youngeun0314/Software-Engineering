package Irumping.IrumOrder.controller;

import Irumping.IrumOrder.dto.MenuDetailDto;
import Irumping.IrumOrder.dto.OrderMenuDto;
import Irumping.IrumOrder.dto.OrderRequestDto;
import Irumping.IrumOrder.dto.OrderResponseDto;
import Irumping.IrumOrder.entity.MenuDetailEntity;
import Irumping.IrumOrder.entity.OrderEntity;
import Irumping.IrumOrder.entity.OrderStatus;
import Irumping.IrumOrder.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Tag(name = "Order Management", description = "API for managing orders")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 주문 생성
    @Operation(summary = "Create Order", description = "Creates a new order based on provided details.")
    @PostMapping
    public OrderResponseDto create(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponseDto;
        orderResponseDto = orderService.createOrder(orderRequestDto);
        return orderResponseDto;
    }


}
