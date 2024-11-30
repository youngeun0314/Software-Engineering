package Irumping.IrumOrder.dto;

import Irumping.IrumOrder.entity.RoutineDay;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class RoutineDto {
    @NotNull
    private long userId;

    @NotNull
    private Integer menuId;


    private Integer menuDetailId;

    @NotNull
    private RoutineDay routineDay;

    @NotNull
    @Schema(example="09:00")
    private LocalTime routineTime;

    @NotNull
    private Boolean isActivated;
}
