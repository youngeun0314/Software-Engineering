import React from 'react';
import {HashRouter,Route, Routes} from 'react-router-dom';
import Signup from './routes/user registration/Signup';
import Login from './routes/user management/Login';
import Main from './routes/Main';
import Order from './routes/order received/Order';
import RoutineList from './routes/routine/RoutineList';
import PastOrder from './routes/order received/PastOrder';
 
 
function App(){
  return(
    <HashRouter>
        <Routes>
        <Route path="/" element={<Login/>}/>
        <Route path="/signup" element={<Signup/>}/>
        <Route path="/main" element={<Main/>}/>
        <Route path="/order" element={<Order/>}/>
        <Route path="/routinelist" element={<RoutineList/>}/>
        <Route path="/past-order" element={<PastOrder/>}/>
        </Routes>
    </HashRouter>
  )
}
 
export default App;