import React from 'react';
import './ToolbarCartBack.css';

const Toolbar = ({ title, onBack, onCart }) => {
    return (
        <div className="Toolbar">
            <button className="back" onClick={onBack}>
            <img src="/images/back_button.png" alt="뒤로가기" className="back_icon" />
            </button>
            <span className="title">{title}</span>
            <button className="cart" onClick={onCart}>
                <img src="/images/cart.png" alt="장바구니" className="cart_icon" />
            </button>
        </div>
    );
};

export default Toolbar;
