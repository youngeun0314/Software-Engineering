package Irumping.IrumOrder.dto;

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
    @Schema(example="TC0ONETIME")
    String cid;
    String order_id;
    String user_id;
    String item_name;
    int quantity;
    @Positive
    @NotNull
    @Schema(minimum = "1")
    int totalPrice;
    int tax_free_amount;

}
