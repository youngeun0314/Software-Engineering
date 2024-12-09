import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Signup.css';

function Signup() {
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');
    const [pwConfirm, setPwConfirm] = useState('');
    const [email, setEmail] = useState('');
    const [isIdValid, setIsIdValid] = useState(false);
    const [isPwValid, setPwValid] = useState(false);
    const navigate = useNavigate();
    const [verificationCode, setVerificationCode] = useState('');
    const [isCodeSent, setIsCodeSent] = useState(false);
    const [isEmailVerified, setIsEmailVerified] = useState(false);
    const [isPwVisible, setIsPwVisible] = useState(false); // 비밀번호 가시성 상태 추가
    const [isPwConfirmVisible, setIsPwConfirmVisible] = useState(false); // 비밀번호

    const handlePwVisibilityToggle = () => {
        setIsPwVisible(!isPwVisible);
    };

    const handlePwConfirmVisibilityToggle = () => {
        setIsPwConfirmVisible(!isPwConfirmVisible);
    };

    const handleIdChange = (e) => {
        setId(e.target.value);
        setIsIdValid(false);
    };

    const handlePwChange = (e) => {
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        if (!emailPattern.test(email)) { 
            alert('이메일 형식이 맞지 않습니다.'); 
            setEmail(''); 
            return;
        }
        
        setPw(e.target.value);
    };

    const handlePwConfirmChange = (e) => {
        const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>])[A-Za-z0-9!@#$^&*(),.?":{}|<>]{8,20}$/;

        if (!passwordPattern.test(pw)) {
            alert('비밀번호는 8자 이상 20자 이하의 영문과 숫자, 특수문자 조합이어야 합니다.');
            setPw('');
            return;
        }
        setPwConfirm(e.target.value);
    };

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
        setIsEmailVerified(false);
    };

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
        
            const message = await response.text(); // JSON 대신 text()로 처리
            if (message === "사용 가능한 아이디입니다.") {
                setIsIdValid(true);
                alert(message);
            } else {
                setIsIdValid(false);
                alert(message);
            }
        } catch (error) {
            console.error('아이디 중복 확인 오류:1', error);
            alert('사용할 수 없는 아이디 입니다.');
        }
    };

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
    
    const verifyCode = async () => {
        if (!verificationCode) {
            alert('인증 코드를 입력해주세요.');
            return;
        }
    
        try {
            const response = await fetch(`http://localhost:8080/auth/verifyEmail?email=user%40example.com&code=1234`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
    
            // 서버가 JSON 형식으로 응답하지 않으면 response.text()로 처리
            const responseText = await response.text();
    
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

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (!isIdValid) {
            alert('아이디 중복 확인을 해주십시오.');
            return;
        } 
        if (pw !== pwConfirm) {
            setPwValid(false);
            alert('비밀번호가 일치하지 않습니다.');
            return;
        } else {
            if(pw === ''){
                setPwValid(false);
                alert('비밀번호가 일치하지 않습니다.');
                return;
            }
            setPwValid(true);
            console.log(isPwValid);
        }
        if (!isCodeSent) {
            alert('이메일 인증을 실시하십시오.');
            return;
        }
        if (!isEmailVerified) {
            alert('이메일 인증코드를 확인해 주십시오.');
            return;
        }
        

        // 새로운 사용자 등록을 위한 POST 요청 (Swagger API 규격에 맞춤)
        try {
            const response = await fetch('http://localhost:8080/auth/signUp', {  // Swagger API URL로 수정
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id,
                    password: pw,  // Swagger 스키마에 맞춰서 password로 변경
                    email,  // 이메일 추가
                }),
            });

            if (response.ok) {
                navigate('/'); // 회원가입 성공 후 로그인 페이지로 이동
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
                <Link to={'/signupstart'}>
                    <button className="back-button">
                        <img
                        src={`${process.env.PUBLIC_URL}/back_button.png`}
                        alt="back"
                        className="back-image"
                    /></button>
                </Link>
                <div className="signup-text">
                    <p>회원가입</p>
                </div>
                <Link to={'/'}>
                <button className="x-button">
                    <img
                    src={`${process.env.PUBLIC_URL}/x_button.png`}
                    alt="x"
                    className="x-image"
                /></button>
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
                    <label htmlFor="id">아이디</label>
                    <input
                        type="text"
                        id="id"
                        placeholder="4자 이상 20자 이하 영문, 숫자 조합"
                        value={id}
                        onChange={handleIdChange}
                    />
                    <button type="button" onClick={checkIdAvailability} className='id-check-button'>중복 확인</button>

                    <label htmlFor="email">이메일</label>
                    <input
                        type="email"
                        id="email"
                        placeholder="이메일을 입력해주세요."
                        value={email}
                        onChange={handleEmailChange}
                    />
                    
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
                    <button type="submit" className="next-button">
                        <img
                            src={`${process.env.PUBLIC_URL}/next_button.png`}
                            alt="next"
                            className="next-image"
                        ></img>
                    </button>
                </form>
            </div>
        </div>
    );
}

export default Signup;
