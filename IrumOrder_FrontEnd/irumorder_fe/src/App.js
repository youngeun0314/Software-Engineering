import React from 'react';
import {HashRouter,Route, Routes} from 'react-router-dom';
import Signup from './routes/Signup';
import Login from './routes/Login';
 
 
function App(){
  return(
    <HashRouter>
        <Routes>
        <Route path="/" element={<Login/>}/>
        <Route path="/signup" element={<Signup/>}/>
        </Routes>
    </HashRouter>
  )
}
 
export default App;