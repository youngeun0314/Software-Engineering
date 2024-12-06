import React, { useState } from "react";
import "./PickupReserv.css";
import { Link } from "react-router-dom";

const PickupReserv = () => {
  const [selectedHour, setSelectedHour] = useState(null);
  const [selectedInterval, setSelectedInterval] = useState(null);

  const hours = Array.from({ length: 11 }, (_, i) => 9 + i); // 9시부터 19시
  const intervals = [
    "00-10",
    "10-20",
    "20-30",
    "30-40",
    "40-50",
    "50-00",
  ];

  const handleHourClick = (hour) => {
    setSelectedHour(hour);
    setSelectedInterval(null); // 시간 변경 시 분 초기화
  };

  const handleIntervalClick = (interval) => {
    setSelectedInterval(interval);
  };

  const handlePayment = () => {
    if (!selectedHour || !selectedInterval) {
      alert("시간대와 구간을 선택해주세요.");
      return;
    }
    console.log(`선택된 시간: ${selectedHour}:${selectedInterval.split("-")[0]}`);
  };

  return (
    <div className="time-picker-container">
      <header className="time-picker-header">
        <Link to={'/main'}>
          <button className="back-button">
            <img
            src={`${process.env.PUBLIC_URL}/back_button.png`}
            alt="back"
            className="back-image"
          /></button>
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
              className={`interval-button ${
                selectedInterval === interval ? "active" : ""
              }`}
              onClick={() => handleIntervalClick(interval)}
            >
              {`${selectedHour}:${interval}`}
            </button>
          ))}
        </div>
      )}

      <button className="payment-button" onClick={handlePayment}>
        결제하기
      </button>

      <footer className="current-time">현재 시각: 09:00</footer>
    </div>
  );
};

export default PickupReserv;
