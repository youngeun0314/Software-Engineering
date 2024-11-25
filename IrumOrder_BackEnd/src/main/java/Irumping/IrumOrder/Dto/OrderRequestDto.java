package Irumping.IrumOrder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    private int userId;
    private int totalPrice;

    @Schema(example="01:20")
    private LocalTime pickUp;

//    private boolean payment = false; // 결제 상태, 기본값은 false
    private List<OrderMenuDto> orderMenuOptions;
}

