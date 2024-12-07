import React from "react";
import { useNavigate, useLocation, useParams } from "react-router-dom";
import Toolbar from "./Toolbar";
import "./CartView.css";

const CartView = (onSelectedStore) => {
    const {store}=useParams();
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
            <h3>옵션 데이터:</h3>
            <pre>{JSON.stringify(options, null, 2)}</pre> {/* JSON 형식으로 출력 */}
            </div>
        </div>
    );
};

export default CartView;
