import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./MenuSelect.css";

const MenuSelect = () => {
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [selectedCategoryId, setSelectedCategoryId] = useState(null);
  const [menus, setMenus] = useState([]);
  const [selectedMenu, setSelectedMenu] = useState(null);
  const [options, setOptions] = useState({
    addShot: false,
    addVanilla: false,
    addHazelnut: false,
    light: false,
  });

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await fetch("/category/getAllCategory");
        const data = await response.json();
        setCategories(data);
      } catch (error) {
        console.error("카테고리 불러오기 실패", error);
      }
    };

    fetchCategories();
  }, []);

  useEffect(() => {
    if (!selectedCategoryId) return;

    const fetchMenus = async () => {
      try {
        const response = await fetch(`/menu/getMenu?categoryId=${selectedCategoryId}`);
        const data = await response.json();
        setMenus(data);
      } catch (error) {
        console.error("메뉴 불러오기 실패", error);
      }
    };

    fetchMenus();
  }, [selectedCategoryId]);

  const handleSelectMenu = (menu) => {
    setSelectedMenu(menu);
  };

  const handleAddToRoutine = () => {
    const updatedRoutine = {
      ...selectedMenu,  // 메뉴와 옵션을 루틴에 추가
      options
    };
    navigate(-1, { state: updatedRoutine });  // 돌아가면서 업데이트된 상태를 전달
  };

  return (
    <div className="menu-select">
      <h1>메뉴 선택</h1>
      <div className="categories">
        {categories.map((category) => (
          <button
            key={category.id}
            onClick={() => setSelectedCategoryId(category.id)}
            className={selectedCategoryId === category.id ? "active" : ""}
          >
            {category.name}
          </button>
        ))}
      </div>

      <div className="menus">
        {menus.map((menu) => (
          <div
            key={menu.id}
            className={`menu-item ${selectedMenu?.id === menu.id ? "selected" : ""}`}
            onClick={() => handleSelectMenu(menu)}
          >
            <p>{menu.name}</p>
            <p>{menu.price}원</p>
          </div>
        ))}
      </div>

      {selectedMenu && (
        <div className="menu-options">
          <h3>옵션</h3>
          <label>
            <input
              type="checkbox"
              checked={options.addShot}
              onChange={(e) => setOptions({ ...options, addShot: e.target.checked })}
            />
            샷 추가
          </label>
          <label>
            <input
              type="checkbox"
              checked={options.addVanilla}
              onChange={(e) => setOptions({ ...options, addVanilla: e.target.checked })}
            />
            바닐라 추가
          </label>
          <label>
            <input
              type="checkbox"
              checked={options.addHazelnut}
              onChange={(e) => setOptions({ ...options, addHazelnut: e.target.checked })}
            />
            헤이즐넛 추가
          </label>
          <label>
            <input
              type="checkbox"
              checked={options.light}
              onChange={(e) => setOptions({ ...options, light: e.target.checked })}
            />
            라이트
          </label>
        </div>
      )}

      <div className="action-buttons">
        <button onClick={() => navigate(-1)}>취소</button>
        <button onClick={handleAddToRoutine}>루틴에 넣기</button>
      </div>
    </div>
  );
};

export default MenuSelect;