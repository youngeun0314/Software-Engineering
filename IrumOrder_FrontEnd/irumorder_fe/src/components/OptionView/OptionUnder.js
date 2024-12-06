import React, {useState} from 'react';
import './OptionUnder.css';

const OptionUnder = ({ }) => {//여기서 가격 받아서
return (
    <div className="OptionUnder">
    <h3>수량</h3>
        <button>-</button>
        <span></span>/객체 점표기법으로 가져오기
        <button>+</button>
        <div>
            <button></button>//가격 표시해줘야함
        </div>
    </div>
);
}

export default OptionUnder;