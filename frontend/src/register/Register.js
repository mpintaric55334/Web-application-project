import { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import AvatarDialog from "./AvatarDialog";

export default function Register() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [nickname, setNickname] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [avatar, setAvatar] = useState('avatar-1');
    const navigate = useNavigate();
    const [isPending, setIsPending] = useState(false);
    const [error, setError] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const data = {
            firstName: firstName, 
            lastName: lastName, 
            nickname: nickname, 
            email: email,
            avatar: avatar,
            password: password
        };
        
        fetch('https://sinappsa.onrender.com/sinapsa/registration', {
            method: 'POST',
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify(data)
        }).then(response => {
            if(response.status !== 200) {
                //setError("Neuspjela registracija. Korisnik već postoji.")
                setIsPending(false)
                response.json()
                .then(error => {
                    setError(error.msg)
                })
            } else {
                setIsPending(true)
                setTimeout(() => {setIsPending(false); 
                                navigate('/sinapsa');
                                }, 3000)
            }
        })
    }

    function isValid() {
        return firstName.length > 0
            && lastName.length > 0
            && nickname.length > 0
            && email.length > 0
            && password.length > 5
    }

    return (
        <div>
            {!isPending && 
                <form className='s-form-container' onSubmit={handleSubmit}>

                    <AvatarDialog setAvatar={avatar => setAvatar(avatar)} avatar={avatar}/>

                    <div className="s-form-group">
                        <label htmlFor="first-name">Ime</label>
                        <input type="text" required className="s-form-control" id="first-name" placeholder="Unesite ime"
                            value={firstName} onChange={(e) => setFirstName(e.target.value)}
                        />
                    </div>
                    <div className="s-form-group">
                        <label htmlFor="last-name">Prezime</label>
                        <input type="text" required className="s-form-control" id="last-name" placeholder="Unesite prezime"
                            value={lastName} onChange={(e) => setLastName(e.target.value)}
                        />
                    </div>
                    <div className="s-form-group">
                        <label htmlFor="nickname">Korisničko ime</label>
                        <input type="text" required className="s-form-control" id="nickname" placeholder="Unesite nadimak"
                            value={nickname} onChange={(e) => setNickname(e.target.value)}
                        />
                    </div>
                    <div className="s-form-group">
                        <label htmlFor="email">E-mail adresa</label>
                        <input type="email" required className="s-form-control" id="email" placeholder="Unesite e-mail adresu"
                            value={email} onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>
                    <div className="s-form-group">
                        <label htmlFor="password">Zaporka</label>
                        <input type="password" required className="s-form-control" id="password" placeholder="Unesite zaporku"
                            value={password} onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    {error && <div className="s-form-group s-error">{error}</div>}
                    <div className='s-form-group-btn'>
                        { !isPending && <button type="submit" className="s-btn s-btn-primary" disabled={!isValid()}>Registriraj se</button> }
                        { isPending && <button type="submit" className="s-btn s-btn-primary" disabled>Molimo pričekajte</button> }
                    </div>
                </form>
            }
            {isPending && 
                <div className='s-verify-container'>
                    <p className='s-message'> Provjerite svoju <br/>
                                                E-mail adresu    <br/> 
                                                kako biste završili registraciju!</p>
                </div>
            }
        </div>
    )
}
