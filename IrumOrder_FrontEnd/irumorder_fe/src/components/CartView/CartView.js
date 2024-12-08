import React, { useEffect, useState } from "react";
import { useNavigate, useLocation, useParams } from "react-router-dom";
import Toolbar from "./Toolbar";
import "./CartView.css";

const CartView = () => {
  const { store, userId } = useParams(); // store와 userId 가져오기
  const nav = useNavigate();
  const location = useLocation();

  const [cartData, setCartData] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);

  // 데이터 변환 함수
  const transformData = (options) => ({
    menuName: options.name,
    price: options.Price,
    orderMenuOptions: [
      {
        menuId: options.menuId,
        quantity: options.quantity,
        menuOptions: { ...options.menuOptions },
      },
    ],
  });

  // 로컬 스토리지 데이터 로드
  const loadFromWebStorage = (userId) => {
    try {
      const storedData = JSON.parse(localStorage.getItem(userId)) || [];
      return storedData;
    } catch (error) {
      console.error("Error loading data from Web Storage:", error);
      return [];
    }
  };

  // 로컬 스토리지 데이터 저장
  const saveToWebStorage = (userId, transformedData) => {
    try {
      const existingData = loadFromWebStorage(userId);
      const isDuplicate = existingData.some(
        (item) =>
          item.menuName === transformedData.menuName &&
          item.price === transformedData.price &&
          JSON.stringify(item.orderMenuOptions) ===
            JSON.stringify(transformedData.orderMenuOptions)
      );
      if (!isDuplicate) {
        const updatedData = [...existingData, transformedData];
        localStorage.setItem(userId, JSON.stringify(updatedData));
      }
    } catch (error) {
      console.error("Error saving to Web Storage:", error);
    }
  };

  useEffect(() => {
    const options = location.state?.options;
    const fromOptionUnder = location.state?.fromOptionUnder || false;

    if (fromOptionUnder && options) {
      const transformedData = transformData(options);
      saveToWebStorage(userId, transformedData);
    }

    setCartData(loadFromWebStorage(userId));
  }, [location.state, userId]);

  // 총 금액 계산
  useEffect(() => {
    const calculateTotalPrice = () => {
      const total = cartData.reduce(
        (sum, item) =>
          sum + item.orderMenuOptions[0].quantity * item.price,
        0
      );
      setTotalPrice(total);
    };

    calculateTotalPrice();
  }, [cartData]);

  // 항목 삭제
  const handleRemoveItem = (index) => {
    const updatedCart = cartData.filter((_, i) => i !== index);
    setCartData(updatedCart);
    localStorage.setItem(userId, JSON.stringify(updatedCart));
  };

  // 수량 감소
  const handleDecreaseQuantity = (index) => {
    const updatedCart = [...cartData];
    if (updatedCart[index].orderMenuOptions[0].quantity > 1) {
      updatedCart[index].orderMenuOptions[0].quantity -= 1;
      setCartData(updatedCart);
      localStorage.setItem(userId, JSON.stringify(updatedCart));
    }
  };

  // 수량 증가
  const handleIncreaseQuantity = (index) => {
    const updatedCart = [...cartData];
    updatedCart[index].orderMenuOptions[0].quantity += 1;
    setCartData(updatedCart);
    localStorage.setItem(userId, JSON.stringify(updatedCart));
  };

  // 네비게이션 핸들러
  const handleNavigate = (destination, fromCartView = false) => {
    nav(destination, {
      state: {
        orderMenuOptions: cartData.map((item) => item.orderMenuOptions[0]),
        userId,
        totalPrice,
        fromCartView, // CartView에서 요청 시 true로 설정
      },
    });
  };

  if (!cartData || cartData.length === 0) {
    return (
      <div className="CartView">
        <div className="cart-tool">
          <Toolbar title="장바구니" onBack={() => nav(-1)} />
        </div>
        <div className="empty-cart">
          <img
            src="/images/cart.png"
            alt="빈 장바구니"
            className="empty-cart-image"
          />
          <p className="empty-cart-text">장바구니가 비었어요</p>
        </div>
      </div>
    );
  }

  return (
    <div className="CartView">
      <div className="cart-tool">
        <Toolbar title="장바구니" onBack={() => nav(-1)} />
      </div>
      <div className="cart-view">
        {cartData.map((item, index) => {
          const orderOptions = item.orderMenuOptions[0];
          return (
            <div className="menu-card" key={index}>
              <div className="menu-card-header">
                {item.menuId ? (
                  <img
                    src={`/images/menu_${item.menuId}.png`}
                    alt={item.menuName || "메뉴 이미지"}
                    className="menu-image"
                    onError={(e) => {
                      e.target.onerror = null;
                      e.target.src = "/placeholder.png";
                    }}
                  />
                ) : (
                  <div className="placeholder-image">이미지 없음</div>
                )}
                <button
                  className="remove-button"
                  onClick={() => handleRemoveItem(index)}
                >
                  <img src="/x_button.png" alt="삭제" className="button-image" />
                </button>
              </div>

              <div className="menu-card-content">
                <h3 className="menu-name">{item.menuName || "메뉴 이름 없음"}</h3>
                <p className="menu-option">
                  {orderOptions.menuOptions?.useCup === "TakeOut"
                    ? "일회용컵 사용"
                    : "다회용컵 사용"}
                  {orderOptions.menuOptions?.light && ", 연하게"}
                  {orderOptions.menuOptions?.addShot && ", 샷 추가"}
                  {orderOptions.menuOptions?.addVanilla && ", 바닐라 시럽 추가"}
                  {orderOptions.menuOptions?.addHazelnut && ", 헤이즐넛 시럽 추가"}
                </p>
                <div className="menu-actions">
                  <button
                    className="quantity-button"
                    onClick={() => handleDecreaseQuantity(index)}
                  >
                    <img
                      src="/-_button.png"
                      alt="감소"
                      className="button-image"
                    />
                  </button>
                  <span className="quantity-value">{orderOptions.quantity}</span>
                  <button
                    className="quantity-button"
                    onClick={() => handleIncreaseQuantity(index)}
                  >
                    <img
                      src="/+_button.png"
                      alt="증가"
                      className="button-image"
                    />
                  </button>
                  <div className="menu-price">
                    {orderOptions.quantity * item.price}원
                  </div>
                </div>
              </div>
            </div>
          );
        })}
      </div>
      <div className="cart-summary">
        <div className="total-amount">
          결제 금액 <span className="total-price">{totalPrice.toLocaleString()}원</span>
        </div>
        <div className="cart-buttons">
          <button
            className="cart-button"
            onClick={() => handleNavigate("/pay", true)}
          >
            <img src="/go_pay.png" alt="바로 주문하기" className="button-image" />
          </button>
          <button
            className="cart-button"
            onClick={() => handleNavigate("/timeReservation")}
          >
            <img src="/reserve_button.png" alt="예약하기" className="button-image" />
          </button>
        </div>
      </div>
    </div>
  );
};

export default CartView;
