import React from 'react';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AppContent from './AppContent';

const App = () => {
  return (
    <div>
      <Router>
        <AppContent />
      </Router>
    </div>
  );
};

export default App;