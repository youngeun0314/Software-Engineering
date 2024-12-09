import React, { useEffect, useState } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import CartView from './components/CartView/CartView';
import CheckView from './components/CheckView/CheckView';
import MenuView from "./components/MenuView/MenuView";
import OptionView from "./components/OptionView/OptionView";
import PayView from './components/PayView/PayView';
import StoreSelection from "./components/StoreSelection/StoreSelection";
import { getMenuIn, getRoutineState, setMenuIn } from "./context/OrderOrRoutine";
import { RoutineProvider } from "./context/RoutineContext";
import { getUserId } from "./context/userStorage";
import Main from "./routes/Main";
import Paymentcomplete from './routes/Paymentcomplete';
import PickupReserv from "./routes/payment/PickupReserv";
import RoutineDetail from "./routes/routine/RoutineDetail";
import RoutineListWrapper from "./routes/routine/RoutineList";
import Login from "./routes/user management/Login";
import Signup from "./routes/user registration/Signup";
import SignupComplete from "./routes/user registration/SignupComplete";
import SignupStart from "./routes/user registration/SignupStart";

const App = () => {
  const userId = getUserId();
  const [options, setOptions] = useState({
    userId : 2,//usrId, //그냥 고정값으로 함
    Price : 0,//프라이스 장바구니 페이지에서 수정, 샷추가도 넣어야함 

    menuId : null, //이거 구조수정해야함
    name : "",
    quantity : 1,
    menuOptions: {
        useCup : "",
        addShot: false, 
        addVanilla: false,
        addHazelnut: false,
        light: false,
    }    
});
useEffect(() => {
  console.log("App.js - Current userId:", options.userId); // 로그로 확인
}, [options.userId]);

  const [selectedStore, setSelectedStore] = useState(null);
  const nav = useNavigate(); // 페이지 이동을 위해 useNavigate 사용

  // 메뉴 화면으로 이동
  const handleStartMenu = (store) => {
    if (!store) {
      console.error("Error: Store 값이 유효하지 않습니다.");
      return;
  }
    setMenuIn(0);
    setSelectedStore(store);
    nav(`/store/${store}`);
  };

  const handleOption = (menuId) => {
    const userId = options.userId;
    console.log("handleOption called with userId:", userId, "and menuId:", menuId); // 로그 추가
    if (!selectedStore) {
      console.error("Error: Store is not selected.");
      return;
  }

  if (!menuId) {
      console.error("Error: Menu ID is undefined.");
      return;
  }
  
    setOptions((prevOptions) => ({
      ...prevOptions,
      userId :prevOptions.userId,
      menuId: menuId, // 선택한 menuId를 options에 저장
  }));
  console.log("Updated options.menuId in App:", menuId);
    nav(`/store/${selectedStore}/option/${menuId}`);
  };

  const handleStartCart = (options) => {
    if (!selectedStore) {
        console.error("Error: Store is not selected.");
        return;
    }
    if (!options.menuOptions.useCup) {
      alert("컵을 선택해주세요.");
      return;
  }
    const userId = getUserId();
    const menu_out=getMenuIn();

    if (menu_out === 1) {
      setTimeout(() => {
          nav(`${getRoutineState()}`,{ state: {options}}); // 상태 업데이트 이후 페이지 이동
      }, 0);
    } else if(menu_out===0) {
      // URL에 userId 포함하여 이동
      nav(`/store/${selectedStore}/cart/${userId}`, { 
        state: { options, fromOptionUnder: true }, replace: true 
      });
    }
    console.log("현재 선택된 Store:", selectedStore);
    console.log("메뉴 상태 (menu_out):", getMenuIn());
    
  // 옵션 초기화
  setOptions({
      userId: userId,
      Price: 0,
      menuId: null,
      name: "",
      quantity: 1,
      menuOptions: {
          useCup: "",
          addShot: false,
          addVanilla: false,
          addHazelnut: false,
          light: false,
      },
  });
};



  useEffect(() => {
    console.log("Updated userId in options:", options.userId);
}, [options.userId]);
useEffect(() => {
  console.log("현재 선택된 Store:", selectedStore);
}, [selectedStore]);


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
        <Route path="/routine/:id" element={<RoutineDetail setSelectedStore={setSelectedStore} />} />
        <Route path="/routine/new" element={<RoutineDetail setSelectedStore={setSelectedStore} />} />

        {/* 추가 라우트 */}
        <Route path="/store" element={<StoreSelection onStartMenu={handleStartMenu} />} />
        <Route path="/store/:store" element={<MenuView userId={options.userId} onSelectedStore={selectedStore} onStartOption={handleOption} />} />
        <Route path="/store/:store/option/:menuId" element={<OptionView onSelectedStore={selectedStore} options={options} setOptions={setOptions} onStartCart = {handleStartCart}/>} />
        <Route path="/store/:store/cart/:userId" element={<CartView onSelectedStore={selectedStore} options={options} />}/>
        <Route path="/timeReservation" element={<PickupReserv/>}/>
        <Route path="/pay" element={<PayView onSelectedStore={selectedStore} />}/>
        <Route path="/check" element={<CheckView userId={options.userId}/>}/>
        <Route path="/paymentcomplete" element={<Paymentcomplete/>}/>
      </Routes>
    </RoutineProvider>
  );
};

export default App;

