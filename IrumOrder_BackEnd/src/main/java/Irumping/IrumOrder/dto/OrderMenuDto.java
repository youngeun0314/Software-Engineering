package Irumping.IrumOrder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMenuDto {

    @Schema(example="1")
    private int menuId;  // 메뉴 ID

    @Schema(example="2")
    private int quantity;  // 수량

    // 옵션을 그룹화하여 `menuDetailId`로 조회 후 해당 옵션에 쉽게 접근 가능
    private MenuDetailDto menuOptions;
}
