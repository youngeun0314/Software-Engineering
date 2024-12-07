import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Cart.css";

function Cart() {
  const [items, setItems] = useState([
    {
      id: 1,
      name: "(ICE) 아메리카노",
      price: 1600,
      quantity: 1,
      option: "일회용컵 사용, 연하게",
      image: `${process.env.PUBLIC_URL}/아메리카노.png`, // 메뉴 이미지
    },
    {
      id: 2,
      name: "(ICE) 아메리카노",
      price: 1600,
      quantity: 1,
      option: "일회용컵 사용, 연하게",
      image: `${process.env.PUBLIC_URL}/아메리카노.png`, // 메뉴 이미지
    },
  ]);

  // 수량 증가
  const incrementQuantity = (id) => {
    setItems((prevItems) =>
      prevItems.map((item) =>
        item.id === id
          ? { ...item, quantity: item.quantity + 1 }
          : item
      )
    );
  };

  // 수량 감소
  const decrementQuantity = (id) => {
    setItems((prevItems) =>
      prevItems.map((item) =>
        item.id === id && item.quantity > 1
          ? { ...item, quantity: item.quantity - 1 }
          : item
      )
    );
  };

  // 메뉴 삭제
  const removeItem = (id) => {
    setItems((prevItems) => prevItems.filter((item) => item.id !== id));
  };

  // 총 결제 금액 계산
  const totalAmount = items.reduce(
    (total, item) => total + item.price * item.quantity,
    0
  );

  return (
    <div className="payment-container">
      <header className="payment-header">
        <button className="back-button">
          <img
            src={`${process.env.PUBLIC_URL}/ep_back.png`}
            alt="go to back"
            className="back-image"
          />
        </button>
        <div className="payment-text">
          <p>장바구니</p>
        </div>
      </header>

      {items.length > 0 ? (
        <>
          <div className="card-container">
            {items.map((item) => (
              <div className="menu-card" key={item.id}>
                <div className="remove-button-container">
                  <button
                    onClick={() => removeItem(item.id)}
                    className="remove-button"
                  >
                  <img
                      src={`${process.env.PUBLIC_URL}/x버튼.png`}
                      alt="-"
                      className="remove-image"
                    />
                  </button>
                </div>
              <div className="menu-info-container">
                <img src={item.image} alt={item.name} className="menu-image" />
                <div className="menu-info">
                  <p className="menu-name">{item.name}</p>
                  <p className="menu-option">{item.option}</p>
                </div>
              </div>
              <div className="menu-bottom">
                <div className="menu-actions">
                  <button onClick={() => decrementQuantity(item.id)}><img
                      src={`${process.env.PUBLIC_URL}/-버튼.png`}
                      alt="-"
                      className="minus-image"
                    /></button>
                  <span className="menu-quantity">{item.quantity}</span>
                  <button onClick={() => incrementQuantity(item.id)}><img
                      src={`${process.env.PUBLIC_URL}/+버튼.png`}
                      alt="+"
                      className="plus-image"
                    /></button>
                </div>
                <div className="menu-price-container">
                  <p className="menu-price">{item.price * item.quantity}원</p>
                </div>
              </div>
            </div>
            ))}
          </div>

          <div className="divider"></div>
          <div className="payment-summary">
            <p>결제 금액</p>
            <p className="payment-amount">
              {totalAmount.toLocaleString()}원
            </p>
          </div>
          <div className="line-divider"></div>
          <div className="button-container">
            <Link to={"/payment"}>
              <button type="button" className="now-order-button">
                <img
                  src={`${process.env.PUBLIC_URL}/바로 주문하기.png`}
                  alt="바로 주문하기"
                  className="now-order-image"
                />
              </button>
            </Link>
            <button type="button" className="reservation-button">
              <img
                src={`${process.env.PUBLIC_URL}/예약하기.png`}
                alt="예약하기"
                className="reservation-image"
              />
            </button>
          </div>
        </>
      ) : (
        <div className="empty-cart">
          <img
            src={`${process.env.PUBLIC_URL}/cart.png`}
            alt="장바구니 비었음"
            className="empty-cart-image"
          />
          <p>장바구니가 비었어요</p>
        </div>
      )}
    </div>
  );
}

export default Cart;
