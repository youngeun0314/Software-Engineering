package Irumping.IrumOrder.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    private int userId;
    private int totalPrice;

    private String pickUp;

//    private boolean payment = false; // 결제 상태, 기본값은 false
    private List<OrderMenuDto> orderMenuOptions;
}

