
import {BrowserRouter as Router, Routes,Route} from 'react-router-dom'

import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from './pages/Register';
import DashBoarad from './pages/DashBoard';
import EmailCheck from './Components/EmailCheck';

function App(){

  return(
    <Router>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/login" element={<Login/>}/>
        <Route path='/register' element={<Register/>}/>
        <Route path='/dashboard' element={<DashBoarad/>}/>
        <Route path='/email-check' element={<EmailCheck/>}/>
      </Routes>
    </Router>
  )
}

export default App;