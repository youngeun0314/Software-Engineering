package Irumping.IrumOrder.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import nonapi.io.github.classgraph.json.Id;

public class RoutineEntity {

    private String userID;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routineID;
    private String routineDay;
    //private - time;
    private boolean offAlarm;
    private String menu;
    //private String option;
}
