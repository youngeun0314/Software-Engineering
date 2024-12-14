/**
 * 파일: menuRoutineState.js
 * 설명: 메뉴와 루틴 상태를 관리하기 위한 전역 상태 변수 및 관련 함수 제공
 * 작성자: 이희진
 * 마지막 수정일: 2024-12-9
 */

// 현재 선택된 메뉴 ID를 저장하는 변수
let currentMenuIn = null; 

// 루틴 상태를 저장하는 변수
let routineState = null; 

/**
 * 메뉴 ID 설정 함수
 *
 * @param in_menu 설정할 메뉴 ID
 * @return void
 */
export const setMenuIn = (in_menu) => {
  currentMenuIn = in_menu; // 메뉴 ID 업데이트
};

/**
 * 현재 메뉴 ID 반환 함수
 *
 * @return {any} currentMenuIn 현재 저장된 메뉴 ID
 */
export const getMenuIn = () => {
  return currentMenuIn; // 저장된 메뉴 ID 반환
};

/**
 * 메뉴 ID 초기화 함수
 *
 * @return void
 */
export const clearMenuIn = () => {
  currentMenuIn = null; // 메뉴 ID 초기화
};

/**
 * 루틴 상태 설정 함수
 *
 * @param state 설정할 루틴 상태
 * @return void
 */
export const setRoutineState = (state) => {
  routineState = state; // 루틴 상태 업데이트
};

/**
 * 현재 루틴 상태 반환 함수
 *
 * @return {any} routineState 현재 저장된 루틴 상태
 */
export const getRoutineState = () => {
  return routineState; // 저장된 루틴 상태 반환
};

/**
 * 루틴 상태 초기화 함수
 *
 * @return void
 */
export const clearRoutineState = () => {
  routineState = null; // 루틴 상태 초기화
};