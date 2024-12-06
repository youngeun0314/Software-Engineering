import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import RoutineContext, { RoutineProvider } from "../../context/RoutineContext";
import { getUserId } from "../../context/userStorage";
import "./RoutineList.css";

const RoutineList = () => {
  const { routines, setRoutines } = useContext(RoutineContext);
  const navigate = useNavigate();
  const user_id = getUserId()

  const deleteRoutineToServer = async (routine) => {
    try {
      const method = "DELETE";
      const endpoint = `http://localhost:8080/api/users/${user_id}/routines/${routine.routineId}`;

      const response = await fetch(endpoint, {
        method,
        headers: { "Content-Type": "application/json" },
      });

      if (!response.ok) {
        throw new Error(`HTTP 오류: ${response.status}`);
      }

      setRoutines((prev) => prev.filter((r) => r.routineId !== routine.routineId)); 

      navigate("/routinelist");
    } catch (error) {
      console.error("루틴 삭제 중 오류 발생:", error);
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
                  요일: {routine.routineDays.map((day, index) => <span key={index}>{day+" "}</span>)}    
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
        <p>데이터를 불러오는 중입니다...</p>
      )}

      <button className="item-add-button" onClick={() => navigate("/routine/new")}>
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
