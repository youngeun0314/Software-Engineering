import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { setUserId } from '../../context/userStorage';
import './Login.css';

function Login() {
  const navigate = useNavigate();
  const [id, setId] = useState('');
  const [pw, setPw] = useState('');

  const handleLogin = async (event) => {
    event.preventDefault();

    try {
      const response = await fetch(`http://localhost:8080/auth/login?id=${id}&password=${pw}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 401) {
        alert('로그인 실패: 아이디나 비밀번호를 확인하세요.');
        console.error('로그인 실패:', response.statusText);
      } else if (response.status === 404) {
        alert('입력하신 아이디는 존재하지 않습니다.');
        console.error('아이디 없음:', response.statusText);
      } else if (response.ok) {
        const responseText = await response.text();
        alert('로그인 성공! 환영합니다.');
        setUserId(responseText); // context에 userId 저장
        navigate('/main');
      } else {
        alert('알 수 없는 오류가 발생했습니다.');
        console.error('서버 응답 오류:', response.statusText);
      }
    } catch (error) {
      console.error('서버와의 통신 오류:', error);
      alert('서버와의 통신 중 문제가 발생했습니다. 다시 시도해주세요.');
    }
  };

  return (
    <div className="login-container">
      <header className="login-header"></header>
      <div className="login-content">
        <h1 className="app-title">내 손 안의 작은 시립대 카페</h1>
        <div className="logo">
          <img src="irum_title.png" alt="Irum_title" />
        </div>
        <form onSubmit={handleLogin} className="login-form">
          <label htmlFor="id">아이디</label>
          <input
            type="text"
            id="id"
            placeholder="아이디를 입력하세요."
            value={id}
            onChange={(e) => setId(e.target.value)}
          />

          <label htmlFor="pw">비밀번호</label>
          <input
            type="password"
            id="pw"
            placeholder="비밀번호를 입력하세요."
            value={pw}
            onChange={(e) => setPw(e.target.value)}
          />
          <button type="submit" className="login-button">
            로그인
          </button>
        </form>
        <div className="singup-prompt">
          <label htmlFor="signupstart">아직 회원이 아니신가요?</label>{' '}
          <Link to={'/signupstart'} className="signup-link" id="signup">
            가입하기 &gt;
          </Link>
        </div>
        <div className="login-irum">
          <img src="Login.png" alt="Irumae" />
        </div>
      </div>
    </div>
  );
}

export default Login;
