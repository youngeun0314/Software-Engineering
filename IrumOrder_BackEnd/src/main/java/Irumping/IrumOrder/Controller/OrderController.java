package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Dto.OrderMenuDto;
import Irumping.IrumOrder.Dto.OrderRequestDto;
import Irumping.IrumOrder.Dto.OrderResponseDto;
import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        OrderEntity createdOrder = orderService.createOrder(orderRequestDto);
        return convertToResponseDto(createdOrder);
    }

    // Helper method to convert OrderEntity to OrderResponseDto
    private OrderResponseDto convertToResponseDto(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }

        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(orderEntity.getOrderId());
        responseDto.setUserId(orderEntity.getUserId());
        responseDto.setTotalPrice(orderEntity.getTotalPrice());
        responseDto.setOrderStatus(orderEntity.getOrderStatus());
        responseDto.setPickUp(orderEntity.getPickUp());
        responseDto.setPayment(false); //어차피 주문된거니까 모든 주문은 false
        responseDto.setOrderMenuOptions(orderEntity.getOrderMenuOptions()
                .stream()
                .map(orderMenuEntity -> {
                    OrderMenuDto orderMenuDto = new OrderMenuDto();
                    orderMenuDto.setMenuId(orderMenuEntity.getMenu().getMenuId());
                    orderMenuDto.setMenuDetailId(orderMenuEntity.getMenuDetail() != null ? orderMenuEntity.getMenuDetail().getMenuDetailId() : 0);
                    orderMenuDto.setQuantity(orderMenuEntity.getQuantity());
                    return orderMenuDto;
                }).collect(Collectors.toList())
        );
        return responseDto;
    }
}
