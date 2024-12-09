import React, { useEffect, useState } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import CartView from "./components/CartView/CartView";
import CheckView from "./components/CheckView/CheckView";
import MenuView from "./components/MenuView/MenuView";
import OptionView from "./components/OptionView/OptionView";
import PayView from "./components/PayView/PayView";
import StoreSelection from "./components/StoreSelection/StoreSelection";
import { getMenuIn, getRoutineState, setMenuIn } from "./context/OrderOrRoutine";
import { RoutineProvider } from "./context/RoutineContext";
import { getUserId } from "./context/userStorage";
import Main from "./routes/Main";
import Paymentcomplete from "./routes/Paymentcomplete";
import PickupReserv from "./routes/payment/PickupReserv";
import RoutineDetail from "./routes/routine/RoutineDetail";
import RoutineListWrapper from "./routes/routine/RoutineList";
import Login from "./routes/user management/Login";
import Signup from "./routes/user registration/Signup";
import SignupComplete from "./routes/user registration/SignupComplete";
import SignupStart from "./routes/user registration/SignupStart";
import requestPermission from "./firebase/requestPermission";

const App = () => {
  const nav = useNavigate();

  // 상태 관리
  const [userId, setUserId] = useState(null);
  const [options, setOptions] = useState({
    userId: null,
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
  const [selectedStore, setSelectedStore] = useState(null);

  // 유저 ID 가져오기
  useEffect(() => {
    const fetchedUserId = getUserId();
    setUserId(fetchedUserId);
    console.log("Fetched userId:", fetchedUserId);
  }, []);

  // 옵션 업데이트
  useEffect(() => {
    if (userId !== null) {
      setOptions((prevOptions) => ({
        ...prevOptions,
        userId: userId,
      }));
    }
  }, [userId]);

  // FCM 토큰 가져오기
  useEffect(() => {
    const fetchFCMToken = async () => {
      try {
        const token = await requestPermission();
        if (token) {
          console.log("FCM Token:", token);
          await sendTokenToServer(token);
        }
      } catch (error) {
        console.error("FCM 처리 중 오류 발생:", error);
      }
    };

    fetchFCMToken();
  }, []);

  // selectedStore 상태 확인
  useEffect(() => {
    console.log("현재 선택된 Store:", selectedStore);
  }, [selectedStore]);

  // FCM 토큰 서버 전송 함수
  const sendTokenToServer = async (token) => {
    try {
      const response = await fetch("http://localhost:3000/api/save-token", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ token }),
      });

      if (response.ok) {
        console.log("FCM 토큰이 서버에 저장되었습니다.");
      } else {
        console.error("FCM 토큰 저장 실패:", response.statusText);
      }
    } catch (error) {
      console.error("FCM 토큰 전송 중 오류 발생:", error);
    }
  };

  // 메뉴 시작 핸들러
  const handleStartMenu = (store) => {
    if (!store) {
      console.error("Error: Store 값이 유효하지 않습니다.");
      return;
  }
    setMenuIn(0);
    setSelectedStore(store);
    nav(`/store/${store}`);
  };

  // 옵션 핸들러
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

  // 장바구니 시작 핸들러
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
        <Route path="/routinelist" element={<RoutineListWrapper />} />
        <Route
          path="/routine/:id"
          element={<RoutineDetail setSelectedStore={setSelectedStore} />}
        />
        <Route
          path="/store"
          element={<StoreSelection onStartMenu={handleStartMenu} />}
        />
        <Route
          path="/store/:store"
          element={
            <MenuView
              userId={options.userId}
              onSelectedStore={selectedStore}
              onStartOption={handleOption}
            />
          }
        />
        <Route
          path="/store/:store/option/:menuId"
          element={
            <OptionView
              onSelectedStore={selectedStore}
              options={options}
              setOptions={setOptions}
              onStartCart={handleStartCart}
            />
          }
        />
        <Route
          path="/store/:store/cart/:userId"
          element={
            <CartView
              onSelectedStore={selectedStore}
              options={options}
              userId={getUserId()}
            />
          }
        />
        <Route path="/timeReservation" element={<PickupReserv />} />
        <Route path="/pay" element={<PayView onSelectedStore={selectedStore} />} />
        <Route
          path="/check"
          element={<CheckView userId={options.userId} />}
        />
        <Route path="/paymentcomplete" element={<Paymentcomplete />} />
      </Routes>
    </RoutineProvider>
  );
};

export default App;

