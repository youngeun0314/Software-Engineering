import React from 'react';
import './Toolbar.css';

const Toolbar = ({ title, onClose }) => {
  return (
    <div className="toolbar">
      <span className="toolbar-title">{title}</span>
      <button className="toolbar-close" onClick={onClose}>
        &#x2715;
      </button>
    </div>
  );
};

export default Toolbar;
