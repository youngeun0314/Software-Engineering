package Irumping.IrumOrder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "Response object for payment preparation")
@Getter
@Setter
@ToString
public class PayReadyResponse {
    @Schema(description = "Transaction ID", example = "TID123456789")
    private String tid;

    @Schema(description = "Redirect URL for PC", example = "https://pay.kakao.com/redirect")
    private String next_redirect_pc_url;

    @Schema(description = "Redirect URL for Mobile", example = "https://m.pay.kakao.com/redirect")
    private String next_redirect_mobile_url;
}

