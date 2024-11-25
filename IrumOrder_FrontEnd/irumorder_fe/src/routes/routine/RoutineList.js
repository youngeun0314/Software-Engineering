import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import RoutineContext from "../context/RoutineContext";
import "./RoutineList.css";

const RoutineList = () => {
  const { routines } = useContext(RoutineContext);
  const navigate = useNavigate();

  const handleBackClick = () => {
    navigate(-1);  // 이전 페이지로 이동
  };

  return (
    <div className="routine-list">
      <header className='signup-header'>
        <button onClick={handleBackClick} className='back-button'>
          {'ᐊ'}
        </button>
      </header>
      <h1>나의 루틴</h1>
      <div className="routine-items">
        {routines.map((routine) => (
          <div
            key={routine.id}
            className="routine-item"
            onClick={() => navigate(`/routine/${routine.id}`)}
          >
            <p>
              시간: {routine.time} ({routine.days.join(", ")})
            </p>
            <p>메뉴: {routine.menu?.name || "없음"}</p>
            <p>매장: {routine.store}</p>
          </div>
        ))}
      </div>
      <button onClick={() => navigate("/routine/new")}>새 루틴 추가</button>
    </div>
  );
};

export default RoutineList;