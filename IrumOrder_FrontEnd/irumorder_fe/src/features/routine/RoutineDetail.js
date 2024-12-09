import React, { useState, useEffect, useContext } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import { Link } from "react-router-dom";
import RoutineContext from "../../context/RoutineContext";
import "./RoutineDetail.css";
import { getUserId } from "../../context/userStorage";
import { setMenuIn } from "../../context/OrderOrRoutine";
import { setRoutineState } from "../../context/OrderOrRoutine";

const RoutineDetail = ({setSelectedStore}) => {
  const location = useLocation();
  const { options } = location.state || {}; //menuId, menuOptions

  const { id } = useParams();
  const { routines, setRoutines } = useContext(RoutineContext);
  const navigate = useNavigate();
  const [routine, setRoutine] = useState({
    userId: getUserId(),
    menuId: options?.menuId,
    menuOptions: {
      useCup: "",
      addShot: false,
      addVanilla: false,
      addHazelnut: false,
      light: false,
    },
    routineDays: [],
    routineTime: "",
    isActivated: true,
  });
  
  const user_id=getUserId();

  useEffect(() => {
    if (id && routines) {
      const existingRoutine = routines.find((routine) => routine.routineId === parseInt(id));
      if (existingRoutine) {
        setRoutine(existingRoutine);
        setRoutine((prev) => ({
          ...prev,
          store: "전농관",
        }));;
      }
    }
  }, [id, routines]);

  useEffect(() => {
    console.log("Updated routine.menuId:", routine.menuId);
  }, [routine.menuId]);

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
    setRoutine((prev) => ({
      ...prev,
      store: store,
    }));
    setSelectedStore(store); // App의 selectedStore를 업데이트
  };


  const saveRoutineToServer = async (routineData) => {
    const method = id ? "PUT" : "POST";
    const endpoint = id
      ? `http://localhost:8080/api/users/${user_id}/routines/${id}`
      : `http://localhost:8080/api/users/${user_id}/routines/add`;
  
    try {
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
      console.log("routineData:", JSON.stringify(routineData, null, 2));
      console.error("요청 실패:", error.message);
      console.error("루틴 저장 중 오류 발생:", error);
    }
  };

  const handleSave = () => {
    setRoutine((prev) => ({
      ...prev,
      menuId: options?.menuId,
    }));
    
    setRoutine((prev) => ({
      ...prev,
      menuOptions: options?.menuOptions,
    }));

    const routineData = {
      userId: routine?.userId,
      menuId: routine?.menuId,
      menuId: routine?.menuId,
      menuDetail: routine?.menuDetail,
      menuOptions: routine?.menuOptions,
      routineDays: routine?.routineDays,
      routineTime: routine?.routineTime,
      isActivated: routine?.isActivated,
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
      <Link to={`/store/${routine.store}`} 
      onClick={() => {
        setMenuIn(1);
        setRoutineState(location.pathname);
      }}>
        <button className="menu-select-button">
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