import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";



function Register() {

    const [name, setName] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");

    const navigate = useNavigate();


    const formData = new FormData();
    formData.append("name",name);
    formData.append("password",password);
    formData.append("email",email);

    const handleRegister=async(e)=>{
        try{
            e.preventDefault();

            const response = await axios.post('http://localhost:8080/auth/register',formData,{withCredentials:true});
            if(response.status===200){
                navigate('/login');
            }
        }catch(err){
            console.log(err);
        }
    }

    return (
        <div>
            <form onSubmit={handleRegister}>
                <label>Name</label>
                <input type="text" onChange={(e) => setName(e.target.value)} value={name} />

                <label>Password</label>
                <input type="password" onChange={(e) => setPassword(e.target.value)} value={password} />

                <label>Email</label>
                <input type="email" onChange={(e) => setEmail(e.target.value)} value={email} />

                <button type="submit">Register</button>
            </form>
        </div>
    )
}

export default Register;