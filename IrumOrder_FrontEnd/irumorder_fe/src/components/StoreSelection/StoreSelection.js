import React from 'react';
import './StoreSelection.css';
import Toolbar from '../Toolbar/Toolbar';

const StoreSelection = ({ onStoreSelect }) => {
  // handleCancel 함수 정의
  const handleCancel = () => {
    // 메인 페이지로 이동하는 기능
    window.location.href = '/';
  };

  return (
    <div className="store-selection-container">
      <Toolbar title="주문" onClose={handleCancel} />
      <h2 className="store-selection-prompt">주문할 카페를 선택하세요</h2>
      <div className="store-button-container">
        <button className="store-button" onClick={() => onStoreSelect('전농관')}>
          전농관
        </button>
        <button className="store-button" onClick={() => onStoreSelect('학생회관')}>
          학생회관
        </button>
      </div>
    </div>
  );
};

export default StoreSelection;
