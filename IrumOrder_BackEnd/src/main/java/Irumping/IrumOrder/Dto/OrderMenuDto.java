package Irumping.IrumOrder.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMenuDto {
    private int menuId; // 메뉴 ID
    private int menuDetailId; // 메뉴 옵션 ID
    private int quantity; // 수량
}
