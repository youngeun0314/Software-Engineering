import axios from 'axios'; // axios로 API 호출
import React, { useEffect, useState } from 'react';
import './Category.css';

const Category = () => {
const [categories, setCategories] = useState([]);
const [loading, setLoading] = useState(true);
const [error, setError] = useState(null);

useEffect(() => {
    // API 호출 함수
    const fetchCategories = async () => {
    try {
        // 서버에서 카테고리 데이터를 가져옴
        const response = await axios.get('http://localhost:8080/category/getAllCategory');
        const sortedCategories = response.data.sort((a, b) => a.id - b.id);
        setCategories(sortedCategories); // 서버 응답 데이터를 상태에 저장
    } catch (err) {
        console.error('Error fetching categories:', err);
        setError('카테고리를 불러오는 데 실패했습니다.');
    } finally {
        setLoading(false); // 로딩 완료
    }
    };

    fetchCategories();
}, []);

if (loading) {
    return <div className="Category">로딩 중...</div>; // 로딩 상태 표시
}

if (error) {
    return <div className="Category">{error}</div>; // 에러 메시지 표시
}

return (
    <div className="Category">
    {categories.map((category, index) => (
    <button key={index} className="button">
      {category.name} {/* 객체의 name 필드를 렌더링 */}
    </button>
))}
</div>
);
};

export default Category;
