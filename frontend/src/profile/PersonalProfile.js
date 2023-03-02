import React, { useEffect, useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { userContext } from '../Contexts/UserContext';
import Profile from "./Profile";
import Notice from "../notice/Notice";
import Query from "../query/Query";
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';

export default function PersonalProfile() {
    const [error, setError] = useState('');
    const [showProfile, setShowProfile] = useState(true);
    const [profileStateMsg, setProfileStateMsg] = useState("Sakrij");
    const [userAuthorised, setUserAuthorised] = useState(false);

    const [notices, setNotices] = useState([]);
    const [queries, setQueries] = useState([]);

    const [dataToDisplay, setDataToDisplay] = useState("notices");

    const { firstName, lastName, nickname, email, avatar, userId, setUser, setIsLoggedIn, onLogout } = useContext(userContext);
    const navigate = useNavigate();

    useEffect(() => {
        if(sessionStorage.getItem("User") === null){  
            setUserAuthorised(false)
            setIsLoggedIn(false)
            setError("Nemate pristup ovoj stranici")
        } else {
            setUserAuthorised(true)
            setIsLoggedIn(true)
            setUser(JSON.parse(sessionStorage.getItem("User")))
        }

        const appUserId = JSON.parse(sessionStorage.getItem("User")).id;

        fetch('https://sinappsa.onrender.com/sinapsa/userNotices', {
            method: 'POST',
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify({ id: appUserId })
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

        const data = {
            userProfileId: appUserId,
            appUserId: appUserId
        }

        fetch('https://sinappsa.onrender.com/sinapsa/getCurrentUserQueries', {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.status !== 200) {
                    response.json()
                        .then(error => {
                            console.log(error.msg);
                        })
                } else {
                    response.json()
                        .then(queries => {
                            setQueries(queries);
                        })
                }
            })

        }, [])

    const handleLogout = (e) => {
        
        e.preventDefault()
        setError("")
        fetch('https://sinappsa.onrender.com/sinapsa/logout', {
            method: 'POST',
            headers: { "Content-Type": "application/json"}
        }).then(response => {
            setUser(null)
            onLogout()
            setUserAuthorised(false)
            sessionStorage.removeItem("User")
            navigate('/sinapsa')
        })
    }

    function handleEdit(){
        navigate('/sinapsa/editProfile'); 
    }

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
            {userAuthorised ?
                <div>   
                    <div className='s-profile-display-container' onClick={handleProfileDisplay}>
                        <h2 className='s-displayProfile-h2'>{profileStateMsg} profil</h2>
                        {
                        showProfile 
                            ? <img src={process.env.PUBLIC_URL + '/images/closingX.png'} className="s-mediumIcon" alt="Closing X icon"/>
                            : <img src={process.env.PUBLIC_URL + '/images/magnifying-glass.png'} className="s-mediumIcon" alt="Magnifying glass icon"/>
                        }
                    </div>
                    {showProfile && <div className="s-personalProfile-container">
                        <Profile user={{firstName, lastName, nickname, email, avatar}}/>
                        <div className="s-personalProfile-group-btn">
                            <button type="button" onClick={handleEdit} className="s-personalProfile-btn s-personalProfile-btn-primary">Uredi profil</button>
                            <button type="button" onClick={handleLogout} className="s-personalProfile-btn s-personalProfile-btn-primary">Odjavi se</button>
                        </div>
                    </div>}
                    <div className="container">
                        <div className="row justify-content-center my-4">
                            <div className="col-auto">
                                <ToggleButtonGroup
                                    value={dataToDisplay}
                                    exclusive
                                    onChange={(event, newValue) => setDataToDisplay(newValue)}
                                >
                                    <ToggleButton value="notices">
                                        <h2 className="s-displayNotice-h2">Popis oglasa</h2>
                                    </ToggleButton>
                                    <ToggleButton value="queries">
                                        <h2 className="s-displayNotice-h2">Popis upita</h2>
                                    </ToggleButton>
                                </ToggleButtonGroup>
                            </div>
                        </div>
                        {
                            dataToDisplay === "notices" &&
                            notices
                                .map(notice =>
                                    <div key={notice.id} className="row">
                                        <Notice key={notice.id} notice={notice}/>
                                    </div>
                                )
                        }
                        {
                            dataToDisplay === "queries" &&
                            queries.map(query =>
                                <div key={query.id} className="row">
                                    <Query key={query.id} query={query}/>
                                </div>
                            )
                        }
                    </div>
                </div> :
                <div className="s-profile-container">
                    {error && <div className="s-profile-group s-error">{error}</div>}
                </div>}
        </div>
    )
}
