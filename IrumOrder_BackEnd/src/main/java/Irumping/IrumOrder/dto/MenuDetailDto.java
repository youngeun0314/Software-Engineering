package Irumping.IrumOrder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuDetailDto {

    @Schema(example="TakeOut")
    private String useCup;     // 컵 사용 여부

    @Schema(example="true")
    private Boolean addShot;        // 샷 추가 여부

    @Schema(example="false")
    private Boolean addVanilla;     // 바닐라 추가 여부

    @Schema(example="true")
    private Boolean addHazelnut;    // 헤이즐넛 추가 여부

    @Schema(example="true")
    private Boolean light;          // 라이트 옵션 여부
}