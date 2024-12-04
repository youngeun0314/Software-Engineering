import React, { useEffect, useState } from 'react';
import './MenuGrid.css';
import axios from 'axios'; // HTTP 요청을 위해 axios 사용

const MenuGrid = ({ category }) => {
const [menuItems, setMenuItems] = useState([]);
const [loading, setLoading] = useState(true);
const [error, setError] = useState(null);

// 메뉴 데이터 가져오기
useEffect(() => {
    const fetchMenuItems = async () => {
    try {
        // API 호출: category는 categoryId를 전달받는 것으로 가정
        const response = await axios.get(`http://localhost:8080/menu/getMenu`, {
        params: { categoryId: category },
        });

        // 서버 응답 데이터를 상태에 저장
        setMenuItems(response.data);
    } catch (err) {
        console.error('메뉴 데이터를 가져오는 중 오류가 발생했습니다.', err);
        setError('메뉴 데이터를 가져오는 중 오류가 발생했습니다.');
    } finally {
        setLoading(false); // 로딩 상태 업데이트
    }
    };

    if (category) {
    fetchMenuItems(); // category가 있을 때만 호출
    }
}, [category]);

if (loading) {
    return <div className="menu-grid">로딩 중...</div>; // 로딩 중 메시지
}

if (error) {
    return <div className="menu-grid error">{error}</div>; // 오류 메시지
}

return (
    <div className="menu-grid">
    {menuItems.length > 0 ? (
        menuItems.map((item) => (
        <button key={item.id} className="menu-item">
            <div className="menu-image">
            {item.image ? (
                <img src={item.image} alt={item.name} />
            ) : (
                <div className="placeholder">이미지 없음</div> // 이미지가 없을 때 대체 UI
            )}
            </div>
            <div className="menu-info">
            <span>{item.name}</span>
            <strong>{item.price.toLocaleString()}원</strong> {/* 가격 표시 */}
            </div>
        </button>
        ))
    ) : (
        <div className="no-menu-items">해당 카테고리에 메뉴가 없습니다.</div>
    )}
    </div>
);
};

export default MenuGrid;
