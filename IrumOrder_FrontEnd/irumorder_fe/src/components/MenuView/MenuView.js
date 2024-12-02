import React from 'react';
import './MenuView.css';
import Toolbar from './Toolbar';
import Category from './Category';
import MenuGrid from './MenuGrid';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { useState } from 'react';


const Menu = () => {
    const [selectedStore, setSelectedStore] = useState('');
    const [selectedCategory, setSelectedCategory] = useState('커피'); // 초기 카테고리

    const nav = useNavigate();
    const { store } = useParams();
    
    const handleBack = () => {
        nav(-1);
    };
    const handleCart = () => {
        nav('/cart');
    };
    const handleCategorySelect = (category) => {
        setSelectedCategory(category);
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
            <Category onCategorySelect={handleCategorySelect} /> {/* 카테고리 선택 핸들러 추가 */}
            <MenuGrid category={selectedCategory} /> {/* 선택된 카테고리의 메뉴를 표시 */}
        </div>
    );
};

export default Menu;