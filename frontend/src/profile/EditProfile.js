import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AvatarDialog from "../register/AvatarDialog";

export default function Profile(props) {
    const [oldNickname, setOldNickname] = useState('');
    const [newNickname, setNewNickname] = useState('');
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [avatar, setAvatar] = useState('');
    const [error, setError] = useState('');
    const [userAuthorised, setUserAuthorised] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        if(sessionStorage.getItem("User") === null){  
            setUserAuthorised(false)
            setError("Nemate pristup ovoj stranici")
        } else {
            setUserAuthorised(true)
            const user = JSON.parse(sessionStorage.getItem("User"));
            setOldNickname(user.nickname);
            setNewNickname(user.nickname);
            setAvatar(user.avatar);
        }
    }, [])

    const handleSubmit = (e) => {
        e.preventDefault()
        setError("")

        if(!isValid())
            return

        const user = JSON.parse(sessionStorage.getItem("User"));

        if (document.activeElement.name === 'Delete'){
            console.log("delete")

            const dataForDelete = {
                id: user.id,
                nickname: oldNickname, 
                oldPassword: oldPassword
            }

            fetch('https://sinappsa.onrender.com/sinapsa/deleteProfile', {
                method: 'POST',
                headers: { "Content-Type": "application/json"},
                body: JSON.stringify(dataForDelete)
            }).then(response => {
                if(response.status !== 200) {
                    response.json()
                    .then(error => {
                        setError(error.msg)
                    })
                } else {
                    props.setUser(null)
                    props.onLogout()
                    setUserAuthorised(false)
                    sessionStorage.removeItem("User")
                    navigate('/sinapsa')
                }
            })

        } else if (document.activeElement.name === 'Update') {
            console.log("update")

            const dataForUpdate = {
                id: user.id,
                oldPassword: oldPassword,
                avatar: avatar,
                nickname: newNickname, 
                newPassword: newPassword
            };

            fetch('https://sinappsa.onrender.com/sinapsa/editProfile', {
                method: 'POST',
                headers: { "Content-Type": "application/json"},
                body: JSON.stringify(dataForUpdate)
            }).then(response => {
                if(response.status !== 200) {
                    response.json()
                    .then(error => {
                        setError(error.msg)
                    })
                } else {
                    user.nickname = newNickname;
                    user.avatar = avatar;
                    user.password = newPassword;
                    sessionStorage.setItem("User",JSON.stringify(user));
                    navigate('/sinapsa/personalProfile');     
                }
            })
        }
    }

    function isValid(){
        if(newNickname.length === 0){
            setError("Unesite valjani nadimak")
            return false
        }
        if (oldPassword.length <= 5){
            setError("Unesite staru lozinku odgovarajuće duljine.");
            return false
        }
        return true
    }

    return (
        <div>
            {userAuthorised && 
                <form className='s-form-container' onSubmit={handleSubmit}>
                    <div className="s-form-group">
                        <h3 className="s-editProfile-h3">Promijenite svoje podatke</h3>
                    </div>

                    <AvatarDialog setAvatar={avatar => setAvatar(avatar)} avatar={avatar}/>

                    <div className="s-form-group">
                        <label for="nickname">Novo korisničko ime</label>
                        <input type="text" className="s-form-control" id="nickname" placeholder="Unesite novi nadimak"
                            value={newNickname} onChange={(e) => setNewNickname(e.target.value)}
                        />
                    </div>
                    <div className="s-form-group">
                        <label for="newPassword">Nova zaporka</label>
                        <input type="password" className="s-form-control" id="newPassword" placeholder="Unesite novu zaporku"
                            value={newPassword} onChange={(e) => setNewPassword(e.target.value)}
                        />
                    </div>
                    <div className="s-form-group">
                        <label for="oldPassword">Stara zaporka</label>
                        <input type="password" required className="s-form-control" id="oldPassword" placeholder="Potvrdite promjene starom zaporkom"
                            value={oldPassword} onChange={(e) => setOldPassword(e.target.value)}
                        />
                    </div>
                    {error && <div className="s-form-group s-error">{error}</div>}
                    <div className='s-editProfile-group-btn'>
                        <button type="submit" className="s-btn s-btn-primary" name="Update">Spremi promjene</button> 
                        <button type="submit" className="s-btn s-btn-primary s-btn-warning" name="Delete">Izbriši profil</button> 
                    </div>
                </form>
            }
            {!userAuthorised && 
                <div className="s-profile-container">
                    {error && <div className="s-profile-group s-error">{error}</div>}
                </div>
            }
        </div>
    )
}
