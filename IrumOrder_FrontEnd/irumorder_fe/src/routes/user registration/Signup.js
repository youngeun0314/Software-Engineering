import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Signup.css';
import { Link } from 'react-router-dom';

function Signup() {
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');
    const [pwConfirm, setPwConfirm] = useState('');
    const [email, setEmail] = useState('');
    const [isIdValid, setIsIdValid] = useState(false);
    const [isPwValid, setPwValid] = useState(false);
    const navigate = useNavigate();

    const handleIdChange = (e) => {
        setId(e.target.value);
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
        const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>])[A-Za-z0-9!@#$%^&*(),.?":{}|<>]{4,20}$/;

        if (!passwordPattern.test(pw)) {
            alert('비밀번호는 4자 이상 20자 이하의 영문과 숫자, 특수문자 조합이어야 합니다.');
            setPw('');
            return;
        }
        setPwConfirm(e.target.value);
    };

    const handleEmailChange = (e) => {  // 이메일 변경 핸들러 추가
        setEmail(e.target.value);
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
            const response = await fetch(`http://localhost:8080/auth/checkId?id=${id}`);  // Swagger API URL로 수정
            const data = await response.json();
            if (data.available) {
                setIsIdValid(true);
                alert("아이디 사용 가능")
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
        }

        if (!isIdValid || !isPwValid) {
            alert('다시 시도해 주십시오.');
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
                alert('회원가입이 완료되었습니다.');
                navigate('/login'); // 회원가입 성공 후 로그인 페이지로 이동
            } else {
                console.error('회원가입 실패:', response.statusText);
                alert('회원가입에 실패했습니다. 다시 시도해주세요.');
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

                    <label htmlFor="pw">비밀번호</label>
                    <input
                        type="password"
                        id="pw"
                        placeholder="4자 이상 20자 이하 영문, 숫자, 특수문자 조합"
                        value={pw}
                        onChange={handlePwChange}
                    />

                    <label htmlFor="pwConfirm">비밀번호 확인</label>
                    <input
                        type="password"
                        id="pwConfirm"
                        placeholder="비밀번호를 한 번 더 입력해주세요."
                        value={pwConfirm}
                        onChange={handlePwConfirmChange}
                    />
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
