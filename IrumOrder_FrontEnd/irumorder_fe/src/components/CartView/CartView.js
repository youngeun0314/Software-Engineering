import React, { useEffect, useState } from "react";
import { useNavigate, useLocation, useParams } from "react-router-dom";
import Toolbar from "./Toolbar";
import "./CartView.css";

const CartView = () => {
    const { store, userId } = useParams(); // store와 userId 가져오기
    const nav = useNavigate();
    const location = useLocation();

    // cartData 상태 관리
    const [cartData, setCartData] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0); // totalPrice 상태 추가

    // 데이터 변환 함수 (menuName과 price 추가)
    const transformData = (options) => {
        return {
            menuName: options.name,
            price: options.Price,
            orderMenuOptions: [
                {
                    menuId: options.menuId,
                    quantity: options.quantity,
                    menuOptions: { ...options.menuOptions },
                },
            ],
        };
    };

    // 로컬 스토리지에서 데이터 불러오기
    const loadFromWebStorage = (userId) => {
        try {
            const storedData = JSON.parse(localStorage.getItem(userId)) || [];
            console.log(`Loaded data for userId ${userId}:`, storedData);
            return storedData;
        } catch (error) {
            console.error("Error loading data from Web Storage:", error);
            return [];
        }
    };

    // 로컬 스토리지에 데이터 저장
    const saveToWebStorage = (userId, transformedData) => {
        try {
            const existingData = JSON.parse(localStorage.getItem(userId)) || [];
            // 중복 확인: 기존 데이터와 새 데이터를 비교
            const isDuplicate = existingData.some(
                (item) =>
                    item.menuName === transformedData.menuName &&
                    item.price === transformedData.price &&
                    JSON.stringify(item.orderMenuOptions) === JSON.stringify(transformedData.orderMenuOptions)
            );

            if (isDuplicate) {
                console.log("Duplicate entry detected. Skipping save.");
                return; // 중복된 데이터는 저장하지 않음
            }

            const updatedData = [...existingData, transformedData];
            localStorage.setItem(userId, JSON.stringify(updatedData));
            console.log("Saved to Web Storage for userId:", userId, updatedData);
        } catch (error) {
            console.error("Error saving to Web Storage:", error);
        }
    };

    // useEffect로 데이터 관리
    useEffect(() => {
        const options = location.state?.options;
        const fromOptionUnder = location.state?.fromOptionUnder || false;

        if (fromOptionUnder && options) {
            console.log("Saving to Web Storage:", options);
            const transformedData = transformData(options);
            saveToWebStorage(userId, transformedData);
        }

        const data = loadFromWebStorage(userId);
        setCartData(data);
    }, [location.state, userId]);

    // cartData가 업데이트될 때 totalPrice 계산
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

    // 항목 삭제 함수
    const handleRemoveItem = (index) => {
        const updatedCart = cartData.filter((_, i) => i !== index);
        setCartData(updatedCart);
        localStorage.setItem(userId, JSON.stringify(updatedCart));
    };

    // 수량 감소 함수
    const handleDecreaseQuantity = (index) => {
        const updatedCart = [...cartData];
        if (updatedCart[index].orderMenuOptions[0].quantity > 1) {
            updatedCart[index].orderMenuOptions[0].quantity -= 1;
            setCartData(updatedCart);
            localStorage.setItem(userId, JSON.stringify(updatedCart));
        }
    };

    // 수량 증가 함수
    const handleIncreaseQuantity = (index) => {
        const updatedCart = [...cartData];
        updatedCart[index].orderMenuOptions[0].quantity += 1;
        setCartData(updatedCart);
        localStorage.setItem(userId, JSON.stringify(updatedCart));
    };

    const handleNavigate = (destination) => {
        console.log("Navigating to:", destination);
        console.log("Cart Data:", cartData.map((item) => item.orderMenuOptions[0]));
        console.log("User ID:", userId);
        console.log("Total Price:", totalPrice);
        nav(destination, {
            state: {
                orderMenuOptions: cartData.map((item) => item.orderMenuOptions[0]),
                userId,
                totalPrice,
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
                    const orderOptions = item.orderMenuOptions[0]; // 첫 번째 옵션 데이터
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
                                            e.target.src = "/placeholder.png"; // 기본 회색 이미지 경로
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
                                        : "다회용컵 사용"}{" "}
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
                                    <span className="quantity-value">
                                        {orderOptions.quantity}
                                    </span>
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
                    <button className="cart-button" onClick={() => handleNavigate("/pay")}>
                        <img src="/go_pay.png" alt="바로 주문하기" className="button-image" />
                    </button>
                    <button className="cart-button" onClick={() => handleNavigate("/timeReservation")}>
                        <img src="/reserve_button.png" alt="예약하기" className="button-image" />
                    </button>
                </div>
            </div>
        </div>
    );
};

export default CartView;
