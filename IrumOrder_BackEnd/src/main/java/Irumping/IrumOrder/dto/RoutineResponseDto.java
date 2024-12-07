package Irumping.IrumOrder.dto;

import Irumping.IrumOrder.entity.RoutineDay;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

/**
 * 클래스 설명: 루틴 정보를 응답으로 제공하는 DTO 클래스.
 * 루틴 엔터티 데이터를 사용자 친화적인 형태로 변환한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineResponseDto {
    @Schema(description = "루틴 ID", example = "1")
    private Integer routineId;

    @Schema(description = "사용자 ID", example = "12345")
    private Integer userId;

    @Schema(description = "메뉴 ID", example = "10")
    private Integer menuId;

    @Schema(description = "메뉴명", example = "아이스 아메리카노")
    private String menuName;

    @Schema(description = "메뉴 세부 정보 ID", example = "5")
    private Integer menuDetailId;

    @Schema(description = "루틴이 실행될 요일", example = "[\"Mon\", \"Wed\", \"Fri\"]")
    private List<RoutineDay> routineDays;

    @Schema(description = "루틴이 실행될 시간", example = "09:00")
    private LocalTime routineTime;

    @Schema(description = "알람 활성화 여부", example = "true")
    private boolean isActivated;

}
