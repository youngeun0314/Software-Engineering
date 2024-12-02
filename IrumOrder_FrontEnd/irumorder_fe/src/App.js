import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import StoreSelection from './components/StoreSelection/StoreSelection';
import MenuView from './components/MenuView/MenuView';
import OptionView from './components/OptionView/OptionView'; 
import HomeView from './components/HomeView/HomeView';
import { useNavigate } from 'react-router-dom'; // useNavigate 추가

const App = () => {
  const [selectedStore, setSelectedStore] = useState('');
  const nav = useNavigate(); // 페이지 이동을 위해 useNavigate 사용

  // 스토어 선택 화면으로 이동
  const handleStartStore = () => {
    nav('/store');
  };

  // 메뉴 화면으로 이동
  const handleStartMenu = (store) => {
    setSelectedStore(store);
    nav(`/store/${store}`);
  };

  const handleOption = (menuId) => {
    nav(`/option/${menuId}`);
  };

  return (

    <Routes>
      <Route path="/" element={<HomeView onStartStore={handleStartStore} />} />
      <Route path="/store" element={<StoreSelection onStartMenu={handleStartMenu} />} />
      <Route path="/store/:store" element={<MenuView onselectedStore={selectedStore} />} />
      <Route path="/option/:menuId" element={<OptionView onStartOption={handleOption}/>} /> {/* 옵션 페이지 라우트 추가 */}
    </Routes>

  );
};

export default App;
