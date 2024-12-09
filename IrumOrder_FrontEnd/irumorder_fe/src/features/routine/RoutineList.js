import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import RoutineContext, { RoutineProvider } from "../../context/RoutineContext";
import { getUserId } from "../../context/userStorage";
import "./RoutineList.css";

const RoutineList = () => {
  const { routines, setRoutines } = useContext(RoutineContext);
  const navigate = useNavigate();
  const user_id = getUserId();

  const deleteRoutineToServer = async (routine) => {
    try {
      const method = "DELETE";
      const endpoint = `http://localhost:8080/api/users/${user_id}/routines/${routine.routineId}`;
  
      const response = await fetch(endpoint, {
        method,
        headers: { "Content-Type": "application/json" },
      });
  
      if (!response.ok) {
        // HTTP 오류 처리
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
      console.error("루틴 삭제 중 오류 발생:", error);
      alert(error.message); // 사용자에게 오류 메시지 표시
    }
  };
  

  return (
    <div className="routine-list">
      <header>
        <h1>나의 루틴</h1>
        <p onClick={() => navigate(`/main`)}>{"X"}</p>
      </header>

      {routines.length > 0 ? (
        <div className="routine-items">
          {routines.map((routine) => (
            <div className="routine-item" key={routine.routineId}>
              <div onClick={() => navigate(`/routine/${routine.routineId}`)}>
                <div className="routine-items-time">
                  <p>시간: {routine.routineTime}</p>
                </div>
                <div className="routine-item-others">
                  <p>
                    요일:{" "}
                    {routine.routineDays.map((day, index) => (
                      <span key={index}>{day + " "}</span>
                    ))}
                    || 메뉴: {routine.menuName}
                  </p>
                </div>
              </div>
              <div className="routine-item-delete">
                <p onClick={() => deleteRoutineToServer(routine)}>{"X"}</p>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p>루틴 개수 : {routines.length}개</p>
      )}

      <button
        className="item-add-button"
        onClick={() => navigate("/routine/new")}
      >
        +
      </button>
    </div>
  );
};

// RoutineContext를 제공하는 래퍼 컴포넌트
const RoutineListWrapper = ({ userId }) => {
  return (
    <RoutineProvider userId={userId}>
      <RoutineList userId={userId} />
    </RoutineProvider>
  );
};

export default RoutineListWrapper;