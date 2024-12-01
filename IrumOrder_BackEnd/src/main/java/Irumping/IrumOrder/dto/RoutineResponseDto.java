package Irumping.IrumOrder.dto;

import Irumping.IrumOrder.entity.RoutineDay;
import Irumping.IrumOrder.entity.RoutineEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * 클래스 설명: 루틴 정보를 응답으로 제공하는 DTO 클래스.
 * 루틴 엔터티 데이터를 사용자 친화적인 형태로 변환한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineResponseDto {
    @Schema(description = "루틴 ID", example = "1")
    private int routineId;

    @Schema(description = "사용자 ID", example = "12345")
    private long userId;

    @Schema(description = "메뉴 ID", example = "10")
    private int menuId;

    @Schema(description = "메뉴 세부 정보 ID", example = "5")
    private int menuDetailId;

    @Schema(description = "루틴이 실행될 요일", example = "MONDAY")
    private RoutineDay routineDay;

    @Schema(description = "루틴이 실행될 시간", example = "09:00")
    private LocalTime routineTime;

    @Schema(description = "알람 활성화 여부", example = "true")
    private boolean isActivated;

    /**
     * 엔터티 데이터를 기반으로 응답 DTO를 생성하는 생성자.
     *
     * @param entity 루틴 엔터티
     */
    public RoutineResponseDto(RoutineEntity entity) {
        this.routineId = entity.getRoutineId();
        this.userId = entity.getUserId();
        this.menuId = entity.getMenuId();
        this.menuDetailId = entity.getMenuDetailId();
        this.routineDay = entity.getRoutineDay();
        this.routineTime = entity.getRoutineTime();
        this.isActivated = entity.getAlarmEnabled();
    }
}