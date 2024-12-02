package Irumping.IrumOrder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import java.time.LocalTime;

@Entity
@Table(name = "routine")
@Getter
@Setter
public class RoutineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Integer routineId; // 기본 키 설정

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "menu_id", nullable = false)
    private Integer menuId;

<<<<<<< HEAD
    @Column(name = "menu_detail_id")
=======
    @Column(name = "menuDetail_id")
>>>>>>> 7f7bb4bb8ba8586161cc4e94df55a95add8ad11c
    private Integer menuDetailId;

    @Enumerated(EnumType.STRING)
    @Column(name = "routine_day", nullable = false)
    private RoutineDay routineDay;

    @Column(name = "routine_time", nullable = false)
    private LocalTime routineTime;

<<<<<<< HEAD
=======

>>>>>>> 7f7bb4bb8ba8586161cc4e94df55a95add8ad11c
    @Column(name = "is_activated", nullable = false)
    private Boolean alarmEnabled = true;
}