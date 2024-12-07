import React from "react";
import "./Payment.css";
import { Link } from 'react-router-dom';


function Payment() {
  return (
    <div className="payment-container">
      <header className="payment-header">
        <Link to={'/cart'}>
          <button className="back-button">
            <img
            src={`${process.env.PUBLIC_URL}/ep_back.png`}
            alt="go to back"
            className="back-image"
          /></button>
        </Link>
        <div className="payment-text">
          <p>결제</p>
        </div>
        
      </header>
      <div className="order-info">
        <p className="order-title">주문 내역</p>
        <p className="order-detail">(ICE) 아메리카노 1건</p>
        <p className="pickup-time-title">픽업 시간</p>
        <p className="pickup-time">바로 주문</p>
        <p className="pickup-place-title">픽업 장소</p>
        <p className="pickup-place">학생회관</p>
      </div>
      <div className="divider"></div>
      <div className="payment-summary">
        <p>결제 금액</p>
        <p className="payment-amount">1,600원</p>
      </div>
      <div className="line-divider"></div>
      <div className="payment-image-container">
        <Link to={'/paymentcomplete'}>
            <button type="pay" className="pay-button">
              <img
                src={`${process.env.PUBLIC_URL}/Group 2.png`}
                alt="pay로 결제하기"
                className="payment-image"
              />
            </button>
        </Link>
      </div>
    </div>
  );
};

export default Payment;
