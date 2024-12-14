/**
 * 파일: RoutineContext.js
 * 설명: 루틴 데이터를 관리하기 위한 React Context를 제공하며, 사용자별 루틴 데이터를 상태로 저장
 * 작성자: 이희진
 * 마지막 수정일: 2024-12-8
 */

import React, { createContext, useState, useEffect } from "react";
import { getUserId } from "./userStorage"; // 사용자 ID를 가져오는 함수

// RoutineContext 생성
const RoutineContext = createContext();

/**
 * RoutineProvider 컴포넌트
 * 
 * @param children 자식 컴포넌트
 * @return 루틴 데이터와 상태 관리 기능을 제공하는 Context Provider
 */
export const RoutineProvider = ({ children }) => {
  const [routines, setRoutines] = useState([]); // 루틴 데이터 상태
  const user_id = getUserId(); // 현재 사용자 ID 가져오기

  useEffect(() => {
    if (!user_id) {
      // user_id가 없는 경우 오류 메시지 출력
      console.error("userId가 제공되지 않았습니다.");
      return;
    }

    /**
     * 사용자별 루틴 데이터를 API 호출로 가져오는 함수
     */
    const fetchRoutines = async () => {
      try {
        // 사용자 루틴 데이터 가져오기
        const response = await fetch(`http://localhost:8080/api/users/${user_id}/routines`);
        if (!response.ok) {
          throw new Error(`HTTP 오류: ${response.status}`); // HTTP 오류 처리
        }
        const data = await response.json(); // 서버에서 받은 루틴 데이터 파싱
        setRoutines(data); // 상태에 루틴 데이터 저장
      } catch (error) {
        // 루틴 데이터를 불러오는 중 오류 발생 시 처리
        console.error("루틴을 불러오는 데 실패했습니다.", error);
      }
    };

    fetchRoutines(); // API 호출 실행
  }, [user_id]);

  return (
    <RoutineContext.Provider value={{ routines, setRoutines }}>
      {/* 자식 컴포넌트에 루틴 데이터와 상태 관리 함수 제공 */}
      {children}
    </RoutineContext.Provider>
  );
};

export default RoutineContext;