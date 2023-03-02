import {Link} from "react-router-dom";
import { useState } from 'react'
import { useNavigate } from 'react-router-dom';

export default function Login(props) {
    const [nickname, setNickname] = useState('');
    const [password, setPassword] = useState('');
    const [loginSuccess, setLoginSuccess] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault()
        setError("")
        const data = {
            nickname: nickname, 
            password: password
        };
        
        fetch('https://sinappsa.onrender.com/sinapsa/login', {
            method: 'POST',
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify(data)
        }).then(response => {
            if(response.status !== 200) {
                response.json()
                    .then(error => {
                        setError(error.msg)
                    })
            } else {
                response.json()
                    .then(user => {
                        setLoginSuccess(true)
                        props.onLogin()
                        props.setUser(user)
                        sessionStorage.setItem("User",JSON.stringify(user))
                        setTimeout(() => {navigate('/sinapsa');}, 2000)
                })
            }
        })
    }

    return (
        <div>
            {!loginSuccess && 
                <form className="s-form-container" onSubmit={handleSubmit}>
                    <div className="s-form-group">
                        <label htmlFor="nickname">Korisničko ime</label>
                        <input type="text" className="s-form-control" id="nickname" placeholder="Unesite nadimak"
                            value={nickname} onChange={(e) => setNickname(e.target.value)}
                        />
                    </div>
                    <div className="s-form-group">
                        <label htmlFor="password">Zaporka</label>
                        <input type="password" className="s-form-control" id="password" placeholder="Unesite zaporku"
                            value={password} onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    {error && <div className="s-form-group s-error">{error}</div>}
                    <div className="s-form-group-btn">
                        <button type="submit" className="s-btn s-btn-primary">Prijavi se</button>
                    </div>
                </form>
            }
            {!loginSuccess && 
                <div className="s-link-container">
                    <Link to="/sinapsa/registration" className="s-link-register">Nemaš profil? Registriraj se!</Link>
                </div>
            }

            {loginSuccess && 
                <div className="s-verify-container">
                    <p className="s-message"> Autentifikacija uspješna! <br/>
                                            Vraćamo vas na <br/>
                                            početni zaslon</p>
                </div>
            }
        </div>
    )
}
