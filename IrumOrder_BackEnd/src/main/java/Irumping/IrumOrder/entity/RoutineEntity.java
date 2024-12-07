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

/**
 * 클래스 설명: 사용자 루틴 정보를 저장하는 JPA 엔터티 클래스.
 * 데이터베이스의 `routine` 테이블과 매핑되며, 루틴의 속성을 정의한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-02
 */
@Entity
@Table(name = "routine")
@Getter
@Setter
public class RoutineEntity {

    //루틴 ID: 엔터티의 기본 키. db에서 자동 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routine_id")
    private Integer routineId; // 기본 키 설정

    //사용자 ID: 루틴을 소유한 사용자 식별자.
    @Column(name = "user_id")
    private Integer userId;

    //메뉴 ID: 루틴에 연관된 기본 메뉴 식별자.
    @Column(name = "menu_id", nullable = false)
    private Integer menuId;

    //메뉴 세부 정보 ID: 루틴에 연관된 메뉴의 세부 정보 식별자.
    @Column(name = "menu_detail_id", nullable = false)
    private Integer menuDetailId;

    //루틴 실행 요일: 루틴이 실행되는 요일.
    @Enumerated(EnumType.STRING)
    @Column(name = "routine_day", nullable = false)
    private RoutineDay routineDay;

    //루틴 실행 시간: 루틴이 실행되는 시간.
    @Column(name = "routine_time", nullable = false)
    private LocalTime routineTime;

    //알람 활성화 여부: 루틴 실행 시 알람을 활성화할지 여부. 기본값 true
    @Column(name = "is_activated", nullable = false)
    private Boolean alarmEnabled = true;
}