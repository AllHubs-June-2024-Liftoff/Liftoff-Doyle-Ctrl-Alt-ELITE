
import React, {useState} from 'react'
import axios from 'axios'
import '../App.css';


const LoginPageComponent = ({ onLogin }) => {

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loginSuccess, setLoginSuccess] = useState(false);


  const handleLogin = async () => {
    try {
      // Use encodeURI if you have a whole URL. 
      // If you only have a portion of a URL, encodeURIComponent is the way to go.
      // const url = `http://localhost:8080/api/users/get?email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`;
      // const response = await axios.get(url);
      // const userData = response.data;
      // onLogin(userData);
      console.log("Login successful");

    } catch (error) {
      console.error('Login error:', error);
    }
    setLoginSuccess(true);
  }

  if (loginSuccess) {
    return (
    <div className='centered-container'>
      <div className="login-container">
        <h1>Logged in successfully</h1>
      </div>

    </div>);
  }


  return (

    <div className="container login-container">
      <div className="login-form">
        <h2 className="text-center header">Login to your Account</h2>

        <div className="input-container">
          <input type="email" placeholder="Enter email" value={email} onChange={(e) => setEmail(e.target.value)} />
        </div>
        <div className="input-container">
          <input type="password" placeholder="Enter password" value={password} onChange={(e) => setPassword(e.target.value)} />
        </div>
        <div className="input-container">
          <button className = "btn btn-primary mb-2 login-button" type = "button" onClick={handleLogin}>Login</button>
        </div>
        <div className="login-link">
          <p className='registration-link'>Don't have an account? <a href="/signup">Click here to SignUp</a></p>
        </div>
      </div>
  </div>

  )
}

export default LoginPageComponent