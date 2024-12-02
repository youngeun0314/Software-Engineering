import React from 'react';
import { useParams } from 'react-router-dom';

const OptionView = ({onStartOption}) => {
const { menuId } = useParams(); // URL에서 menuId 가져오기

return (
    <div>
    <h2>옵션 설정</h2>
    <p>선택한 메뉴 ID: {menuId}</p>
    {/* 여기서 menuId에 해당하는 메뉴의 옵션을 설정하도록 구현 */}
    </div>
);
};

export default OptionView;
