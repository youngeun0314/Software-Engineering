import React, { createContext, useState, useEffect } from "react";

// RoutineContext 생성
const RoutineContext = createContext();

// RoutineProvider 컴포넌트
export const RoutineProvider = ({ children }) => {
  const [routines, setRoutines] = useState([]);

  useEffect(() => {
    // API 호출로 루틴 데이터를 가져옵니다.
    const fetchRoutines = async () => {
      try {
        const response = await fetch("/api/routines");
        const data = await response.json();
        setRoutines(data);
      } catch (error) {
        console.error("루틴을 불러오는 데 실패했습니다.", error);
      }
    };

    fetchRoutines();
  }, []);

  return (
    <RoutineContext.Provider value={{ routines, setRoutines }}>
      {children}
    </RoutineContext.Provider>
  );
};

export default RoutineContext;
