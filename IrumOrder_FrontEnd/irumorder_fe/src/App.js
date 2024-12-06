import React, { useState } from 'react';
import { Route, Routes, useNavigate } from "react-router-dom";
import HomeView from './components/HomeView/HomeView';
import MenuView from './components/MenuView/MenuView';
import OptionView from './components/OptionView/OptionView';
import StoreSelection from './components/StoreSelection/StoreSelection';

const App = () => {
  const [selectedStore, setSelectedStore] = useState('');
  const [selectedOption, setSelectedOption] = useState(''); // 선택한 옵션 상태 추가
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
    setSelectedOption(menuId); // 선택한 옵션 상태
    nav(`/option/${menuId}`);
  };

  return (
    <Routes>
      <Route path="/" element={<HomeView onStartStore={handleStartStore} />} />
      <Route path="/store" element={<StoreSelection onStartMenu={handleStartMenu} />} />
      <Route path="/store/:store" element={<MenuView onSelectedStore={selectedStore} onStartOption={handleOption}/>} />
      <Route path="/option/:menuId" element={<OptionView onSelectedOption={selectedOption}/>} /> {/* 옵션 페이지 라우트 추가 */}
    </Routes>
  );
};

export default App;
