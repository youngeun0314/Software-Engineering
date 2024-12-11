/**
 * 파일: CheckView.js
 * 설명: 사용자 주문 내역을 조회하고 화면에 표시하는 컴포넌트
 * 작성자: 이희진, 최진영
 * 마지막 수정일: 2024-12-9
 */

import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getUserId } from "../../shared/context/userStorage";
import "./CheckView.css";

const CheckView = () => {
  // 주문 데이터를 저장하는 상태
  const [orders, setOrders] = useState([]); 
  const [loading, setLoading] = useState(true); // 로딩 상태
  const [error, setError] = useState(null); // 에러 메시지

  const userId = getUserId(); // 현재 사용자 ID 가져오기

  /**
   * 주문 데이터를 서버에서 가져오는 함수
   */
  const fetchOrders = async () => {
    try {
      // API 호출로 사용자 주문 데이터 가져오기
      const response = await fetch(
        `http://localhost:8080/orders/{userId}/orderCheck?userId=${userId}`
      );
      if (!response.ok) {
        throw new Error("데이터를 불러오는 데 실패했습니다."); // HTTP 오류 처리
      }
      const data = await response.json(); // JSON 데이터를 파싱
      setOrders(data); // 주문 데이터 상태 업데이트
    } catch (err) {
      setError(err.message); // 에러 메시지 상태 업데이트
    } finally {
      setLoading(false); // 로딩 상태 종료
    }
  };

  // 컴포넌트가 처음 렌더링될 때 fetchOrders 함수 실행
  useEffect(() => {
    fetchOrders();
  }, []);

  // 데이터 로딩 중일 때 표시
  if (loading) return <div className="loading">데이터 불러오는 중...</div>;
  // 데이터 로딩 실패 시 에러 메시지 표시
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="order-list-container">
      <header className="check-header">
        <p className="check-title">주문 내역</p>
        <Link to={"/main"}>
          <button className="x-button">
            <img
              src={`${process.env.PUBLIC_URL}/x_button.png`}
              alt="close"
              className="x-image"
            />
          </button>
        </Link>
      </header>

      <div className="order-card-container">
        {/* 주문 데이터 렌더링 */}
        {orders.map((order) => (
          <div key={order.orderId} className="order-card">
            {/* 각 주문의 메뉴 옵션 표시 */}
            {order.orderMenuOptions.map((menu, index) => (
              <div key={`${order.orderId}-${index}`}>
                <div className="menu-header">
                  {/* 메뉴 정보 및 수량 */}
                  <p className="menu-name">
                    ({menu.useCup === "TakeOut" ? "ICE" : "HOT"})
                    {menu.menuName} {menu.quantity}건
                  </p>
                </div>
                <div className="menu-footer">
                  {/* 픽업 시간 정보 */}
                  <p className="pickup-time">
                    {order.pickUp ? order.pickUp : "시간 미정"}
                  </p>
                </div>
              </div>
            ))}
            {/* 주문 총 가격 표시 */}
            <p className="menu-price">
              {order.totalPrice.toLocaleString()}원
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CheckView;