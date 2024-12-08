package Irumping.IrumOrder.dto;

import Irumping.IrumOrder.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
public class OrdersCheckResponseDto {

    @Schema(example="1")
    private int orderId; // 주문 번호

    @Schema(example="1")
    private int userId; // 주문한 유저의 ID

    @Schema(example="3000")
    private int totalPrice; // 주문 총 가격

    @Schema(example="WAITING")
    private OrderStatus orderStatus; // 주문 상태

    private LocalTime pickUp; //픽업시간

    private List<OrderDto> orderMenuOptions; // 주문 내 메뉴와 옵션 목록

    public OrdersCheckResponseDto() {
    }
}
