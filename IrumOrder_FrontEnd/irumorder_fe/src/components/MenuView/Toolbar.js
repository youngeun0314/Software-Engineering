import React from 'react';
import './Toolbar.css';

const Toolbar = ({ title, onBack, onCart }) => {
    return (
        <div className="Toolbar">
            <button className="back" onClick={onBack}>
                &#x25c0;
            </button>
            <span className="title">{title}</span>
            <button className="cart" onClick={onCart}>
                <img src="/images/cart.png" alt="장바구니" className="cart_icon" />
            </button>
        </div>
    );
};

export default Toolbar;
