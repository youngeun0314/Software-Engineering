import React from 'react';
import { useState, useEffect } from 'react';
import './Main.css';
import { Link } from 'react-router-dom';
import { setUserId, getUserId } from '../context/userStorage';

function Main() {
    const [nickname, setNickname] = useState('');

    setUserId(1); //로그인api 작동 시 생략 가능

    useEffect(() => {
        
        const fetchNickname = async () => {
            const fetchedNickname = `${getUserId()}`;  // 백엔드에서 받아온 닉네임
            setNickname(fetchedNickname);
        };

        fetchNickname();
    }, []);

    return (
        <div className="main-container">
            <header className="logo-header">
                <div className="logo">
                    <img
                    src={`irum_title_black.png`}
                    alt="irum_title_black"
                    className="logo-image"
                    /></div>
            </header>
            {/* <div className='logo-header'>
                <div className="logo">
                    <img src="irum_title_black.png" alt="irum_title_black" className="logo-image"/>
                </div>
            </div> */}
            <div className="banner-image-container">
                <img src="banner.png" alt="banner" className="banner-image"/>
            </div>
            <div className="welcome-box">
                <div className="profile">
                    <img src="profile.png" alt="profile" className="profile-image"/>
                    <p className="welcome-message">{nickname}님 환영해요!</p>
                </div>
                <div className="main-options">
                    <Link to={'/store'}>
                        <button type="main_order" className="main-button">
                            <img src="main_order.png" alt="main_order" className="main-image"/>
                        </button>
                    </Link>
                    <Link to={'/routinelist'}>
                        <button type="main_routine" className="main-button">
                        <img src="main_routine.png" alt="main_routine" className="main-image"/>
                        </button>
                    </Link>
                    <Link to={'/check'}>
                        <button type="main_past_order" className="main-button">
                            <img src="main_past_order.png" alt="main_past_order" className="main-image"/>
                        </button>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default Main;