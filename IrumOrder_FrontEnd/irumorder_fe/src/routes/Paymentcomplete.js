import React from "react";
import "./Paymentcomplete.css";
import { Link } from 'react-router-dom';

function Paymentcomplete() {
  return (
    <div className="complete-container">
      <div className="complete-text">
        <p className="text1">주문이 정상적으로 완료되었어요!</p>
        <p className="text2">메뉴가 나올때까지 잠시만 기다려 주세요.</p>
      </div>
      <div className="check-image-container">
        <img
          src={`${process.env.PUBLIC_URL}/Group_119.png`}
          alt="check"
          className="check-image"
          />
      </div>
      <div className="character-image-container">
        <img
          src={`${process.env.PUBLIC_URL}/Group_241.png`}
          alt="character"
          className="character-image"
          />
      </div>
      <div className="complete-button-container">
        <Link to={'/main'}>
          <button type="complete" className="complete-button">
                <img
                  src={`${process.env.PUBLIC_URL}/complete.png`}
                  alt="완료"
                  className="complete-image"
                />
              </button>
            </Link>
      </div>
    </div>
  );
};

export default Paymentcomplete;
