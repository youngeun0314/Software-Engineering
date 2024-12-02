import React from 'react';
import './Toolbar.css';

const Toolbar = ({ title, onBack, onCart }) => {
    return (
        <div className="Toolbar">
            <button className="back" onClick={onBack}>
                &#x25c0;
            </button>
            <span className="title">{title}</span>
        </div>
    );
};

export default Toolbar;