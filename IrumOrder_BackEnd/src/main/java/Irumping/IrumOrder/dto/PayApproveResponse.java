package Irumping.IrumOrder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "Response object for payment approval")
@Getter
@Setter
@ToString
public class PayApproveResponse {
    @Schema(description = "Request unique ID", example = "AID123456789")
    private String aid;

    @Schema(description = "Transaction ID", example = "TID123456789")
    private String tid;

    @Schema(description = "Merchant Code", example = "TC0ONETIME")
    private String cid;

    @Schema(description = "Merchant Order ID", example = "1001")
    private String partner_order_id;

    @Schema(description = "Merchant User ID", example = "12345")
    private String partner_user_id;

    @Schema(description = "Payment method type", example = "CARD")
    private String payment_method_type;

    @Schema(description = "Product name", example = "Product Name")
    private String item_name;

    @Schema(description = "Product code", example = "P001")
    private String item_code;

    @Schema(description = "Quantity of the product", example = "2")
    private String quantity;

    @Schema(description = "Request creation timestamp", example = "2023-12-01T12:00:00")
    private String created_at;

    @Schema(description = "Payment approval timestamp", example = "2023-12-01T12:10:00")
    private String approved_at;

    @Schema(description = "Payload associated with the approval", example = "Additional info")
    private String payload;
}

