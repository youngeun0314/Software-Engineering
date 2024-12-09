import React, { useEffect, useState } from "react";
import { getUserId } from "../../context/userStorage";
import "./CheckView.css";
import { Link } from "react-router-dom";

const CheckView = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const userId = getUserId();

  // 주문 데이터 가져오기
  const fetchOrders = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/orders/{userId}/orderCheck?userId=${userId}`
      );
      if (!response.ok) {
        throw new Error("데이터를 불러오는 데 실패했습니다.");
      }
      const data = await response.json();
      setOrders(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  if (loading) return <div className="loading">데이터 불러오는 중...</div>;
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
        {orders.map((order) => (
          <div key={order.orderId} className="order-card">
            {order.orderMenuOptions.map((menu, index) => (
              <div key={`${order.orderId}-${index}`}>
                <div className="menu-header">
                  <p className="menu-name">
                    ({menu.useCup === "TakeOut" ? "ICE" : "HOT"})
                    {menu.menuName} {menu.quantity}건
                  </p>
                </div>
                <div className="menu-footer">
                  <p className="pickup-time">
                    {order.pickUp ? order.pickUp : "시간 미정"}
                  </p>
                  <p className="menu-price">
                    {order.totalPrice.toLocaleString()}원
                  </p>
                </div>
              </div>
            ))}
          </div>
        ))}
      </div>
    </div>
  );
};

export default CheckView;
