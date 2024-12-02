import React from 'react';
import { useState, useEffect } from 'react';
import './SignupStart.css';
import { Link } from 'react-router-dom';

function SignupStart() {
  return (
    <div className="signup-start-container">
      <header className="signup-start-header">
        <div className="signup-start-text">
          <p>회원가입</p>
        </div>
        <Link to={'/'}>
          <button className="x-button">
            <img
            src={`${process.env.PUBLIC_URL}/x버튼.png`}
            alt="x"
            className="x-image"
          /></button>
        </Link>
      </header>
      <div className='content-container'>
        <div className='image-container'>
          <img
            src={`${process.env.PUBLIC_URL}/Group 242.png`}
            alt="이룸오더는 간단하게 가입할 수 있어요."
            className="text-image"
          />
        </div>
      </div>
      <div className="complete-button-container">
        <Link to={'/signup'}>  
          <button type="complete" className="complete-button">
              <img
                src={`${process.env.PUBLIC_URL}/회원가입 시작하기.png`}
                alt="완료"
                className="complete-image"
              />
            </button>
          </Link>
      </div>
      
    </div>
  );
};

export default SignupStart;