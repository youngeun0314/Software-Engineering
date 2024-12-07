import React from "react";
import "./Paymentcomplete.css";

function Paymentcomplete() {
  return (
    <div className="complete-container">
      <div className="complete-text">
        <p className="text1">주문이 정상적으로 완료되었어요!</p>
        <p className="text2">메뉴가 나올때까지 잠시만 기다려 주세요.</p>
      </div>
      <div className="check-image-container">
        <img
          src={`${process.env.PUBLIC_URL}/Group 119.png`}
          alt="check"
          className="check-image"
          />
      </div>
      <div className="character-image-container">
        <img
          src={`${process.env.PUBLIC_URL}/Group 241.png`}
          alt="character"
          className="character-image"
          />
      </div>
      <div className="complete-button-container">
        <button type="complete" className="complete-button">
              <img
                src={`${process.env.PUBLIC_URL}/버튼.png`}
                alt="완료"
                className="complete-image"
              />
            </button>
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

export default Paymentcomplete;
