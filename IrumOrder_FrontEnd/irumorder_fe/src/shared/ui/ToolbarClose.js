import React from 'react';
import './ToolbarClose.css';

const Toolbar = ({ title, onClose }) => {
    return (
        <div className="Toolbar">
            <span className="title">{title}</span>
            <button className="close" onClick={onClose}>
                &#x2715;
            </button>
        </div>
    );
};

export default Toolbar;
