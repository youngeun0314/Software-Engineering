package Irumping.IrumOrder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "Request object for payment approval")
@Getter
@Setter
@ToString
public class PayApproveRequest {
    @Schema(description = "PG token for authorization, you can check it in form of ?pg_token=~~ in url", example = "abcdef123456")
    private String pg_token;

    @Schema(description = "User ID", example = "1")
    private Integer userId;

    @Schema(description = "Order ID", example = "1001")
    private int orderId;

    @Schema(description = "Merchant Code (Test)", example = "TC0ONETIME")
    private String cid;
}
