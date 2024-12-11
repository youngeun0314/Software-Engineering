/**
 * 파일: RoutineList.js
 * 설명: 사용자의 루틴 목록을 표시하며, 루틴 추가, 수정, 삭제를 관리하는 컴포넌트
 * 작성자: 이희진
 * 마지막 수정일: 2024-12-10
 */

import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import RoutineContext, { RoutineProvider } from "../../shared/context/RoutineContext";
import { getUserId } from "../../shared/context/userStorage";
import "./RoutineList.css";

const RoutineList = () => {
  const { routines, setRoutines } = useContext(RoutineContext); // 루틴 컨텍스트에서 데이터 및 상태 관리
  const navigate = useNavigate();
  const user_id = getUserId(); // 현재 로그인된 사용자 ID 가져오기

  /**
   * 서버에서 루틴 삭제
   *
   * @param routine 삭제할 루틴 객체
   */
  const deleteRoutineToServer = async (routine) => {
    try {
      const method = "DELETE"; // 삭제 HTTP 메서드 사용
      const endpoint = `http://localhost:8080/api/users/${user_id}/routines/${routine.routineId}`; // API 엔드포인트 정의
  
      const response = await fetch(endpoint, {
        method,
        headers: { "Content-Type": "application/json" },
      });
  
      if (!response.ok) {
        // 서버 응답이 성공적이지 않을 경우 오류 처리
        if (response.status === 404) {
          throw new Error("루틴을 찾을 수 없습니다.");
        } else if (response.status === 403) {
          throw new Error("삭제 권한이 없습니다.");
        } else {
          throw new Error(`HTTP 오류: ${response.status}`);
        }
      }
  
      // UI에서 삭제된 루틴 제거
      setRoutines((prev) =>
        prev.filter((r) => r.routineId !== routine.routineId)
      );
    } catch (error) {
      console.error("루틴 삭제 중 오류 발생:", error); // 오류 디버깅 로그
      alert(error.message); // 사용자에게 오류 메시지 표시
    }
  };

  return (
    <div className="routine-list">
      <header>
        <h1>나의 루틴</h1>
        <p onClick={() => navigate(`/main`)}>{"X"}</p> {/* 메인 페이지로 이동 */}
      </header>

      {routines.length > 0 ? (
        <div className="routine-items">
          {routines.map((routine) => (
            <div className="routine-item" key={routine.routineId}>
              {/* 루틴 상세 페이지로 이동 */}
              <div onClick={() => navigate(`/routine/${routine.routineId}`)}>
                <div className="routine-items-time">
                  <p>시간: {routine.routineTime}</p> {/* 루틴 시간 표시 */}
                </div>
                <div className="routine-item-others">
                  <p>
                    요일:{" "}
                    {routine.routineDays.map((day, index) => (
                      <span key={index}>{day + " "}</span> // 선택된 요일 표시
                    ))}
                    || 메뉴: {routine.menuName} {/* 선택된 메뉴 이름 */}
                  </p>
                </div>
              </div>
              <div className="routine-item-delete">
                {/* 루틴 삭제 */}
                <p onClick={() => deleteRoutineToServer(routine)}>{"X"}</p>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p>루틴 개수 : {routines.length}개</p> // 루틴이 없을 경우 표시
      )}

      {/* 새로운 루틴 추가 페이지로 이동 */}
      <button
        className="item-add-button"
        onClick={() => navigate("/routine/new")}
      >
        +
      </button>
    </div>
  );
};

/**
 * RoutineContext를 제공하는 래퍼 컴포넌트
 * @param userId 사용자 ID
 * @return RoutineList를 RoutineProvider로 감싼 컴포넌트
 */
const RoutineListWrapper = ({ userId }) => {
  return (
    <RoutineProvider userId={userId}>
      <RoutineList userId={userId} />
    </RoutineProvider>
  );
};

export default RoutineListWrapper;