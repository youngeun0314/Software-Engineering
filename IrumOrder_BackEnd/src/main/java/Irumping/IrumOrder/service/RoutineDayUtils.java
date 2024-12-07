package Irumping.IrumOrder.service;

import Irumping.IrumOrder.entity.RoutineDay;

import java.util.ArrayList;
import java.util.List;

/**
 * 클래스 설명: 요일값을 비트 형태로 바꾸는 클래스
 * 리스트 형태로 받은 요일 값들을 월요일(LSB), 일요일 (MSB)로 하여
 * int형태로 변환하여 리턴
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-08
 */

public class RoutineDayUtils {

    // 리스트 -> int형 변환
    public static int toBitmask(List<RoutineDay> days) {
        int bitmask = 0;
        for (RoutineDay day : days) {
            bitmask |= (1 << day.ordinal());
        }
        return bitmask;
    }

    // int -> 리스트 변환
    public static List<RoutineDay> fromBitmask(int bitmask) {
        List<RoutineDay> days = new ArrayList<>();
        for (RoutineDay day : RoutineDay.values()) {
            if ((bitmask & (1 << day.ordinal())) != 0) {
                days.add(day);
            }
        }
        return days;
    }

    public static boolean isSpecificDay(int bitmask, RoutineDay day) {
        return (bitmask & (1 << day.ordinal())) != 0;
    }}
