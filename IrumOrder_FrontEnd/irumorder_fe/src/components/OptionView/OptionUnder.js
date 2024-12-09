import React from 'react';
import './OptionUnder.css';

const OptionUnder = ({ store, options, setOptions, onStartCart }) => {
    const increaseQuantity = () => {
        setOptions((prevOptions) => ({
            ...prevOptions,
            quantity: prevOptions.quantity + 1,
        }));
    };
    const decreaseQuantity = () => {
        setOptions((prevOptions) => ({
            ...prevOptions,
            quantity: prevOptions.quantity > 1 ? prevOptions.quantity - 1 : 1,
        }));
    };
    const calculatePrice = () => {
        const basePrice = options.Price || 0;
        const addShotPrice = options.menuOptions.addShot ? 500 : 0;
        const addVanillaPrice = options.menuOptions.addVanilla ? 500 : 0;
        const addHazelnutPrice = options.menuOptions.addHazelnut ? 500 : 0;
        options.Price = basePrice + addShotPrice + addVanillaPrice + addHazelnutPrice;
        return basePrice + addShotPrice + addVanillaPrice + addHazelnutPrice;
    };

    const handleCartClick = () => {
        onStartCart(options);
    };

    return (
        <div className="OptionUnder">
            <div className="quantity">
                <h3>수량</h3>
                <div className="quantity-button">
                    <button onClick={decreaseQuantity}>-</button>
                    <span>{options.quantity}</span>
                    <button onClick={increaseQuantity}>+</button>
                </div>
            </div>
            <div className="gocart">
                <button onClick={handleCartClick}>
                    {calculatePrice() * options.quantity}원 담기
                </button>
            </div>
        </div>
    );
};

export default OptionUnder;
