import React, { useState, useEffect, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import RoutineContext from "../context/RoutineContext";
import "./RoutineDetail.css";

const RoutineDetail = () => {
  const { id } = useParams();
  const { routines, setRoutines } = useContext(RoutineContext);
  const navigate = useNavigate();
  const [routine, setRoutine] = useState({
    time: "",
    days: [],
    menu: "",
    store: "",
  });

  useEffect(() => {
    if (id && routines) {
      const existingRoutine = routines.find((routine) => routine.id === parseInt(id));
      if (existingRoutine) {
        setRoutine(existingRoutine);
      }
    }
  }, [id, routines]);

  const handleChange = (key, value) => {
    setRoutine({ ...routine, [key]: value });
  };

  const handleDayToggle = (day) => {
    setRoutine((prev) => ({
      ...prev,
      days: prev.days.includes(day)
        ? prev.days.filter((d) => d !== day)
        : [...prev.days, day],
    }));
  };

  const handleSave = () => {
    if (id) {
      // 기존 루틴 업데이트
      setRoutines((prev) =>
        prev.map((r) => (r.id === parseInt(id) ? { ...routine } : r))
      );
    } else {
      // 새 루틴 추가
      const newRoutine = { ...routine, id: routines.length + 1 };
      setRoutines((prev) => [...prev, newRoutine]);
    }
    navigate("/routinelist");
  };

  const handleCancel = () => {
    navigate("/routinelist");
  };

  if (!routine) {
    return <p>로딩 중...</p>;
  }

  return (
    <div className="container">
      <h1>{id ? "루틴 수정" : "새 루틴 추가"}</h1>
      <div className="time-section">
        <label htmlFor="time">시간:</label>
        <input
          id="time"
          type="time"
          value={routine.time}
          onChange={(e) => handleChange("time", e.target.value)}
        />
      </div>
      <div className="days-container">
        {["월", "화", "수", "목", "금", "토", "일"].map((day) => (
          <button
            key={day}
            className={`day-button ${routine.days.includes(day) ? "active" : ""}`}
            onClick={() => handleDayToggle(day)}
          >
            {day}
          </button>
        ))}
      </div>
      <div className="store-button-container">
        {["전농관", "학생회관"].map((store) => (
          <button
            key={store}
            className={`store-button ${routine.store === store ? "active" : ""}`}
            onClick={() => handleChange("store", store)}
          >
            {store}
          </button>
        ))}
      </div>
      <div className="action-buttons">
        <button className="cancel-button" onClick={handleCancel}>
          취소
        </button>
        <button className="save-button" onClick={handleSave}>
          저장
        </button>
      </div>
    </div>
  );
};

export default RoutineDetail;