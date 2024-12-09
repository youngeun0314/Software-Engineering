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
  const nav = useNavigate();

  // `getUserId`에서 초기 값을 가져오고, 상태로 관리
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const fetchedUserId = getUserId();
    setUserId(fetchedUserId);
    console.log("Fetched userId:", fetchedUserId);
  }, []);

  const [options, setOptions] = useState({
    userId: null, // 초기값 null로 변경
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

  useEffect(() => {
    if (userId !== null) {
      setOptions((prevOptions) => ({
        ...prevOptions,
        userId: userId, // `userId` 상태를 옵션에 반영
      }));
    }
  }, [userId]);

  const [selectedStore, setSelectedStore] = useState(null);

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
      userId: userId,
      menuId: menuId,
    }));
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

    const menu_out = getMenuIn();

    if (menu_out === 1) {
      setTimeout(() => {
        nav(`${getRoutineState()}`, { state: { options } });
      }, 0);
    } else if (menu_out === 0) {
      nav(`/store/${selectedStore}/cart/${userId}`, {
        state: { options, fromOptionUnder: true },
        replace: true,
      });
    }

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
          path="/routine/new"
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
          element={<CartView onSelectedStore={selectedStore} options={options} userId={getUserId()}/>}
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
