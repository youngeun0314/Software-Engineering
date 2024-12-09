import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { getUserId } from "../../context/userStorage";
import "./PayView.css";
import Toolbar from "./Toolbar";

const Pay = ({ onSelectedStore }) => {
  const location = useLocation();
  const nav = useNavigate();

  const { totalPrice, pickUp, orderMenuOptions, fromCartView } = location.state || {};
  const [isLoading, setIsLoading] = useState(false);
  const [menuDetails, setMenuDetails] = useState([]);

  useEffect(() => {
    if (orderMenuOptions) {
      fetchMenuDetails(orderMenuOptions);
    }
  }, [orderMenuOptions]);

  const adjustedPickUp = fromCartView ? null : pickUp;

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
    const userId = getUserId();
    if (!userId) {
      console.error("Error: userId가 null입니다. 로그인 상태를 확인하세요.");
      alert("로그인 정보가 확인되지 않습니다. 다시 로그인해주세요.");
      return;
    }
    if (!totalPrice || orderMenuOptions === undefined) {
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
      orderMenuOptions: sanitizedOrderMenuOptions,
    };

    try {
      setIsLoading(true);

      const createOrderResponse = await fetch(`/orders/${userId}/order`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (!createOrderResponse.ok) {
        const errorMessage = await createOrderResponse.text();
        console.error("주문 생성 실패 메시지:", errorMessage);
        throw new Error("주문 생성에 실패했습니다. 다시 시도해주세요.");
      }

      const orderData = await createOrderResponse.json();
      const { orderId } = orderData;
      console.log("생성된 주문 ID:", orderId);

      localStorage.setItem("orderId", orderId); // orderId를 로컬 스토리지에 저장
      localStorage.setItem("userId", userId); // userId를 로컬 스토리지에 저장

      const paymentPayload = {
        cid: "TC0ONETIME",
        order_id: orderId,
        user_id: userId,
        item_name: "이룸 오더",
        quantity: sanitizedOrderMenuOptions.reduce((sum, item) => sum + item.quantity, 0),
        totalPrice: totalPrice,
        tax_free_amount: 0,
      };

      console.log("결제 준비 요청 payload:", paymentPayload);

      const paymentResponse = await fetch(`/order/pay/ready`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(paymentPayload),
      });

      if (!paymentResponse.ok) {
        const errorMessage = await paymentResponse.text();
        console.error("결제 준비 실패 메시지:", errorMessage);
        throw new Error("결제 준비에 실패했습니다. 다시 시도해주세요.");
      }

      const paymentData = await paymentResponse.json();
      console.log("결제 준비 성공 데이터:", paymentData);

      const { next_redirect_pc_url } = paymentData;
      if (next_redirect_pc_url) {
        window.location.href = next_redirect_pc_url; 
      } else {
        throw new Error("pc URL이 응답 데이터에 없습니다.");
      }
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
              <li className="pay-view-title-li-details" key={`${menu.menuId}-${index}`}>
                {menu.name} {menu.quantity}건
              </li>
            ))}
          </ul>
        ) : (
          <p className="pay-view-title-none">메뉴 정보를 불러오는 중...</p>
        )}
        <p>
          <div className="pay-view-title">픽업 시간</div>
          <div className="pay-view-title-details">{adjustedPickUp || "바로 주문"}</div>
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
        <button className="payment-button" onClick={handlePayment} disabled={isLoading}>
          {isLoading ? (
            "결제 중..."
          ) : (
            <img
              src="/kakaopay.png"
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
