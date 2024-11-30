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
    private int routineId; // 기본 키 설정

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "menu_id", nullable = false)
    private int menuId;

    @Column(name = "menu_detail_id", nullable = false)
    private int menuDetailId;

    @Enumerated(EnumType.STRING)
    @Column(name = "routine_day", nullable = false)
    private RoutineDay routineDay;

    @Column(name = "routine_time", nullable = false)
    private LocalTime routineTime;

    @Column(name = "is_activated", nullable = false)
    private Boolean alarmEnabled = true;
}