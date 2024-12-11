import React from 'react';
import './ToolbarBack.css';

const Toolbar = ({ title, onBack }) => {
    return (
        <div className="Toolbar">
            <button className="back" onClick={onBack}>
                <img src="/images/back_button.png" alt="뒤로가기" className="back_icon" />
            </button>
            <span className="title">{title}</span>
        </div>
    );
};

export default Toolbar;
