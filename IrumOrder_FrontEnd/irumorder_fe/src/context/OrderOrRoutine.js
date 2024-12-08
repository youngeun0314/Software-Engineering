let currentMenuIn = null;
let routineState = null;

export const setMenuIn = (in_menu) => {
  currentMenuIn = in_menu;
};

export const getMenuIn = () => {
  return currentMenuIn;
};

export const clearMenuIn = () => {
  currentMenuIn = null;
};

export const setRoutineState = (state) => {
  routineState = state;
};

export const getRoutineState = () => {
  return routineState;
};

export const clearRoutineState = () => {
  routineState = null;
};