package Irumping.IrumOrder.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDetailDto {

    private Boolean useCup;         // 컵 사용 여부
    private Boolean addShot;        // 샷 추가 여부
    private Boolean addVanilla;     // 바닐라 추가 여부
    private Boolean addHazelnut;    // 헤이즐넛 추가 여부
    private Boolean light;          // 라이트 옵션 여부
}