import { useEffect,useRef } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function OAuthCallback() {
  const navigate = useNavigate();
  const hasRun = useRef(false);;

  useEffect(() => {

    if (hasRun.current) return; 
    hasRun.current = true;

    async function fetchUserAndCheckEmail() {
      try {
        const response = await axios.get("http://localhost:8080/user-data", { withCredentials: true });
        if (response.status === 200) {
          const email = response.data.email;
    
          await axios.get(`http://localhost:8080/auth/check-email?email=${encodeURIComponent(email)}`, { withCredentials: true });
       
          navigate('/dashboard');
        }
      } catch (error) {
        alert('user is not registered')
        navigate('/register');
      }
    }

    fetchUserAndCheckEmail();
  }, []);

  return( <div>Loading...</div>);
}
