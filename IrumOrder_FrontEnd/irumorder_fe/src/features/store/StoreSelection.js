import React from 'react';
import { useNavigate } from 'react-router-dom';
import './StoreSelection.css';
import Toolbar from '../../shared/ui/ToolbarClose';

const StoreSelection = ({ onStartMenu }) => {
  const navigate = useNavigate(); // useNavigate 훅 사용

  // handleCancel 함수 정의
  const handleCancel = () => {
    // 메인 페이지로 이동 (새로고침 방지)
    navigate('/main');
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
