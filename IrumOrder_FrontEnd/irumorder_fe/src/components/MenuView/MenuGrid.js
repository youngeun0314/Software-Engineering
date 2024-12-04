import React, { useEffect, useState } from 'react';
import axios from 'axios'; // HTTP 요청을 위해 axios 사용
import './MenuGrid.css';

const MenuGrid = ({ category }) => {
const [menuItems, setMenuItems] = useState([]);
const [loading, setLoading] = useState(false);
const [error, setError] = useState(null);

useEffect(() => {
    if (category) {
    const fetchMenuItems = async () => {
        setLoading(true);
        setError(null);

        try {
        // 백엔드 API 호출
        const response = await axios.get('http://localhost:8080/menu/getMenu', {
            params: { categoryId: category }, // 카테고리 ID를 쿼리 파라미터로 전달
        });

        // 서버 응답 데이터를 상태에 저장
        setMenuItems(response.data);
        } catch (err) {
        console.error('Error fetching menu items:', err);
        setError('메뉴 데이터를 가져오는 중 오류가 발생했습니다.');
        } finally {
        setLoading(false); // 로딩 완료
        }
    };

    fetchMenuItems();
    }
}, [category]); // category가 변경될 때마다 데이터 갱신

if (loading) {
    return <div className="menu-loading"></div>; // 로딩 상태 표시
}

if (error) {
    return <div className="menu-error">{error}</div>; // 에러 메시지 표시
}

return (
    <>
    {menuItems.length > 0 ? (
        <div className="menu-grid">
        {menuItems.map((item) => (
            <div key={item.id} className="menu-item">
            <div className="menu-image">
                {item.image ? (
                <img src={item.image} alt={item.name} />
                ) : (
                <div className="placeholder">이미지 없음</div> // 이미지 없는 경우
                )}
            </div>
            <div className="menu-info">
                <span>{item.name}</span>
                <strong>{item.price.toLocaleString()}원</strong> {/* 가격 포맷팅 */}
            </div>
            </div>
        ))}
        </div>
    ) : (
        <div className="no-menu-items">해당 카테고리에 메뉴가 없습니다.</div>
    )}
    </>
);
};

export default MenuGrid;
