import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from 'react-router-dom';
import RoutineContext from "../../context/RoutineContext";
import "./RoutineList.css";

const RoutineList = () => {
  const { routines } = useContext(RoutineContext);
  const navigate = useNavigate();

  return (
    <div className="routine-list">
      <header className='signup-header'>
        <Link to={'/main'}>
          <button>{'ᐊ'}</button>
        </Link>
      </header>
      <h1>나의 루틴</h1>
      {routines.length > 0 ? (
        <ul>
          {routines.map((routine) => (
            <li key={routine.routineId}>{routine.routineDay} - {routine.routineTime.hour}:{routine.routineTime.minute}</li>
          ))}
        </ul>
      ) : (
        <p>데이터를 불러오는 중입니다...</p>
      )}
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