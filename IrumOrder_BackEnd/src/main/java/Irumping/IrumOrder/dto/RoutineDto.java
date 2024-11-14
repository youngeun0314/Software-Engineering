package Irumping.IrumOrder.dto;

import Irumping.IrumOrder.entity.RoutineDay;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
public class RoutineDto {
    private Integer menuId;
    private Integer menuDetailId;
    private RoutineDay routineDay;
    private LocalTime routineTime;
    private Boolean alarmEnabled;
}
