package Irumping.IrumOrder.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMenuDto {

    private int menuId;  // 메뉴 ID
    private int quantity;  // 수량

    private int menuDetailId;  // 메뉴 세부 옵션 ID

    // 옵션을 그룹화하여 `menuDetailId`로 조회 후 해당 옵션에 쉽게 접근 가능
    private MenuDetailDto menuOptions;
}
