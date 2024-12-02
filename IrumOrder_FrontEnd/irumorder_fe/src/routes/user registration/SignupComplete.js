import React from "react";
import "./SignupComplete.css";
import { Link } from 'react-router-dom';

function SignupComplete() {
  return (
    <div className="complete-container">
      <div className="complete-text">
        <p className="text1">회원가입이 끝났어요!</p>
        <p className="text2">이제부터 픽업 시스템을</p>
        <p className="text3">마음껏 사용할 수 있어요.</p>
      </div>
      <div className="check-image-container">
        <img
          src={`${process.env.PUBLIC_URL}/check.png`}
          alt="check"
          className="check-image"
          />
      </div>
      <div className="character-image-container">
        <img
          src={`${process.env.PUBLIC_URL}/Group 243.png`}
          alt="character"
          className="character-image"
          />
      </div>
      <div className="complete-button-container">
        <Link to={'/main'}>  
          <button type="complete" className="complete-button">
              <img
                src={`${process.env.PUBLIC_URL}/완료.png`}
                alt="완료"
                className="complete-image"
              />
            </button>
          </Link>
      </div>
      {/* <div className="payment-image-container">
        <Link to={'/paymentcomplete'}>
            <button type="pay" className="pay-button">
              <img
                src={`${process.env.PUBLIC_URL}/Group 2.png`}
                alt="pay로 결제하기"
                className="payment-image"
              />
            </button>
        </Link>
      </div> */}
    </div>
  );
};

export default SignupComplete;