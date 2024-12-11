/**
 * 파일: Main.js
 * 설명: 메인 페이지 컴포넌트로, 사용자 환영 메시지, 주요 메뉴 네비게이션, 그리고 로그아웃 기능을 제공
 * 작성자: 이희진
 * 마지막 수정일: 2024-12-9
 */

import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getUserId, setUserId } from '../context/userStorage';
import './Main.css';

function Main() {
    const [nickname, setNickname] = useState(''); // 사용자 닉네임 상태
    const navigate = useNavigate(); // 페이지 이동을 위한 hook

    useEffect(() => {
        /**
         * 사용자 닉네임을 가져오는 비동기 함수
         */
        const fetchNickname = async () => {
            const fetchedNickname = `${getUserId()}`; // 닉네임 대신 userId를 가져옴
            setNickname(fetchedNickname); // 닉네임 상태 업데이트
        };

        fetchNickname(); // 닉네임 데이터 가져오기
    }, []);

    /**
     * 로그아웃 처리 함수
     * 
     * @return void
     */
    const handleLogout = () => {
        setUserId(null); // userId를 null로 설정하여 로그아웃 처리
        navigate('/'); // 로그인 페이지로 이동
    };

    return (
        <div className="main-container">
            <header className="logo-header">
                <div className="logo">
                    <img
                        src={`irum_title_black.png`} // 로고 이미지 경로
                        alt="irum_title_black"
                        className="logo-image"
                    />
                </div>
            </header>
            <div className="banner-image-container">
                <img src="banner.png" alt="banner" className="banner-image" /> {/* 배너 이미지 */}
            </div>
            <div className="welcome-box">
                <div className="profile">
                    <img src="profile.png" alt="profile" className="profile-image" /> {/* 프로필 이미지 */}
                    <p className="welcome-message">{nickname}님 환영해요!</p> {/* 사용자 환영 메시지 */}
                    {/* 로그아웃 버튼 */}
                    <button onClick={handleLogout} className="logout-button">
                        로그아웃
                    </button>
                </div>
                <div className="main-options">
                    {/* 주요 메뉴 버튼 */}
                    <Link to={'/store'}>
                        <button type="main_order" className="main-button">
                            <img src="main_order.png" alt="main_order" className="main-image" /> {/* 메뉴 주문 */}
                        </button>
                    </Link>
                    <Link to={'/routinelist'}>
                        <button type="main_routine" className="main-button">
                            <img src="main_routine.png" alt="main_routine" className="main-image" /> {/* 루틴 관리 */}
                        </button>
                    </Link>
                    <Link to={'/check'}>
                        <button type="main_past_order" className="main-button">
                            <img src="main_past_order.png" alt="main_past_order" className="main-image" /> {/* 이전 주문 내역 확인 */}
                        </button>
                    </Link>
                </div>
            </div>
        </div>
    );
}

export default Main;