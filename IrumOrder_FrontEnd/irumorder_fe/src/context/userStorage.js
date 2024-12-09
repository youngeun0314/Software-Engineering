let currentUserId = null;

// user_id 저장
export const setUserId = (id) => {
  currentUserId = id; //id
};

// user_id 가져오기
export const getUserId = () => {
  return currentUserId;
};

// user_id 초기화
export const clearUserId = () => {
  currentUserId = null;
};