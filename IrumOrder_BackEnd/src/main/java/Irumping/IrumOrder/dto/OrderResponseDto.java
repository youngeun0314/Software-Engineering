package Irumping.IrumOrder.dto;

import Irumping.IrumOrder.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {

    @Schema(example="1")
    private Integer orderId; // 주문 번호

    @Schema(example="1")
    private Integer userId; // 주문한 유저의 ID

    @Schema(example="3000")
    private Integer totalPrice; // 주문 총 가격

    @Schema(example="WAITING")
    private OrderStatus orderStatus; // 주문 상태

    private LocalTime pickUp; //픽업시간


    private LocalDateTime payment = null;
    private List<OrderMenuDto> orderMenuOptions; // 주문 내 메뉴와 옵션 목록
}
