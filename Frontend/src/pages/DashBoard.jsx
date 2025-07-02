import { useEffect, useState } from "react";
import axios from 'axios'
import { useNavigate } from "react-router-dom";

function DashBoarad() {

    const [user, setUser] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
        const fetchUser = async () => {
            try {
                let response = await axios.get("http://localhost:8080/auth/me", { withCredentials: true });
                if (response.status === 200) {
                    setUser(response.data);
                    console.log(response.data);
                    return;
                }
            } catch { }

            try {
                let response = await axios.get("http://localhost:8080/user-data", { withCredentials: true });
                if (response.status === 200) {
                    setUser(response.data);
                    return;
                }
            } catch { }
        };

        fetchUser();
    }, []);


    const handleLogout = async () => {

        const response = await axios.post('http://localhost:8080/auth/logout', {}, { withCredentials: true });
        if (response.status === 200) {
            navigate('/')
        }
    }

    return (
        <>
            <h1>U have successfully logged in</h1>

            {
                user ? (<>
                    <p>{user.name}</p>
                    <p>{user.email}</p>
                    <p>{user.role}</p>
                    <p><img src={user.photo} alt="photo" width={80} /></p>
                </>
                ) : (
                    <p>No user </p>
                )
            }
            <button onClick={handleLogout}>Logout</button>
        </>
    )

}

export default DashBoarad;