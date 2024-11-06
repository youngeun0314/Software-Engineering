package Irumping.IrumOrder.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayOrderForm {

    String menuName;
    @Positive
    @NotNull
    @Schema(minimum = "1")
    int totalPrice;
    @NotNull
    String user_id;

    public PayOrderForm(String menuName, int totalPrice, String user_id){
        this.menuName=menuName;
        this.totalPrice=totalPrice;
        this.user_id = user_id;
    }
}
