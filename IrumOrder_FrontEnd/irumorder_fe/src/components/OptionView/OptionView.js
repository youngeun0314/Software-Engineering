import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Toolbar from './Toolbar';
import './OptionView.css';
import axios from 'axios';

const OptionView = ({ options = {}, setOptions, onStartCart }) => {
const { store, menuId } = useParams(); // URL에서 menuId 가져오기
const [menuDetails, setMenuDetails] = useState(null); // 메뉴 세부정보 상태
const [loading, setLoading] = useState(false); // 로딩 상태
const [error, setError] = useState(null); // 에러 상태
const nav = useNavigate();

useEffect(() => {
    if (menuId) {
    setOptions((prevOptions) => ({
        ...prevOptions,
        menuId: Number(menuId), // menuId를 숫자로 변환하여 추가
    }));
    }
}, [menuId, setOptions]);

// 메뉴 정보를 가져오는 함수
useEffect(() => {
    const fetchMenuDetails = async () => {
    setLoading(true); // 로딩 시작
    setError(null); // 에러 초기화
    try {
        const response = await axios.get(`http://localhost:8080/menu/getOneMenu`, {
        params: { menuId },
        });
        setMenuDetails(response.data); // 서버에서 가져온 데이터 저장
        // options에 Price 업데이트
        setOptions((prevOptions) => ({
        ...prevOptions,
        name: response.data.name,
        Price: response.data.price,
        }));
    } catch (err) {
        console.error('Error fetching menu details:', err);
        setError('메뉴 정보를 가져오는 중 오류가 발생했습니다.');
    } finally {
        setLoading(false); // 로딩 종료
    }
    };

    if (menuId) {
    fetchMenuDetails(); // menuId가 있는 경우 데이터 가져오기
    }
}, [menuId, setOptions]);

const handleBack = () => {
    setOptions((prevOptions) => ({
    ...prevOptions,
    quantity: 1, // 수량 초기화
    menuOptions: {
        useCup: "", // 컵 옵션 초기화
        addShot: false, // 샷 추가 초기화
        addVanilla: false, // 바닐라 시럽 초기화
        addHazelnut: false, // 헤이즐넛 시럽 초기화
        light: false, // 연하게 초기화
    },
    }));
    nav(-1);
};

const handleCupSelection = (cupType) => {
    setOptions((prevOptions) => ({
    ...prevOptions,
    menuOptions: {
        ...prevOptions.menuOptions,
        useCup: cupType, // useCup 값 업데이트
    },
    }));
};

const handleOptionClick = (option) => {
    setOptions((prevOptions) => ({
    ...prevOptions,
    menuOptions: {
        ...prevOptions.menuOptions,
        [option]: !prevOptions.menuOptions[option], // 현재 상태를 반대로 토글
    },
    }));
};

// 가격 계산 함수
const calculatePrice = () => {
    const basePrice = options.Price || 0;
    const addShotPrice = options.menuOptions.addShot ? 500 : 0;
    const addVanillaPrice = options.menuOptions.addVanilla ? 500 : 0;
    const addHazelnutPrice = options.menuOptions.addHazelnut ? 500 : 0;
    return basePrice + addShotPrice + addVanillaPrice + addHazelnutPrice;
};

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

const handleCartClick = () => {
    onStartCart(options);
};

if (loading) {
    return <div>로딩 중...</div>; // 로딩 상태 표시
}

if (error) {
    return <div>{error}</div>; // 에러 메시지 표시
}

if (!options || !options.menuOptions) {
    return <div>옵션 데이터를 불러오는 중입니다...</div>;
}

return (
    <>
    <Toolbar title="메뉴 상세" onBack={handleBack} />
    <div className="OptionView">
        {menuDetails ? (
        <div className="option-container">
            {/* 메뉴 이름 */}
            <h2 className="menu-name">{menuDetails.name}</h2>
            {/* 이미지 표시 */}
            {menuDetails.image ? (
            <img
                src={menuDetails.image}
                alt={menuDetails.name}
                className="menu-image"
            />
            ) : (
            <div className="image-placeholder">이미지 없음</div>
            )}
            {/* 카테고리 */}
        </div>
        ) : (
        <p className="error-message">메뉴 정보를 불러오지 못했습니다.</p>
        )}
    </div>

    <div className="OptionView2">
        <h3 className="choose-cup">컵 선택</h3>
        <div className="add-cup">
        <button
            className={options.menuOptions.useCup === 'TakeIn' ? 'selected' : ''}
            onClick={() => handleCupSelection('TakeIn')}
        >
            매장 내 다회용컵
        </button>
        <button
            className={options.menuOptions.useCup === 'TakeOut' ? 'selected' : ''}
            onClick={() => handleCupSelection('TakeOut')}
        >
            일회용컵
        </button>
        </div>
        <h3 className="choose-option">추가옵션</h3>
        <div className="add-option">
        <button
            className={options.menuOptions.light ? 'selected' : ''}
            onClick={() => handleOptionClick('light')}
        >
            연하게(+0)
        </button>
        <button
            className={options.menuOptions.addShot ? 'selected' : ''}
            onClick={() => handleOptionClick('addShot')}
        >
            샷 추가(+500)
        </button>
        <button
            className={options.menuOptions.addVanilla ? 'selected' : ''}
            onClick={() => handleOptionClick('addVanilla')}
        >
            바닐라 시럽(+500)
        </button>
        <button
            className={options.menuOptions.addHazelnut ? 'selected' : ''}
            onClick={() => handleOptionClick('addHazelnut')}
        >
            헤이즐넛 시럽(+500)
        </button>
        </div>
    </div>
    <div className='container-divider'></div>
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
    </>
);
};

export default OptionView;
