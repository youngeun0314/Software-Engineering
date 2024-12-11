/**
 * 파일 설명: React 애플리케이션의 엔트리 포인트
 * 작성자: 이희진, 최진영, 박수빈
 * 마지막 수정일: 2024-12-11
 * 
 * - ReactDOM.createRoot를 사용하여 React 애플리케이션을 DOM에 마운트합니다.
 * - BrowserRouter를 사용하여 라우팅을 제공합니다.
 * - 서비스 워커를 등록하여 Firebase 푸시 알림을 처리합니다.
 */

import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { BrowserRouter } from 'react-router-dom';

// 서비스 워커 등록 부분
// Firebase 푸시 알림을 처리하기 위한 서비스 워커 등록 로직
if ("serviceWorker" in navigator) {
  navigator.serviceWorker
    .register(`${process.env.PUBLIC_URL}/firebase-messaging-sw.js`)
    .then((registration) => {
      // 서비스 워커가 성공적으로 등록되었을 때 로그 출력
      console.log("서비스 워커가 성공적으로 등록되었습니다:", registration);
    })
    .catch((error) => {
      // 서비스 워커 등록 중 오류 발생 시 로그 출력
      console.error("서비스 워커 등록 중 오류 발생:", error);
    });
}

// React 애플리케이션 초기화 및 마운트
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
      <App />
    </BrowserRouter>
);