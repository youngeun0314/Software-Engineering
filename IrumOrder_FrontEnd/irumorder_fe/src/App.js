import React from 'react';
import {HashRouter,Route, Routes} from 'react-router-dom';
import Payment from './routes/Payment';
import Paymentcomplete from './routes/Paymentcomplete';

function App(){
  return(
    <HashRouter>
        <Routes>
        <Route path="/payment" element={<Payment/>}/>
        <Route path="/paymentcomplete" element={<Paymentcomplete/>}/>
        </Routes>
    </HashRouter>
  )
}
 
export default App;