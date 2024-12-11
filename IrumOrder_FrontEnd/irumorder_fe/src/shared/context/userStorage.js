/**
 * 파일: userStorage.js
 * 설명: 애플리케이션 내에서 현재 사용자 ID를 관리하는 유틸리티 모듈
 * 작성자: 이희진
 * 마지막 수정일: 2024-12-5
 */

let currentUserId = null; // 현재 사용자 ID를 저장하는 전역 변수

/**
 * 사용자 ID 설정
 *
 * @param id 저장할 사용자 ID
 * @return void
 */
export const setUserId = (id) => {
  currentUserId = id; // 사용자 ID 저장
};

/**
 * 사용자 ID 가져오기
 *
 * @return 현재 저장된 사용자 ID
 */
export const getUserId = () => {
  return currentUserId; // 현재 사용자 ID 반환
};

/**
 * 사용자 ID 초기화
 *
 * @return void
 */
export const clearUserId = () => {
  currentUserId = null; // 사용자 ID 초기화
};