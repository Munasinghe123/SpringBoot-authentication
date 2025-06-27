import { useEffect, useState } from "react";
import axios from 'axios'

function DashBoarad() {

    const [user, setUser] = useState(null);

    useEffect(() => {

        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/user-data', { withCredentials: true });
                setUser(response.data);
            } catch (err) {
                console.log(err);
            }
        }
        fetchData();
    }, [])

    const handleLogout = () => {
        window.location.href = 'http://localhost:8080/logout'
    }

    return (
        <>
            <h1>U have successfully logged in</h1>

            {
                user ? (<>
                    <p>{user.name}</p>
                    <p>{user.email}</p>
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