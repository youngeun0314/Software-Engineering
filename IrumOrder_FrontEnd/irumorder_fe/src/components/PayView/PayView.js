import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./PayView.css"; // Import the CSS file
import Toolbar from "./Toolbar";

const Pay = ({ onSelectedStore }) => {
  const location = useLocation();
  const nav = useNavigate();

  // 기존 데이터 가져오기
  const { userId, totalPrice, pickUp, orderMenuOptions, fromCartView } = location.state || {};
  const [isLoading, setIsLoading] = useState(false);
  const [menuDetails, setMenuDetails] = useState([]);

  useEffect(() => {
    if (orderMenuOptions) {
      fetchMenuDetails(orderMenuOptions);
    }
  }, [orderMenuOptions]);

  // 픽업 시간 처리
  const adjustedPickUp = fromCartView ? null : pickUp;

  // Fetch menu details for each menuId
  const fetchMenuDetails = async (menuOptions) => {
    try {
      const uniqueMenuOptions = menuOptions.map((option, index) => ({
        ...option,
        uniqueKey: `${option.menuId}-${index}`,
      }));
  
      const menuDetailsPromises = uniqueMenuOptions.map(async (option) => {
        const response = await fetch(`/menu/getOneMenu?menuId=${option.menuId}`, {
          method: "GET",
        });
  
        if (!response.ok) {
          throw new Error(`메뉴 ID ${option.menuId} 정보를 가져오는데 실패했습니다.`);
        }
  
        const menu = await response.json();
        return {
          menuId: option.menuId,
          name: menu.name,
          quantity: option.quantity || 1,
          uniqueKey: option.uniqueKey,
        };
      });
  
      const resolvedMenuDetails = await Promise.all(menuDetailsPromises);
      setMenuDetails(resolvedMenuDetails);
    } catch (error) {
      console.error("메뉴 정보를 가져오는 중 오류 발생:", error);
      alert("메뉴 정보를 가져오는 중 문제가 발생했습니다. 다시 시도해주세요.");
    }
  };
  

  const handlePayment = async () => {
    if (!userId || !totalPrice || orderMenuOptions === undefined) {
      alert("주문 정보를 확인해주세요.");
      return;
    }

    const sanitizedOrderMenuOptions = orderMenuOptions.map(({ menuId, quantity, menuOptions }) => ({
      menuId,
      quantity,
      menuOptions,
    }));
  
    const payload = {
      userId,
      totalPrice,
      pickUp: adjustedPickUp,
      orderMenuOptions: sanitizedOrderMenuOptions, // clean된 데이터 사용
    };

    console.log("백엔드로 보내는 payload:", payload);

    try {
      setIsLoading(true);
      const response = await fetch(`/orders/${userId}/order`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (!response.ok) {
        const errorMessage = await response.text(); // 서버 오류 메시지 출력
        console.error("서버 오류 메시지:", errorMessage);
        throw new Error("결제에 실패했습니다. 다시 시도해주세요.");
      }
      

      const data = await response.json();
      alert("결제가 완료되었습니다!");

      // 결제 성공 시 로컬 스토리지 비우기
      localStorage.removeItem(userId);

      nav("/paymentcomplete", { state: { orderId: data.orderId } });
    } catch (error) {
      console.error("결제 요청 중 오류 발생:", error);
      alert("결제 처리 중 문제가 발생했습니다. 다시 시도해주세요.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="PayView">
      <div className="pay-view-tool">
        <Toolbar title="결제" onBack={() => nav(-1)} />
      </div>
      <div className="pay-view-details">
        <div className="pay-view-title">주문 내역</div>
        {menuDetails.length > 0 ? (
          <ul>
          {menuDetails.map((menu, index) => (
            <li
              className="pay-view-title-li-details"
              key={`${menu.menuId}-${index}`}
            >
              {menu.name} {menu.quantity}건
            </li>
          ))}
        </ul>
        
        ) : (
          <p className="pay-view-title-none">메뉴 정보를 불러오는 중...</p>
        )}
        <p>
          <div className="pay-view-title">픽업 시간</div>
          <div className="pay-view-title-details">
            {adjustedPickUp || "바로 주문"}
          </div>

        </p>
        <p>
          <div className="pay-view-title">픽업 장소</div>
          <div className="pay-view-title-details">{onSelectedStore || "전농관"}</div>
        </p>
      </div>
      <div className="divider"></div>
      <div className="payment-summary">
        <p>결제 금액</p>
        <p className="payment-amount">{totalPrice || "1,600"}</p>
      </div>
      <div className="line-divider"></div>

      <div className="pay-view-payment-button-container">
        <button
          className="payment-button"
          onClick={handlePayment}
          disabled={isLoading}
        >
          {isLoading ? (
            "결제 중..."
          ) : (
            <img
              src="/kakaopay.png" // public 폴더의 이미지 경로
              alt="결제 버튼"
              style={{
                width: "100%",
                height: "auto",
                display: "block",
              }}
            />
          )}
        </button>
      </div>
    </div>
  );
};

export default Pay;
