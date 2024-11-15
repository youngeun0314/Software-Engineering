import React from 'react';
import {HashRouter,Route, Routes} from 'react-router-dom';
import Signup from './routes/Signup';
import Login from './routes/Login';
import Main from './routes/Main';
 
 
function App(){
  return(
    <HashRouter>
        <Routes>
        <Route path="/" element={<Login/>}/>
        <Route path="/signup" element={<Signup/>}/>
        <Route path="/main" element={<Main/>}/>
        </Routes>
    </HashRouter>
  )
}
 
export default App;