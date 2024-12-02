// AppContent.js
import React, { useState } from 'react';
import { Routes, Route, useNavigate } from "react-router-dom";
import StoreSelection from './components/StoreSelection/StoreSelection';
import MenuView from './components/MenuView/MenuView';
import HomeView from './components/HomeView/HomeView';

const AppContent = () => {
  const [selectedStore, setSelectedStore] = useState('');
  const nav = useNavigate(); // 컴포넌트 내부에서 useNavigate 호출

  const handleStartStore = () => {
    nav('/store'); // 스토어 선택 화면으로 이동
  };

  const handleStartMenu = (store) => {
    setSelectedStore(store);
    nav(`/store/${store}`); // 메뉴 화면으로 이동
  };

  return (
    <Routes>
      <Route path="/" element={<HomeView onStartStore={handleStartStore} />} />
      <Route path="/store" element={<StoreSelection onStartMenu={handleStartMenu} />} />
      <Route path="/store/:store" element={<MenuView selectedStore={selectedStore} />} />
    </Routes>
  );
};

export default AppContent;
