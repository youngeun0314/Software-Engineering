package Irumping.IrumOrder.Dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    private int userId;
    private int totalPrice;
    private LocalTime pickUp;
    private boolean payment = false; // 결제 상태, 기본값은 false
    private List<OrderMenuDto> orderMenuOptions;
}

