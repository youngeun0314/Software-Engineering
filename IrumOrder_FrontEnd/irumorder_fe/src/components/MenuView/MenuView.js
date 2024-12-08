import React, { useState, useEffect } from 'react';
import './MenuView.css';
import Toolbar from './Toolbar';
import Category from './Category';
import MenuGrid from './MenuGrid';
import { useNavigate, useParams } from 'react-router-dom';

const Menu = ({userId , onStartOption}) => {
    useEffect(() => {
        console.log("Menu received userId:", userId);
    }, [userId]);
    
    const [selectedCategory, setSelectedCategory] = useState(null); // 선택된 카테고리
    const {store, categoryId } = useParams(); // URL에서 categoryId를 가져옴 (옵션)
    const nav = useNavigate();

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
        nav(`/store/${store}/cart/${userId}`);
    };

    const handleCategorySelect = (categoryId) => {
        setSelectedCategory(categoryId); // 선택된 카테고리를 상태로 설정
    };

    useEffect(() => {
        console.log("Received userId in MenuView:", userId);
    }, [userId]);

    

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
        {selectedCategory && (
        <MenuGrid userId={userId} store={store} category={selectedCategory} onStartOption={onStartOption}/>)}
        </div>
    );
};

export default Menu;
