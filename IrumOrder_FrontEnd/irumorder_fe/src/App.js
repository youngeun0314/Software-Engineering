/**
 * 파일: App.js
 * 설명: 애플리케이션의 최상위 컴포넌트로, 라우팅 설정, 사용자 인증, 푸시 알림 처리, 전역 상태 관리를 포함
 * 작성자: 이희진, 박수빈, 최진영
 * 마지막 수정일: 2024-12-10
 */

import React, { useEffect, useState } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
import CartView from "./features/cart/CartView";
import CheckView from "./features/check/CheckView";
import Menu from "./features/menu/MenuView";
import OptionView from "./features/option/OptionView";
import Pay from "./features/payment/PayView";
import StoreSelection from "./features/store/StoreSelection";
import { getMenuIn, getRoutineState, setMenuIn } from "./shared/context/menuRoutineState";
import { RoutineProvider } from "./shared/context/RoutineContext";
import { getUserId } from "./shared/context/userStorage";
import requestPermission from "./firebase/requestPermission";
import Main from "./shared/ui/Main";
import Paymentcomplete from "./features/payment/Paymentcomplete";
import PickupReserv from "./features/payment/PickupReserv";
import RoutineDetail from "./features/routine/RoutineDetail";
import RoutineListWrapper from "./features/routine/RoutineList";
import Login from "./features/user/management/Login";
import Signup from "./features/user/registration/Signup";
import SignupComplete from "./features/user/registration/SignupComplete";
import SignupStart from "./features/user/registration/SignupStart";

const App = () => {
  const nav = useNavigate(); // 페이지 네비게이션을 위한 hook

  // 사용자 ID를 상태로 관리
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    // 로컬 스토리지 또는 다른 소스로부터 사용자 ID를 가져옴
    const fetchedUserId = getUserId();
    setUserId(fetchedUserId); // 상태 업데이트
    console.log("Fetched userId:", fetchedUserId); // 디버깅용 로그
  }, []);

  // 메뉴 옵션 초기값 및 상태 관리
  const [options, setOptions] = useState({
    userId: null, // 사용자 ID
    Price: 0, // 총 가격
    menuId: null, // 메뉴 ID
    name: "", // 메뉴 이름
    quantity: 1, // 수량
    menuOptions: {
      useCup: "", // 컵 옵션
      addShot: false, // 샷 추가 여부
      addVanilla: false, // 바닐라 추가 여부
      addHazelnut: false, // 헤이즐넛 추가 여부
      light: false, // 가벼운 옵션
    },
  });

  useEffect(() => {
    // userId 변경 시 옵션 상태 업데이트
    if (userId !== null) {
      setOptions((prevOptions) => ({
        ...prevOptions,
        userId: userId,
      }));
    }
  }, [userId]);

  const [selectedStore, setSelectedStore] = useState(null); // 선택된 매장 상태

  /**
   * 메뉴 시작 핸들러
   *
   * @param store 선택된 매장
   * @return void
   */
  const handleStartMenu = (store) => {
    if (!store) {
      console.error("Error: Store 값이 유효하지 않습니다.");
      return;
    }
    setMenuIn(0); // 메뉴 설정 초기화
    setSelectedStore(store); // 매장 선택 상태 업데이트
    nav(`/store/${store}`); // 메뉴 페이지로 이동
  };

  /**
   * 옵션 설정 핸들러
   *
   * @param menuId 메뉴 ID
   * @return void
   */
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
    nav(`/store/${selectedStore}/option/${menuId}`); // 옵션 페이지로 이동
  };

  /**
   * 장바구니 시작 핸들러
   *
   * @param options 메뉴 옵션
   * @return void
   */
  const handleStartCart = (options) => {
    if (!selectedStore) {
      console.error("Error: Store is not selected.");
      return;
    }
    if (!options.menuOptions.useCup) {
      alert("컵을 선택해주세요."); // 컵 선택 필수
      return;
    }

    const menu_out = getMenuIn(); // 현재 메뉴 상태 확인

    if (menu_out === 1) {
      // 루틴과 연동된 메뉴
      setTimeout(() => {
        nav(`${getRoutineState()}`, { state: { options } }); // 루틴 페이지로 이동
      }, 0);
    } else if (menu_out === 0) {
      // 일반 장바구니
      nav(`/store/${selectedStore}/cart/${userId}`, {
        state: { options, fromOptionUnder: true },
        replace: true,
      });
    }

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

  // FCM 푸시 알림 처리
  useEffect(() => {
    const fetchFCMToken = async () => {
      try {
        // 푸시 알림 권한 요청 및 FCM 토큰 가져오기
        const token = await requestPermission();
        if (token) {
          console.log("FCM Token:", token);
          // 서버에 FCM 토큰 전송
          await sendTokenToServer(token);
        }
      } catch (error) {
        console.error("FCM 처리 중 오류 발생:", error);
      }
    };

    fetchFCMToken();
  }, []);

  /**
   * FCM 토큰을 서버에 전송하는 함수
   *
   * @param token FCM 토큰
   * @return void
   */
  const sendTokenToServer = async (token) => {
    try {
      const response = await fetch("http://localhost:3000/api/save-token", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ token }), // 서버에 전송할 토큰
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

  return (
    <RoutineProvider>
      <Routes>
        <Route path="/" element={<Login />} /> {/* 로그인 페이지 */}
        <Route path="/signupstart" element={<SignupStart />} /> {/* 회원가입 시작 페이지 */}
        <Route path="/signup" element={<Signup />} /> {/* 회원가입 페이지 */}
        <Route path="/signupcomplete" element={<SignupComplete />} /> {/* 회원가입 완료 페이지 */}
        <Route path="/main" element={<Main />} /> {/* 메인 페이지 */}
        <Route path="/routinelist" element={<RoutineListWrapper />} /> {/* 루틴 목록 페이지 */}
        <Route
          path="/routine/:id"
          element={<RoutineDetail setSelectedStore={setSelectedStore} />} // 루틴 상세 페이지
        />
        <Route
          path="/routine/new"
          element={<RoutineDetail setSelectedStore={setSelectedStore} />} // 루틴 생성 페이지
        />
        <Route
          path="/store"
          element={<StoreSelection onStartMenu={handleStartMenu} />} // 매장 선택 페이지
        />
        <Route
          path="/store/:store"
          element={
            <Menu
              userId={options.userId}
              onSelectedStore={selectedStore}
              onStartOption={handleOption}
            />
          } // 메뉴 페이지
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
          } // 옵션 선택 페이지
        />
        <Route
          path="/store/:store/cart/:userId"
          element={<CartView onSelectedStore={selectedStore} options={options} />} // 장바구니 페이지
        />
        <Route path="/timeReservation" element={<PickupReserv />} /> {/* 예약 페이지 */}
        <Route path="/pay" element={<Pay onSelectedStore={selectedStore} />} /> {/* 결제 페이지 */}
        <Route
          path="/check"
          element={<CheckView userId={options.userId} />} // 주문 내역 페이지
        />
        <Route path="/paymentcomplete" element={<Paymentcomplete />} /> {/* 결제 완료 페이지 */}
      </Routes>
    </RoutineProvider>
  );
};

export default App;