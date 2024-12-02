import React from 'react';
import './StoreSelection.css';
import Toolbar from './Toolbar';

const StoreSelection = ({ onStartMenu }) => {
  // handleCancel 함수 정의
  const handleCancel = () => {
    // 메인 페이지로 이동하는 기능
    window.location.href = '/';
  };

  return (
    <div className="StoreSelection">
      <Toolbar title="주문" onClose={handleCancel} />
      <h2 className="prompt">주문할 카페를 선택하세요</h2>
      <div className="button_container">
        <button className="store_button" onClick={() => onStartMenu('전농관')}>
          전농관
        </button>
        <button className="store_button" onClick={() => onStartMenu('학생회관')}>
          학생회관
        </button>
      </div>
    </div>
  );
};

export default StoreSelection;
