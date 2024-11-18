package Irumping.IrumOrder.dto;

import Irumping.IrumOrder.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {

    private int orderId; // 주문 번호
    private int userId; // 주문한 유저의 ID
    private int totalPrice; // 주문 총 가격
    private OrderStatus orderStatus; // 주문 상태
    private String pickUp; //픽업시간
    private LocalDateTime payment = null;
    private List<OrderMenuDto> orderMenuOptions; // 주문 내 메뉴와 옵션 목록
}
