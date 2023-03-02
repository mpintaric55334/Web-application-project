import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom'
import Profile from "./Profile";
import Notice from "../notice/Notice";

export default function StudentProfile() {
    const [error, setError] = useState('');
    const [showProfile, setShowProfile] = useState(true);
    const [profileStateMsg, setProfileStateMsg] = useState("Sakrij");
    const [notices, setNotices] = useState([]);
    const location = useLocation();
    const { userId, postNickname } = location.state;

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [nickname, setNickname] = useState('');
    const [email, setEmail] = useState('');
    const [avatar, setAvatar] = useState('');

    useEffect(() => {

        if(!postNickname){
            setError("Nemate pristup ovoj stranici")
        }

        const data = {
            nickname: postNickname
        }

        fetch('https://sinappsa.onrender.com/sinapsa/StudentProfile', {
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
                        setAvatar(user.avatar)
                        setFirstName(user.firstName)
                        setLastName(user.lastName)
                        setNickname(user.nickname)
                        setEmail(user.email)
                    })
            }
        })

        fetch('https://sinappsa.onrender.com/sinapsa/userNotices', {
            method: 'POST',
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify({ id: userId })
        })
            .then(response => {
                if(response.status !== 200) {
                    response.json()
                        .then(error => {
                            setNotices([])
                        })
                } else {
                    response.json()
                        .then(notices => {
                            setNotices(notices);
                        })
                }
            })
    }, [])

    function handleProfileDisplay(){
        if(showProfile){
            setShowProfile(false);
            setProfileStateMsg("Prikaži");
            return
        }
        setShowProfile(true);
        setProfileStateMsg("Sakrij");
    }

    return (
        <div>
            {
                error   
                    ? <div className="s-profile-container">
                        <div className="s-profile-group s-error">{error}</div>
                      </div>
                    : <div>
                        <div className='s-profile-display-container' onClick={handleProfileDisplay}>
                            <h2 className='s-displayProfile-h2'>{profileStateMsg} profil</h2>
                            {
                            showProfile 
                                ? <img src={process.env.PUBLIC_URL + '/images/closingX.png'} className="s-mediumIcon" alt="Closing X icon"/>
                                : <img src={process.env.PUBLIC_URL + '/images/magnifying-glass.png'} className="s-mediumIcon" alt="Magnifying glass icon"/>
                            }
                        </div>
                        {
                        showProfile && 
                            <div className="s-personalProfile-container">
                                <Profile user={{firstName, lastName, nickname, email, avatar}}/>
                            </div>
                        }
                        <div className="container">
                            <div className="row">
                                <h2 className="text-center">Popis oglasa</h2>
                            </div>
                            {
                                notices
                                    .filter(notice => notice.active)
                                    .map(notice => <Notice key={notice.id} notice={notice}/>)
                            }
                        </div>
                    </div>
            }
        </div>
    )
}
