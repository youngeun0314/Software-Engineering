import React from 'react';
import { useState, useEffect } from 'react';
import './Main.css';
import { Link } from 'react-router-dom';

function Main() {
    const [nickname, setNickname] = useState('');

    useEffect(() => {
        // 실제 API 호출로 수정 필요
        const fetchNickname = async () => {
            const fetchedNickname = '이루매';  // 백엔드에서 받아온 닉네임
            setNickname(fetchedNickname);
        };

        fetchNickname();
    }, []);

    /*
    useEffect(() => {
        // 백엔드에서 닉네임을 받아오는 API 호출
        const fetchNickname = async () => {
            try {
                const response = await fetch('https://your-backend-url.com/api/get-nickname', {
                    method: 'GET',  // API 호출 방식 (GET, POST 등)
                    headers: {
                        'Content-Type': 'application/json',  // 응답 데이터 형식
                        // Authorization: `Bearer ${token}`,  // 인증이 필요한 경우 Authorization 헤더 추가
                    },
                });

                if (!response.ok) {
                    throw new Error('서버에서 닉네임을 가져오는 데 실패했습니다.');
                }

                const data = await response.json();
                setNickname(data.nickname);  // API에서 받은 닉네임을 상태로 설정
                setLoading(false);  // 로딩 완료
            } catch (error) {
                console.error('API 호출 에러:', error);
                setLoading(false);  // 로딩 완료 (실패해도 로딩 끝)
            }
        };

        fetchNickname();  // API 호출
    }, []);  // 컴포넌트가 마운트될 때 한 번만 호출

    if (loading) {
        return <div>로딩 중...</div>;  // 로딩 상태일 때 표시
    } 
    */

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