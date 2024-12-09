import React, { useEffect, useState } from "react";
import "./CheckView.css"; // 스타일을 위한 CSS 파일

const CheckView = ({ userId }) => {
  const [orders, setOrders] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  // 주문 데이터를 가져오는 함수
  const fetchOrders = async () => {
    if (!userId) {
      setError("사용자 ID가 필요합니다.");
      return;
    }

    try {
      setIsLoading(true);
      setError(null);

      const response = await fetch(`/orders/1/orderCheck?userId=1`, {
        method: "GET",
      });

      if (!response.ok) {
        throw new Error("주문 내역을 가져오는데 실패했습니다.");
      }

      const data = await response.json();
      setOrders(data.orders || []);
    } catch (error) {
      console.error("주문 내역 조회 중 오류 발생:", error);
      setError(error.message || "알 수 없는 오류가 발생했습니다.");
    } finally {
      setIsLoading(false);
    }
  };

  // 컴포넌트가 렌더링될 때 데이터 로드
  useEffect(() => {
    fetchOrders();
  }, [userId]);

  return (
    <div className="checkview-container">
      <h1>주문 내역 확인</h1>
      {isLoading ? (
        <p>주문 내역을 불러오는 중...</p>
      ) : error ? (
        <p className="error">{error}</p>
      ) : orders.length === 0 ? (
        <p>저장된 주문 내역이 없습니다.</p>
      ) : (
        <ul className="order-list">
          {orders.map((order) => (
            <li key={order.orderId} className="order-item">
              <p><strong>주문 ID:</strong> {order.orderId}</p>
              <p><strong>결제 금액:</strong> {order.totalPrice}원</p>
              <p><strong>픽업 시간:</strong> {order.pickUp}</p>
              <p><strong>주문 메뉴:</strong></p>
              <ul>
                {order.orderMenuOptions.map((menu, index) => (
                  <li key={index}>
                    {menu.name} - {menu.quantity}개
                  </li>
                ))}
              </ul>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default CheckView;
