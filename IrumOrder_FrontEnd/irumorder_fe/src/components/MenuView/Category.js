import React, { useEffect, useState } from 'react';
import './Category.css';

const Category = () => {
    const [categories, setCategories] = useState([]);

  // 예시 데이터 (서버에서 가져온 데이터라고 가정)
    useEffect(() => {
    // 여기서는 간단하게 하드코딩된 데이터를 사용했지만, 실제로는 API 호출로 가져올 수 있습니다.
    const fetchCategories = async () => {
      
      //const response = await fetch('/api/categories');
      //const data = await response.json();
    setCategories(data);

    const data = ['커피', '논 커피', '티/에이드', '프라페/블렌디드', '디저트']; // 임시 데이터 
    setCategories(data);
    };
    
    fetchCategories();
}, []);

return (
    <div className="Category">
    {categories.map((category, index) => (
        <button key={index} className="button">
        {category}
        </button>
    ))}
    </div>
);
};

export default Category;
