import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getUserId, setUserId } from '../context/userStorage';
import './Main.css';

function Main() {
    const [nickname, setNickname] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchNickname = async () => {
            const fetchedNickname = `${getUserId()}`; // 닉네임 대신 userId 사용
            setNickname(fetchedNickname);
        };

        fetchNickname();
    }, []);

    const handleLogout = () => {
        setUserId(null); // userId 초기화
        navigate('/'); // 로그아웃 후 로그인 페이지로 이동
    };

    return (
        <div className="main-container">
            <header className="logo-header">
                <div className="logo">
                    <img
                        src={`irum_title_black.png`}
                        alt="irum_title_black"
                        className="logo-image"
                    />
                </div>
            </header>
            <div className="banner-image-container">
                <img src="banner.png" alt="banner" className="banner-image" />
            </div>
            <div className="welcome-box">
                <div className="profile">
                    <img src="profile.png" alt="profile" className="profile-image" />
                    <p className="welcome-message">{nickname}님 환영해요!</p>
                    {/* 로그아웃 버튼 */}
                    <button onClick={handleLogout} className="logout-button">
                        로그아웃
                    </button>
                </div>
                <div className="main-options">
                    <Link to={'/store'}>
                        <button type="main_order" className="main-button">
                            <img src="main_order.png" alt="main_order" className="main-image" />
                        </button>
                    </Link>
                    <Link to={'/routinelist'}>
                        <button type="main_routine" className="main-button">
                            <img src="main_routine.png" alt="main_routine" className="main-image" />
                        </button>
                    </Link>
                    <Link to={'/check'}>
                        <button type="main_past_order" className="main-button">
                            <img src="main_past_order.png" alt="main_past_order" className="main-image" />
                        </button>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default Main;
