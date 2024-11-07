package Irumping.IrumOrder.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoutineDto {
    @NotNull
    private String userID;
    private String[] routineDays;
    private String time;
    private boolean offAlarm;
    private String menu;
    private String options;
}
