/**
 * 파일: Login.js
 * 설명: 사용자 로그인 화면을 제공하며, 서버를 통해 사용자 인증을 수행하는 컴포넌트
 * 작성자: 이희진
 * 마지막 수정일: 2024-12-9
 */

import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { setUserId } from '../../../shared/context/userStorage';
import './Login.css';

function Login() {
  const navigate = useNavigate(); // 페이지 이동을 위한 hook
  const [id, setId] = useState(''); // 아이디 입력 상태 관리
  const [pw, setPw] = useState(''); // 비밀번호 입력 상태 관리

  /**
   * 로그인 처리 메서드
   *
   * @param event 폼 제출 이벤트
   * @return void
   */
  const handleLogin = async (event) => {
    event.preventDefault(); // 폼의 기본 동작 방지

    try {
      const response = await fetch(`http://localhost:8080/auth/login?id=${id}&password=${pw}`, {
        method: 'POST', // POST 요청으로 서버에 로그인 정보 전달
        headers: {
          'Content-Type': 'application/json', // JSON 요청 헤더 설정
        },
      });

      if (response.status === 401) {
        // 인증 실패 시 사용자 알림
        alert('로그인 실패: 아이디나 비밀번호를 확인하세요.');
        console.error('로그인 실패:', response.statusText);
      } else if (response.status === 404) {
        // 사용자 ID 미존재 시 알림
        alert('입력하신 아이디는 존재하지 않습니다.');
        console.error('아이디 없음:', response.statusText);
      } else if (response.ok) {
        // 로그인 성공 시 처리
        const responseText = await response.text(); // 서버에서 받은 사용자 ID
        alert('로그인 성공! 환영합니다.');
        setUserId(responseText); // 사용자 ID를 저장 (context 사용)
        navigate('/main'); // 메인 페이지로 이동
      } else {
        // 기타 서버 오류 처리
        alert('알 수 없는 오류가 발생했습니다.');
        console.error('서버 응답 오류:', response.statusText);
      }
    } catch (error) {
      // 서버와의 통신 오류 처리
      console.error('서버와의 통신 오류:', error);
      alert('서버와의 통신 중 문제가 발생했습니다. 다시 시도해주세요.');
    }
  };

  return (
    <div className="login-container">
      <header className="login-header"></header> {/* 페이지 헤더 */}
      <div className="login-content">
        <h1 className="app-title">내 손 안의 작은 시립대 카페</h1> {/* 앱 제목 */}
        <div className="logo">
          <img src="irum_title.png" alt="Irum_title" /> {/* 앱 로고 */}
        </div>
        <form onSubmit={handleLogin} className="login-form">
          <label htmlFor="id">아이디</label>
          <input
            type="text"
            id="id"
            placeholder="아이디를 입력하세요." // 입력 힌트
            value={id}
            onChange={(e) => setId(e.target.value)} // 입력값 상태 업데이트
          />

          <label htmlFor="pw">비밀번호</label>
          <input
            type="password"
            id="pw"
            placeholder="비밀번호를 입력하세요." // 입력 힌트
            value={pw}
            onChange={(e) => setPw(e.target.value)} // 입력값 상태 업데이트
          />
          <button type="submit" className="login-button">
            로그인
          </button> {/* 로그인 버튼 */}
        </form>
        <div className="singup-prompt">
          <label htmlFor="signupstart">아직 회원이 아니신가요?</label>{' '}
          <Link to={'/signupstart'} className="signup-link" id="signup">
            가입하기 &gt;
          </Link> {/* 회원가입 페이지로 이동 */}
        </div>
        <div className="login-irum">
          <img src="Login.png" alt="Irumae" /> {/* 추가 이미지 */}
        </div>
      </div>
    </div>
  );
}

export default Login;