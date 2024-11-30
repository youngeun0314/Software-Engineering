package Irumping.IrumOrder.dto;

import Irumping.IrumOrder.entity.RoutineDay;
import Irumping.IrumOrder.entity.RoutineEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineResponseDto {
    private int routineId;
    private long userId;
    private int menuId;
    private int menuDetailId;
    private RoutineDay routineDay;
    private LocalTime routineTime;
    private boolean isActivated;

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