import React, { useState } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import MenuView from "./components/MenuView/MenuView";
import OptionView from "./components/OptionView/OptionView";
import StoreSelection from "./components/StoreSelection/StoreSelection";
import { RoutineProvider } from "./context/RoutineContext";
import Main from "./routes/Main";
import PastOrder from "./routes/order received/PastOrder";
import RoutineDetail from "./routes/routine/RoutineDetail";
import RoutineListWrapper from "./routes/routine/RoutineList";
import Login from "./routes/user management/Login";
import Signup from "./routes/user registration/Signup";
import SignupComplete from "./routes/user registration/SignupComplete";
import SignupStart from "./routes/user registration/SignupStart";
import PickupReserv from "./routes/payment/PickupReserv";

const App = () => {
  const [selectedStore, setSelectedStore] = useState("");
  const [selectedOption, setSelectedOption] = useState(""); // 선택한 옵션 상태 추가
  const nav = useNavigate(); // 페이지 이동을 위해 useNavigate 사용

  // 스토어 선택 화면으로 이동
  const handleStartStore = () => {
    nav("/store");
  };

  // 메뉴 화면으로 이동
  const handleStartMenu = (store) => {
    setSelectedStore(store);
    nav(`/store/${store}`);
  };

  const handleOption = (menuId) => {
    setSelectedOption(menuId); // 선택한 옵션 상태
    nav(`/option/${menuId}`);
  };

  return (
    <RoutineProvider>
      <Routes>
        {/* 기존 라우트 */}
        <Route path="/" element={<Login />} />
        <Route path="/signupstart" element={<SignupStart />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/signupcomplete" element={<SignupComplete />} />
        <Route path="/main" element={<Main />} />
        <Route path="/routinelist" element={<RoutineListWrapper/>} />
        <Route path="/past-order" element={<PastOrder />} />
        <Route path="/routine/:id" element={<RoutineDetail />} />
        <Route path="/routine/new" element={<RoutineDetail />} />

        {/* 추가 라우트 */}
        <Route path="/store" element={<StoreSelection onStartMenu={handleStartMenu} />} />
        <Route path="/store/:store" element={<MenuView onSelectedStore={selectedStore} onStartOption={handleOption} />} />
        <Route path="/option/:menuId" element={<OptionView onSelectedOption={selectedOption} />} />
        {/*<Route path="/cart" element={<CartView/>} />*/}
      </Routes>
    </RoutineProvider>
  );
};

export default App;
