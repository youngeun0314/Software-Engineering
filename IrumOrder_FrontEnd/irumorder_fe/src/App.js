import React from 'react';
import {HashRouter,Route, Routes} from 'react-router-dom';
import Cart from './routes/Cart';
import Payment from './routes/Payment';
import Paymentcomplete from './routes/Paymentcomplete';

function App(){
  return(
    <HashRouter>
        <Routes>
        <Route path="/cart" element={<Cart/>}/>
        <Route path="/payment" element={<Payment/>}/>
        <Route path="/paymentcomplete" element={<Paymentcomplete/>}/>
        </Routes>
    </HashRouter>
  )
}
 
export default App;