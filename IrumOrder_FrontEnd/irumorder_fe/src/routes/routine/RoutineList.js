import React from "react";
import { useNavigate } from "react-router-dom";
import "./RoutineList.css";

const RoutineList = () => {
  const navigate = useNavigate();
  const routines = [
    {
      id: 1,
      time: "10:00",
      menu: "아메리카노",
      days: ["월", "수", "금"],
      store: "스타벅스",
      active: true,
    },
  ];

  const toggleActive = (id) => {
    // API Call: Update routine active state
    console.log(`Toggled routine ${id}`);
  };

  const deleteRoutine = (id) => {
    // API Call: Delete routine
    console.log(`Deleted routine ${id}`);
  };

  return (
    <div className="routine-list">
      <h1>나의 루틴</h1>
      {routines.map((routine) => (
        <div key={routine.id} className="routine-item">
          <div className="routine-info">
            <h2>{routine.time}</h2>
            <p>
              {routine.menu} | {routine.days.join(", ")} | {routine.store}
            </p>
          </div>
          <div className="routine-controls">
            <button onClick={() => toggleActive(routine.id)}>
              {routine.active ? "ON" : "OFF"}
            </button>
            <button onClick={() => deleteRoutine(routine.id)}>X</button>
          </div>
        </div>
      ))}
      <button onClick={() => navigate("/routine-add")} className="add-button">
        +
      </button>
    </div>
  );
};

export default RoutineList;