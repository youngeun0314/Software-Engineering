import React from "react";
import { useNavigate, useLocation } from "react-router-dom";
import Toolbar from "./Toolbar";
import "./CartView.css";

const CartView = () => {
    const nav = useNavigate();
    const location = useLocation();
    const options = location.state?.options;

    if (!options) {
        return <div>옵션 데이터가 없습니다.</div>;
    }

    return (
        <div className="CartView">
            {/* 고정된 툴바 */}
            <div className="cart-tool">
                <Toolbar title="장바구니" onBack={() => nav(-1)} />
            </div>
            
            {/* 장바구니 내용 */}
            <div className="cart-view">
                <p>유저 ID: {options.userId}</p>
                <p> 메뉴 이름: {options.name}</p>
                <p>메뉴 ID: {options.menuId}</p>
                <p>수량: {options.quantity}</p>
                <p>총 금액: {options.quantity * (options.Price || 0)}원</p>
            </div>
        </div>
    );
};

export default CartView;
