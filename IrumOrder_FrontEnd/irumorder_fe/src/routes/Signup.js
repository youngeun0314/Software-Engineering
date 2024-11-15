import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Signup.css';

function Signup() {
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');
    const [pwConfirm, setPwConfirm] = useState('');
    const [isIdValid, setIsIdValid] = useState(false);
    const navigate = useNavigate();

    const handleIdChange = (e) => {
        setId(e.target.value);
    };

    const handlePwChange = (e) => {
        setPw(e.target.value);
    };

    const handlePwConfirmChange = (e) => {
        setPwConfirm(e.target.value);
    };

    const checkIdAvailability = async () => {
        // Check if the ID is available by sending a request to the server
        try {
            const response = await fetch(`https://your-backend-url.com/api/check-id?userId=${id}`);
            const data = await response.json();
            if (data.available) {
                setIsIdValid(true);
                alert('아이디 사용 가능');
            } else {
                setIsIdValid(false);
                alert('아이디가 이미 존재합니다.');
            }
        } catch (error) {
            console.error('아이디 중복 확인 오류:', error);
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (pw !== pwConfirm) {
            alert('비밀번호가 일치하지 않습니다.');
            return;
        }

        // POST request to register the new user
        try {
            const response = await fetch('https://your-backend-url.com/api/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id,
                    pw,
                }),
            });

            if (response.ok) {
                alert('회원가입이 완료되었습니다.');
                navigate('/login'); // Redirect to login after successful signup
            } else {
                console.error('회원가입 실패:', response.statusText);
                alert('회원가입에 실패했습니다. 다시 시도해주세요.');
            }
        } catch (error) {
            console.error('서버와의 통신 오류:', error);
            alert('서버와의 통신 오류가 발생했습니다. 나중에 다시 시도해주세요.');
        }
    };

    const handleBackClick = () => {
        navigate(-1);  // 이전 페이지로 이동
    };

    return (
        <div className='signup-container'>
            <header className='signup-header'>
                <button onClick={handleBackClick} className='back-button'>{'<-'}</button>
            </header>
            <div className='signup-content'>
                <h2 className='signup-guide'>먼저 아이디와 비밀번호를 입력해주세요</h2>
                <form onSubmit={handleSubmit} className='signup-form'>
                    <label htmlFor="id">아이디</label>
                    <input
                        type="text"
                        id="id"
                        placeholder="아이디를 입력하세요."
                        value={id}
                        onChange={handleIdChange}
                    />
                    <button type="button" onClick={checkIdAvailability} className='id-check-button'>아이디 중복 확인</button>

                    <label htmlFor="pw">비밀번호</label>
                    <input
                        type="password"
                        id="pw"
                        placeholder="비밀번호를 입력하세요."
                        value={pw}
                        onChange={handlePwChange}
                    />

                    <label htmlFor="pwConfirm">비밀번호 확인</label>
                    <input
                        type="password"
                        id="pwConfirm"
                        placeholder="비밀번호를 다시 입력하세요."
                        value={pwConfirm}
                        onChange={handlePwConfirmChange}
                    />

                    <button type="submit" className='signup-button'>완료</button>
                </form>
            </div>
        </div>
    );
}

export default Signup;
