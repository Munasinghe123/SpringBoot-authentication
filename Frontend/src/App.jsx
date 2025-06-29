
import {BrowserRouter as Router, Routes,Route} from 'react-router-dom'

import Home from "./Home";
import Login from "./pages/Login";
import Register from './pages/Register';
import DashBoarad from './DashBoard';

function App(){

  return(
    <Router>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/login" element={<Login/>}/>
        <Route path='/register' element={<Register/>}/>
        <Route path='/dashboard' element={<DashBoarad/>}/>
      </Routes>
    </Router>
  )
}

export default App;