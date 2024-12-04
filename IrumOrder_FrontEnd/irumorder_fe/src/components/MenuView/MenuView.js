import React, { useState, useEffect } from 'react';
import './MenuView.css';
import Toolbar from './Toolbar';
import Category from './Category';
import MenuGrid from './MenuGrid';
import { useNavigate, useParams } from 'react-router-dom';

const Menu = () => {
const [selectedCategory, setSelectedCategory] = useState(null); // 선택된 카테고리
const nav = useNavigate();
const { store, categoryId } = useParams(); // URL에서 categoryId를 가져옴 (옵션)


useEffect(() => {
    if (categoryId) {
      setSelectedCategory(parseInt(categoryId)); // URL에서 가져온 categoryId를 초기값으로 설정
    } else {
        
      setSelectedCategory(null); // 기본 카테고리 ID 설정 ()
    }
}, [categoryId]);


const handleBack = () => {
    nav(-1);
};

const handleCart = () => {
    nav('/cart');
};

const handleCategorySelect = (categoryId) => {
    setSelectedCategory(categoryId); // 선택된 카테고리를 상태로 설정
};


return (
    <div className="Menu">
    <Toolbar title="주문" onBack={handleBack} onCart={handleCart} />
    <div className="header">
        <h2>주문할 메뉴를 선택하세요.</h2>
        <div className="store">
        <img src="/images/location.png" alt="매장 위치" className="location-icon" />
        <span>{store}</span>
        </div>
    </div>
    <Category onCategorySelect={handleCategorySelect} /> {/* 카테고리 선택 */}
    {selectedCategory && <MenuGrid category={selectedCategory} />} {/* 선택된 카테고리 메뉴 표시 */}
    </div>
);
};

export default Menu;
