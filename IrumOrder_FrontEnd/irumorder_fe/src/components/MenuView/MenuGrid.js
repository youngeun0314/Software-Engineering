import React, { useEffect, useState } from 'react';
import './MenuGrid.css';

const MenuGrid = ({ category }) => {
const [menuItems, setMenuItems] = useState([]);

// 메뉴 데이터 가져오기
useEffect(() => {
    const fetchMenuItems = async () => {
    // 실제로는 API 호출을 통해 데이터를 가져옵니다.
    // 예를 들어:
    // const response = await fetch(`/api/menu?category=${category}`);
    // const data = await response.json();
    // setMenuItems(data);

    // 임시 데이터 (DB에서 불러온 데이터라고 가정)
    const data = [
        { id: 1, name: '아메리카노', price: 1600, image: '/images/coffee.png' },
        { id: 2, name: '아메리카노', price: 1600, image: '' }, // 이미지 없는 경우
        { id: 3, name: '아메리카노', price: 1600, image: '' },
        { id: 4, name: '아메리카노', price: 1600, image: '' },
    ];
    setMenuItems(data);
    };

    fetchMenuItems();
}, [category]);

return (
    <div className="menu-grid">
    {menuItems.map((item) => (
        <button key={item.id} className="menu-item">
            <div className="menu-image">
                {item.image ? (
                <img src={item.image} alt={item.name} />
                ) : (
                <div className="placeholder"></div>
                )}
            </div>
            <div className="menu-info">
                <span>{item.name}</span>
                <strong>{item.price.toLocaleString()}원</strong>
            </div>
        </button>
    ))}
    </div>
);
};

export default MenuGrid;
