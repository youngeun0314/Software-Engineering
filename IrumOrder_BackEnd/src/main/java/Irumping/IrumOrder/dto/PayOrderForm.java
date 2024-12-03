package Irumping.IrumOrder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "Request object for preparing a payment")
@Getter
@Setter
@ToString
public class PayOrderForm {
    @Schema(example="TC0ONETIME", description = "Merchant Code (Test)")
    private String cid;

    @Schema(example = "1001", description = "Order ID")
    private Integer order_id;

    @Schema(example = "12345", description = "User ID")
    private Long user_id;

    @Schema(example = "Product Name", description = "Name of the product")
    private String item_name;

    @Schema(example = "2", description = "Quantity of the product")
    private int quantity;

    @Positive
    @NotNull
    @Schema(example = "20000", description = "Total price of the product")
    private int totalPrice;

    @Schema(example = "0", description = "Tax-free amount")
    private int tax_free_amount;

}
