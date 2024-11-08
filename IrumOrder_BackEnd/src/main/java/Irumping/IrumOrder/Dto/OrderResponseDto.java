package Irumping.IrumOrder.Dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {

    private int orderId; // 주문 번호
    private int userId; // 주문한 유저의 ID
    private int totalPrice; // 주문 총 가격
    private String orderStatus; // 주문 상태
    private LocalTime pickUp; // 픽업 시간
    private boolean payment;
    private List<OrderMenuDto> orderMenuOptions; // 주문 내 메뉴와 옵션 목록
}
