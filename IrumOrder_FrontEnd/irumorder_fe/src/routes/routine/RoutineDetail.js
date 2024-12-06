import React, { useState, useEffect, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import RoutineContext from "../../context/RoutineContext";
import "./RoutineDetail.css";
import { getUserId } from "../../context/userStorage";

const RoutineDetail = () => {
  const { id } = useParams();
  const { routines, setRoutines } = useContext(RoutineContext);
  const navigate = useNavigate();
  const [routine, setRoutine] = useState({
    userId: 1,
    menuId: 10,
    menuDetailId: 1001,
    routineDays: [],
    routineTime: "",
    isActivated: true,
  });
  const [store, setStore] = useState("");

  const user_id=getUserId();

  useEffect(() => {
    if (id && routines) {
      const existingRoutine = routines.find((routine) => routine.routineId === parseInt(id));
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
      routineDays: prev.routineDays.includes(day)
        ? prev.routineDays.filter((d) => d !== day)
        : [...prev.routineDays, day],
    }));
  };

  const handleStoreChange = (store) => {
    setStore(() => ({
      store: store,
    }));
  };

  const saveRoutineToServer = async (routineData) => {
    try {
      const method = id ? "PUT" : "POST";
      const endpoint = id
        ? `http://localhost:8080/api/users/${user_id}/routines/${routineData.routineId}`
        : `http://localhost:8080/api/users/${user_id}/routines/add`;

      const response = await fetch(endpoint, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(routineData),
      });

      if (!response.ok) {
        throw new Error(`HTTP 오류: ${response.status}`);
      }

      const updatedRoutine = await response.json();

      setRoutines((prev) =>
        method === "PUT"
          ? prev.map((r) => (r.routineId === updatedRoutine.routineId ? updatedRoutine : r))
          : [...prev, updatedRoutine]
      );

      navigate("/routinelist");
    } catch (error) {
      console.error("루틴 저장 중 오류 발생:", error);
    }
  };

  const handleSave = () => {
    const userId = routines?.[0]?.userId || 1;
    const routineData = {
      ...routine,
      userId,
      routineTime: routine.routineTime,
      routineDays: routine.routineDays,
    };
    saveRoutineToServer(routineData);
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
          value={routine.routineTime}
          onChange={(e) => handleChange("routineTime", e.target.value)}
        />
      </div>
      <div className="days-container">
        {["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"].map((day) => (
          <button
            key={day}
            className={`day-button ${routine.routineDays.includes(day) ? "active" : ""}`}
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
            onClick={() => handleStoreChange(store)}
          >
            {store}
          </button>
        ))}
      </div>
      <div className="menu-button-container">
        <Link to={`/store/:${store}`}>
        <button className={`menu-select-button`}>
            메뉴
        </button>
        </Link>
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