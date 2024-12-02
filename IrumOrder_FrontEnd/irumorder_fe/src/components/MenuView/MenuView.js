import React from 'react';
import './MenuView.css';
import Toolbar from './Toolbar';

const Menu = () => {
    const handleCancel = () => {
        window.location.href = '../';
    };

    return (
        <div>
            <Toolbar title="메뉴" onClose={handleCancel} />
        </div>
    )
}

export default Menu;