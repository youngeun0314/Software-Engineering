import React, { useState, useEffect } from "react";
import "./PickupReserv.css";
import { Link, useNavigate, useLocation } from "react-router-dom";
import Toolbar from "./Toolbar";

const PickupReserv = () => {
  const nav = useNavigate();
  const location = useLocation();
  const [selectedHour, setSelectedHour] = useState(null);
  const [selectedInterval, setSelectedInterval] = useState(null);
  const [pickUpTime, setPickUpTime] = useState("");

  const { orderMenuOptions = [], totalPrice = 0, userId = null } = location.state || {};

  // 시간 및 구간 배열
  const hours = Array.from({ length: 11 }, (_, i) => 9 + i); // 9시부터 19시
  const intervals = ["00-10", "10-20", "20-30", "30-40", "40-50", "50-00"];

  useEffect(() => {
    console.log("Received orderMenuOptions:", orderMenuOptions);
    console.log("Received Total Price:", totalPrice);
    console.log("Received User ID:", userId);
  }, [orderMenuOptions, totalPrice, userId]);

  // 시간 및 구간 선택 시 pickUpTime 업데이트
  useEffect(() => {
    if (selectedHour && selectedInterval) {
      const [startMinute] = selectedInterval.split("-");
      const updatedTime = `${selectedHour < 10 ? `0${selectedHour}` : selectedHour}:${startMinute}`;
      setPickUpTime(updatedTime);
    }
  }, [selectedHour, selectedInterval]);

  const handleHourClick = (hour) => {
    setSelectedHour(hour);
    setSelectedInterval(null); // 시간 변경 시 분 초기화
  };

  const handleIntervalClick = (interval) => {
    setSelectedInterval(interval);
  };

  const handlePayment = () => {
    if (!pickUpTime) {
      alert("시간대를 선택해주세요.");
      return;
    }

    const paymentData = {
      userId,
      totalPrice,
      pickUp: pickUpTime,
      orderMenuOptions,
    };

    console.log("결제 데이터:", paymentData);

    // `/pay`로 이동하면서 결제 데이터를 전달
    nav("/pay", { state: paymentData });
  };

  return (
    <div className="time-picker-container">
      <div className="cart-tool">
        <Toolbar title="픽업 시간 예약" onBack={() => nav(-1)} />
      </div>
      <header className="time-picker-header">
        <Link to={"/main"}>
          <button className="back-button">
            <img
              src={`${process.env.PUBLIC_URL}/back_button.png`}
              alt="back"
              className="back-image"
            />
          </button>
        </Link>
        <h2>픽업 시간 예약</h2>
      </header>
      <p>상품을 픽업할 시간대를 선택하세요.</p>

      <div className="time-picker-hours">
        {hours.map((hour) => (
          <button
            key={hour}
            className={`hour-button ${selectedHour === hour ? "active" : ""}`}
            onClick={() => handleHourClick(hour)}
          >
            {hour < 10 ? `0${hour}:00` : `${hour}:00`}
          </button>
        ))}
      </div>

      {selectedHour && (
        <div className="time-picker-intervals">
          {intervals.map((interval) => (
            <button
              key={interval}
              className={`interval-button ${selectedInterval === interval ? "active" : ""}`}
              onClick={() => handleIntervalClick(interval)}
            >
              {`${selectedHour}:${interval}`}
            </button>
          ))}
        </div>
      )}

      <div className="payment-button-container">
        <button className="payment-button" onClick={handlePayment}>
          결제하기
        </button>
      </div>

      <footer className="current-time">현재 시각: 09:00</footer>
    </div>
  );
};

export default PickupReserv;
