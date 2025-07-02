import { Link } from "react-router-dom";


function Home(){

    return(
        <>
         <Link to='/login'> Login</Link>
         <Link to='/register'>Register</Link>
        </>
    )
}

export default Home; 