package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Dto.MenuDetailDto;
import Irumping.IrumOrder.Dto.OrderMenuDto;
import Irumping.IrumOrder.Dto.OrderRequestDto;
import Irumping.IrumOrder.Dto.OrderResponseDto;
import Irumping.IrumOrder.Entity.MenuDetailEntity;
import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Entity.OrderStatus;
import Irumping.IrumOrder.Service.OrderService;
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

        return orderService.createOrder(orderRequestDto);
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

        // Enum을 String으로 변환
        responseDto.setOrderStatus(OrderStatus.valueOf(orderEntity.getOrderStatus().toString()));


        // LocalTime -> String 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        responseDto.setPickUp(orderEntity.getPickUp() != null ? orderEntity.getPickUp().format(formatter) : null);

        responseDto.setPayment(false); // 어차피 주문된거니까 모든 주문은 false

        // OrderMenuEntity 리스트를 OrderMenuDto 리스트로 변환
        responseDto.setOrderMenuOptions(orderEntity.getOrderMenuOptions()
                .stream()
                .map(orderMenuEntity -> {
                    OrderMenuDto orderMenuDto = new OrderMenuDto();
                    orderMenuDto.setMenuId(orderMenuEntity.getMenu().getMenuId());
                    orderMenuDto.setQuantity(orderMenuEntity.getQuantity());
                    // MenuDetailEntity를 MenuDetailDto로 변환
                    MenuDetailDto menuDetailDto = convertToMenuDetailDto(orderMenuEntity.getMenuDetail());
                    orderMenuDto.setMenuOptions(menuDetailDto);return orderMenuDto;
                }).collect(Collectors.toList())
        );

        return responseDto;
    }

    // Helper method to convert MenuDetailEntity to MenuDetailDto
    private MenuDetailDto convertToMenuDetailDto(MenuDetailEntity menuDetailEntity) {
        MenuDetailDto menuDetailDto = new MenuDetailDto();
        menuDetailDto.setUseCup(menuDetailEntity.getUseCup());
        menuDetailDto.setAddShot(menuDetailEntity.isAddShot()); // isAddShot() 사용
        menuDetailDto.setAddVanilla(menuDetailEntity.isAddVanilla()); // isAddVanilla() 사용
        menuDetailDto.setAddHazelnut(menuDetailEntity.isAddHazelnut()); // isAddHazelnut() 사용
        menuDetailDto.setLight(menuDetailEntity.isLight()); // isLight() 사용
        return menuDetailDto;
    }
}
