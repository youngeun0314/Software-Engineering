package Irumping.IrumOrder.dto;

import Irumping.IrumOrder.entity.RoutineDay;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

/**
 * 클래스 설명: 루틴 생성 요청을 처리하는 DTO 클래스.
 * 사용자가 제공한 데이터를 기반으로 루틴을 생성한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-02
 */
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class RoutineDto {
    @NotNull
    @Schema(description = "사용자 ID, long", example = "12345")
    private long userId;

    @NotNull
    @Schema(description = "메뉴 ID", example = "10")
    private Integer menuId;

    @Schema(description = "메뉴 세부 정보 ID", example = "5")
    private Integer menuDetailId;

    @NotNull
    @Schema(description = "루틴이 실행될 요일", example = "MONDAY")
    private RoutineDay routineDay;

    @NotNull
    @Schema(description = "루틴이 실행될 시간", example = "09:00")
    private LocalTime routineTime;

    @NotNull
    @Schema(description = "알람 활성화 여부", example = "true")
    private Boolean isActivated;
}
