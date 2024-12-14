/**
 * 파일: Signup.js
 * 설명: 사용자가 새로운 계정을 생성할 수 있도록 회원가입 절차를 관리하는 컴포넌트
 * 작성자: 이희진
 * 마지막 수정일: 2024-12-10
 */

import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Signup.css';

function Signup() {
    const [id, setId] = useState(''); // 사용자 ID 상태 관리
    const [pw, setPw] = useState(''); // 비밀번호 상태 관리
    const [pwConfirm, setPwConfirm] = useState(''); // 비밀번호 확인 상태 관리
    const [email, setEmail] = useState(''); // 이메일 상태 관리
    const [isIdValid, setIsIdValid] = useState(false); // ID 유효성 상태
    const [isPwValid, setPwValid] = useState(false); // 비밀번호 유효성 상태
    const navigate = useNavigate(); // 페이지 이동을 위한 Hook
    const [verificationCode, setVerificationCode] = useState(''); // 이메일 인증 코드 상태 관리
    const [isCodeSent, setIsCodeSent] = useState(false); // 이메일 인증 코드 전송 여부 상태
    const [isEmailVerified, setIsEmailVerified] = useState(false); // 이메일 인증 여부 상태
    const [isPwVisible, setIsPwVisible] = useState(false); // 비밀번호 표시 여부 상태
    const [isPwConfirmVisible, setIsPwConfirmVisible] = useState(false); // 비밀번호 확인 표시 여부 상태

    // 비밀번호 가시성 토글
    const handlePwVisibilityToggle = () => {
        setIsPwVisible(!isPwVisible);
    };

    // 비밀번호 확인 가시성 토글
    const handlePwConfirmVisibilityToggle = () => {
        setIsPwConfirmVisible(!isPwConfirmVisible);
    };

    // ID 입력 변경 핸들러
    const handleIdChange = (e) => {
        setId(e.target.value);
        setIsIdValid(false); // 유효성 초기화
    };

    // 이메일 입력 변경 핸들러
    const handleEmailChange = (e) => {
        setEmail(e.target.value);
        setIsEmailVerified(false); // 인증 초기화
    };

    /**
     * 아이디 중복 확인 요청
     *
     * @return void
     */
    const checkIdAvailability = async () => {
        const idPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z0-9]{4,20}$/;

        if (!idPattern.test(id)) {
            alert('아이디는 4자 이상 20자 이하의 영문과 숫자 조합이어야 합니다.');
            setId('');
            return;
        }

        // ID 중복 확인 요청
        try {
            const response = await fetch(`http://localhost:8080/auth/checkId?id=${id}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            if (!response.ok) {
                throw new Error("서버 오류가 발생했습니다.");
            }
        
            const message = await response.text(); // 서버 응답 메시지
            if (message === "사용 가능한 아이디입니다.") {
                setIsIdValid(true);
                alert(message); // 성공 메시지 알림
            } else {
                setIsIdValid(false);
                alert(message); // 실패 메시지 알림
            }
        } catch (error) {
            console.error('아이디 중복 확인 오류:', error);
            alert('사용할 수 없는 아이디 입니다.');
        }
    };

    /**
     * 이메일 인증 코드 전송
     *
     * @return void
     */
    const sendVerificationCode = async () => {
        if (!email) {
            alert('이메일을 입력해주세요.');
            return;
        }
    
        try {
            const response = await fetch(`http://localhost:8080/auth/sendEmailVerification?email=${email}`, { 
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
    
            if (response.ok) {
                alert('인증 코드가 이메일로 전송되었습니다.');
                setIsCodeSent(true);
            } else {
                alert('인증 코드 전송에 실패했습니다. 다시 시도해주세요.');
            }
        } catch (error) {
            console.error('인증 코드 전송 오류:', error);
            alert('서버와의 통신 오류가 발생했습니다.');
        }
    };
    
    /**
     * 인증 코드 확인 요청
     *
     * @return void
     */
    const verifyCode = async () => {
        if (!verificationCode) {
            alert('인증 코드를 입력해주세요.');
            return;
        }
    
        try {
            const response = await fetch(`http://localhost:8080/auth/verifyEmail?email=${email}&code=${verificationCode}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
    
            const responseText = await response.text(); // 응답 메시지 확인
            if (response.ok) {
                alert(responseText);
                setIsEmailVerified(true);
            } else {
                alert('인증 코드 확인에 실패했습니다. 다시 시도해주세요.');
            }
        } catch (error) {
            console.error('인증 코드 확인 오류:', error);
            alert('서버와의 통신 오류가 발생했습니다.');
        }
    };    

    /**
     * 회원가입 요청
     *
     * @param event 폼 제출 이벤트
     * @return void
     */
    const handleSubmit = async (event) => {
        event.preventDefault(); // 폼 기본 동작 방지
        if (!isIdValid) {
            alert('아이디 중복 확인을 해주십시오.');
            return;
        } 
        if (pw !== pwConfirm) {
            setPwValid(false);
            alert('비밀번호가 일치하지 않습니다.');
            return;
        } else {
            if (pw === '') {
                setPwValid(false);
                alert('비밀번호를 입력해주세요.');
                return;
            }
            setPwValid(true);
        }
        if (!isCodeSent) {
            alert('이메일 인증을 실시하십시오.');
            return;
        }
        if (!isEmailVerified) {
            alert('이메일 인증코드를 확인해 주십시오.');
            return;
        }

        // 회원가입 요청 전송
        try {
            const response = await fetch('http://localhost:8080/auth/signUp', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id,
                    password: pw, // 서버 요구 스키마에 맞춘 키
                    email,
                }),
            });

            if (response.ok) {
                navigate('/'); // 성공 시 로그인 페이지로 이동
            } else {
                console.error('회원가입 실패:', response.statusText);
                alert('회원가입에 실패했습니다. 중복된 이메일을 사용했습니다.');
            }
        } catch (error) {
            console.error('서버와의 통신 오류:', error);
            alert('서버와의 통신 오류가 발생했습니다. 나중에 다시 시도해주세요.');
        }
    };

    return (
        <div className='signup-container'>
            <header className='signup-header'>
                {/* 이전 페이지로 이동 */}
                <Link to={'/signupstart'}>
                    <button className="back-button">
                        <img
                            src={`${process.env.PUBLIC_URL}/back_button.png`}
                            alt="back"
                            className="back-image"
                        />
                    </button>
                </Link>
                <div className="signup-text">
                    <p>회원가입</p>
                </div>
                {/* 메인 페이지로 이동 */}
                <Link to={'/'}>
                    <button className="x-button">
                        <img
                            src={`${process.env.PUBLIC_URL}/x_button.png`}
                            alt="x"
                            className="x-image"
                        />
                    </button>
                </Link>
            </header>
            <div className='signup-content'>
                <div className='signup-guide'>
                    <img
                        src={`${process.env.PUBLIC_URL}/guide.png`}
                        alt="이룸오더는 간단하게 가입할 수 있어요."
                        className="guide-image"
                    />
                </div>
                <form onSubmit={handleSubmit} className='signup-form'>
                    {/* 아이디 입력 */}
                    <label htmlFor="id">아이디</label>
                    <input
                        type="text"
                        id="id"
                        placeholder="4자 이상 20자 이하 영문, 숫자 조합"
                        value={id}
                        onChange={handleIdChange}
                    />
                    <button type="button" onClick={checkIdAvailability} className='id-check-button'>중복 확인</button>

                    {/* 이메일 입력 */}
                    <label htmlFor="email">이메일</label>
                    <input
                        type="email"
                        id="email"
                        placeholder="이메일을 입력해주세요."
                        value={email}
                        onChange={handleEmailChange}
                    />
                    
                    {/* 이메일 인증 */}
                    <div className="email-verification">
                        <button 
                            type="button" 
                            onClick={sendVerificationCode} 
                            className="send-code-button"
                        >
                            인증 코드 전송
                        </button>

                        {isCodeSent && !isEmailVerified && (
                            <div className="verification-code-section">
                                <label htmlFor="verificationCode">인증 코드</label>
                                <input
                                    type="text"
                                    id="verificationCode"
                                    placeholder="이메일로 받은 코드를 입력하세요."
                                    value={verificationCode}
                                    onChange={(e) => setVerificationCode(e.target.value)}
                                />
                                <button 
                                    type="button" 
                                    onClick={verifyCode} 
                                    className="verify-code-button"
                                >
                                    인증 확인
                                </button>
                            </div>
                        )}

                        {isEmailVerified && (
                            <p className="verified-message">이메일 인증이 완료되었습니다!</p>
                        )}
                    </div>

                    {/* 비밀번호 입력 */}
                    <label htmlFor="pw">비밀번호</label>
                    <input
                        type={isPwVisible ? "text" : "password"}
                        id="pw"
                        placeholder="8자 이상 20자 이하 영문, 숫자, 특수문자 조합"
                        value={pw}
                        onChange={(e) => setPw(e.target.value)}
                    />
                    <button
                        type="button"
                        className="toggle-visibility-button"
                        onClick={handlePwVisibilityToggle}
                    >
                        {isPwVisible ? "숨기기" : "보기"}
                    </button>
                    
                    {/* 비밀번호 확인 입력 */}
                    <label htmlFor="pwConfirm">비밀번호 확인</label>
                    <input
                        type={isPwConfirmVisible ? "text" : "password"}
                        id="pwConfirm"
                        placeholder="비밀번호를 한 번 더 입력해주세요."
                        value={pwConfirm}
                        onChange={(e) => setPwConfirm(e.target.value)}
                    />
                    <button
                        type="button"
                        className="toggle-visibility-button"
                        onClick={handlePwConfirmVisibilityToggle}
                    >
                        {isPwConfirmVisible ? "숨기기" : "보기"}
                    </button>

                    {/* 제출 버튼 */}
                    <button type="submit" className="next-button">
                        <img
                            src={`${process.env.PUBLIC_URL}/next_button.png`}
                            alt="next"
                            className="next-image"
                        />
                    </button>
                </form>
            </div>
        </div>
    );
}

export default Signup;