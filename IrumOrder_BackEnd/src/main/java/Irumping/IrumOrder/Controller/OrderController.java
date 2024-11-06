package Irumping.IrumOrder.Controller;

import Irumping.IrumOrder.Dto.OrderMenuDto;
import Irumping.IrumOrder.Dto.OrderRequestDto;
import Irumping.IrumOrder.Dto.OrderResponseDto;
import Irumping.IrumOrder.Entity.OrderEntity;
import Irumping.IrumOrder.Service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "order")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 주문 생성 엔드포인트
     *
     * @param orderRequestDto 클라이언트로부터 받은 주문 요청 데이터
     * @return 생성된 주문 정보 (OrderResponseDto)
     */
    @PostMapping("/orders/api")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        try {
            // 서비스에서 주문 생성
            OrderEntity createdOrder = orderService.createOrder(orderRequestDto);

            // 생성된 주문을 OrderResponseDto로 변환
            OrderResponseDto responseDto = convertToResponseDto(createdOrder);

            // 성공적으로 생성된 경우, 201 Created 상태 코드와 함께 응답
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

        } catch (Exception e) {
            // 예외 발생 시, 500 Internal Server Error와 함께 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * OrderEntity를 OrderResponseDto로 변환하는 메서드
     *
     * @param orderEntity 변환할 OrderEntity
     * @return OrderResponseDto 변환된 주문 응답 데이터
     */
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

        // 주문 메뉴 항목 변환
        List<OrderMenuDto> orderMenuDtos = orderEntity.getOrderMenuOptions()
                .stream()
                .map(orderMenuEntity -> {
                    OrderMenuDto orderMenuDto = new OrderMenuDto();
                    orderMenuDto.setMenuId(orderMenuEntity.getMenu().getMenuId());
                    orderMenuDto.setMenuDetailId(orderMenuEntity.getMenuDetail() != null
                            ? orderMenuEntity.getMenuDetail().getMenuDetailId() : 0);
                    orderMenuDto.setQuantity(orderMenuEntity.getQuantity());
                    return orderMenuDto;
                })
                .collect(Collectors.toList());

        responseDto.setOrderMenuOptions(orderMenuDtos);
        return responseDto;
    }
}
