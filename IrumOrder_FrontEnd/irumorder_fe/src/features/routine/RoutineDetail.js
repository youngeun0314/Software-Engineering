/**
 * 파일: RoutineDetail.js
 * 설명: 루틴 상세 페이지 컴포넌트를 정의하며, 루틴 데이터를 조회, 생성, 수정할 수 있는 UI를 제공
 * 작성자: 이희진
 * 마지막 수정일: 2024-12-10
 */

import React, { useContext, useEffect, useState } from "react";
import { Link, useLocation, useNavigate, useParams } from "react-router-dom";
import { setMenuIn, setRoutineState } from "../../shared/context/menuRoutineState";
import RoutineContext from "../../shared/context/RoutineContext";
import { getUserId } from "../../shared/context/userStorage";
import "./RoutineDetail.css";

const RoutineDetail = ({setSelectedStore}) => {
  // React Router를 통해 받은 location 객체에서 state 정보 추출
  const location = useLocation();
  const { options } = location.state || {}; // 메뉴 ID 및 옵션 정보 포함

  const { id } = useParams(); // URL 파라미터에서 ID 추출
  const { routines, setRoutines } = useContext(RoutineContext); // 루틴 컨텍스트에서 데이터 및 상태 관리
  const navigate = useNavigate();

  // 루틴 초기 상태 설정
  const [routine, setRoutine] = useState({
    userId: parseInt(getUserId()), // 현재 사용자 ID 설정
    menuId: options?.menuId, // 선택된 메뉴 ID
    menuOptions: {
      useCup: "",
      addShot: false,
      addVanilla: false,
      addHazelnut: false,
      light: false,
    },
    routineDays: [], // 요일 정보
    routineTime: "", // 루틴 시간
    isActivated: true, // 활성화 여부
  });
  
  const user_id=getUserId(); // 사용자 ID 가져오기

  // 기존 루틴 데이터를 가져와 편집 모드로 전환
  useEffect(() => {
    if (id && routines) {
      const existingRoutine = routines.find((routine) => routine.routineId === parseInt(id));
      if (existingRoutine) {
        setRoutine(existingRoutine); // 기존 데이터를 상태에 로드
        setRoutine((prev) => ({
          ...prev,
          store: "전농관", // 기본 매장 설정
        }));;
      }
    }
  }, [id, routines]);

  // menuId가 업데이트될 때마다 확인
  useEffect(() => {
    console.log("Updated routine.menuId:", routine.menuId); // 디버깅용 로그
  }, [routine.menuId]);

  /**
   * 루틴 속성 업데이트
   *
   * @param key 업데이트할 속성 키
   * @param value 해당 속성의 값
   */
  const handleChange = (key, value) => {
    setRoutine({ ...routine, [key]: value });
  };

  /**
   * 요일 선택/해제
   *
   * @param day 선택된 요일
   */
  const handleDayToggle = (day) => {
    setRoutine((prev) => ({
      ...prev,
      routineDays: prev.routineDays.includes(day)
        ? prev.routineDays.filter((d) => d !== day)
        : [...prev.routineDays, day],
    }));
  };

  /**
   * 매장 정보 변경
   *
   * @param store 선택된 매장 이름
   */
  const handleStoreChange = (store) => {
    setRoutine((prev) => ({
      ...prev,
      store: store,
    }));
    setSelectedStore(store); // App의 selectedStore를 업데이트
  };

  /**
   * 서버에 루틴 저장
   *
   * @param routineData 저장할 루틴 데이터
   */
  const saveRoutineToServer = async (routineData) => {
    const method = id ? "PUT" : "POST"; // 수정/생성 여부에 따라 메서드 결정
    const endpoint = id
      ? `http://localhost:8080/api/users/${user_id}/routines/${id}`
      : `http://localhost:8080/api/users/${user_id}/routines/add`;
  
    try {
      console.log("Sending data to server:", JSON.stringify(routineData, null, 2)); // 디버깅용 로그
      const response = await fetch(endpoint, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(routineData),
      });
  
      if (!response.ok) {
        const errorText = await response.text();
        console.error("Server responded with error:", errorText); // 서버 오류 메시지 출력
        throw new Error(`HTTP 오류: ${response.status}`);
      }
  
      const updatedRoutine = await response.json(); // 서버 응답 데이터
      console.log("Server responded with updatedRoutine:", updatedRoutine);
  
      // 루틴 상태 업데이트
      setRoutines((prev) =>
        method === "PUT"
          ? prev.map((r) =>
              r.routineId === updatedRoutine.routineId ? updatedRoutine : r
            )
          : [...prev, updatedRoutine]
      );
  
      navigate("/routinelist"); // 루틴 목록 페이지로 이동
    } catch (error) {
      console.log("routineData:", JSON.stringify(routineData, null, 2)); // 오류 발생 시 데이터를 확인
      console.error("루틴 저장 중 오류 발생:", error);
  
      // HTTP 500에 대한 추가 디버깅
      if (error.message.includes("HTTP 오류: 500")) {
        console.error("서버 내부 오류 발생: 이 문제는 서버에서 데이터를 처리하는 중 발생했습니다.");
        console.error("서버 로그를 확인하거나 요청 데이터의 형식을 다시 확인하세요.");
      }
  
      alert(
        `루틴 저장 중 문제가 발생했습니다. 서버 응답: ${
          error.message || "Unknown error"
        }`
      );
    }
  };
  
  /**
   * 루틴 저장 핸들러
   */
  const handleSave = () => {
    setRoutine((prev) => ({
      ...prev,
      menuId: options?.menuId, // 선택된 메뉴 ID 업데이트
    }));
    
    setRoutine((prev) => ({
      ...prev,
      menuOptions: options?.menuOptions, // 메뉴 옵션 설정
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

  /**
   * 취소 핸들러
   */
  const handleCancel = () => {
    navigate("/routinelist"); // 루틴 목록으로 이동
  };

  if (!routine) {
    return <p>로딩 중...</p>; // 로딩 중 상태 표시
  }

  return (
    <div className="container">
      <h1>루틴 설정</h1>
      <div className="routine-container">
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
    </div>
  );
};

export default RoutineDetail;