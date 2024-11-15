import React, {useState} from 'react';
import './Login.css';
import { Link } from 'react-router-dom';
import Signup from './Signup';
 

function Login() {
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');

    const handleLogin = async (event) => {
        event.preventDefault();
    
        // POST 요청을 통해 서버에 데이터 전송
        try {
          const response = await fetch('https://your-backend-url.com/api/login', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              id,
              pw
            })
          });
    
          if (response.ok) {
            const data = await response.json();
            console.log('로그인 성공:', data);
            // 필요한 경우 토큰 저장 등 후속 작업 수행
          } else {
            console.error('로그인 실패:', response.statusText);
          }
        } catch (error) {
          console.error('서버와의 통신 오류:', error);
        }
      };

    return (
        <div className='login-container'>
            <header className='login-header'>
                <a href='/register-store' className='register-link'>매장 등록 신청하러 가기 &gt;</a>
            </header>
            <div className='login-content'>
                <h1 className='app-title'>내 손 안의 작은 시립대 카페</h1>
                <div className='logo'>
                  <img src="irum_title.png" alt="Irum_title"/>
                </div>
                <form onSubmit={handleLogin} className='login-form'>
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

                    <button type="submit" className='login-button'>로그인</button>
                </form>
                <div className='singup-prompt'>
                    <label htmlFor='signup'>아직 회원이 아니신가요?</label> <Link to={'/signup'} className='signup-link' id='signup'>가입하기 &gt;</Link>
                </div>
                <div className='login-irum'>
                    <img src="Login.png" alt="Irumae"/>
                </div>
            </div>
        </div>
    );
}
 
export default Login;