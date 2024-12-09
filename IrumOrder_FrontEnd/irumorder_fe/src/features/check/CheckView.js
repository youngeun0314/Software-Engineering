import React, { useEffect, useState } from "react";
import { getUserId } from "../../context/userStorage";
import "./CheckView.css";

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
      <h2>주문 내역</h2>
      <table className="order-table">
        <thead>
          <tr>
            <th>주문 ID</th>
            <th>메뉴명</th>
            <th>수량</th>
            <th>가격</th>
            <th>픽업 시간</th>
            <th>옵션</th>
          </tr>
        </thead>
        <tbody>
          {orders.map((order) => (
            <React.Fragment key={order.orderId}>
              {order.orderMenuOptions.map((menu, index) => (
                <tr key={`${order.orderId}-${index}`}>
                  {index === 0 && (
                    <td rowSpan={order.orderMenuOptions.length}>
                      {order.orderId}
                    </td>
                  )}
                  <td>{menu.menuName}</td>
                  <td>{menu.quantity}</td>
                  {index === 0 && (
                    <td rowSpan={order.orderMenuOptions.length}>
                      {order.totalPrice}원
                    </td>
                  )}
                  {index === 0 && (
                    <td rowSpan={order.orderMenuOptions.length}>
                      {order.pickUp || "미정"}
                    </td>
                  )}
                  <td>
                    {menu.addShot && "샷 추가 "}
                    {menu.addVanilla && "바닐라 "}
                    {menu.addHazelnut && "헤이즐넛 "}
                    {menu.light && "연하게"}
                  </td>
                </tr>
              ))}
            </React.Fragment>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CheckView;
