import 'bootstrap/dist/css/bootstrap.css';
import {React, useEffect, useState} from 'react';
import {Avatar} from "@mui/material";
import {Link, useNavigate} from "react-router-dom";
import GradeQueryButton from "./GradeQueryButton";

const Query = props => {
    const navigate = useNavigate();

    const {id, message, status, isAskingHelp, noticeId, senderId, senderNickname, senderAvatar, receiverId} = props.query;
    const user = sessionStorage.User === undefined ? null : JSON.parse(sessionStorage.User);

    const [displayGradeButton, setDisplayGradeButton] = useState(false);
    const [displayDeclineButton, setDisplayDeclineButton] = useState(false);

    const initDisplayButtons = () => {
        if (status !== "IN_PROGRESS" || user === null) {
            setDisplayGradeButton(false);
            setDisplayDeclineButton(false);
            return;
        }

        setDisplayDeclineButton(user.id === receiverId);

        if (isAskingHelp)
            setDisplayGradeButton(user.id === receiverId);
        else
            setDisplayGradeButton(user.id === senderId);
    }

    const handleReroute = () => {
        navigate('/sinapsa/notice', { state: {noticeId: noticeId} });
    }

    const decline = () => {
        const data = {
            queryId: id,
            appUserId: user?.id
        }

        fetch('https://sinappsa.onrender.com/sinapsa/rejectQuery', {
            method: 'POST',
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify(data)
        })
            .then(response => {
                if(response.status !== 200) {
                    response.json()
                        .then(error => {
                            console.log(error.msg)
                        })
                } else {
                    window.location.reload();
                }
            })
    }

    useEffect(() => {
        initDisplayButtons();
    }, []);

    return (
        <div className={"container-fluid rounded border border-dark my-4 py-3 "
            + (isAskingHelp ? "s-notice-background" : "s-help-provider-notice-background")}>
            <div className="row justify-content-between">
                <div className="col">
                    <div className="s-center">
                        <Avatar
                            className="mx-2"
                            alt={senderAvatar}
                            src={process.env.PUBLIC_URL + '/images/avatars/' + senderAvatar + '.png'}
                            sx={{width: "3rem", height: "3rem"}}
                        />
                        <Link to='/sinapsa/StudentProfile' state={{postNickname: senderNickname, userId: senderId}}
                              className='s-profile-link mx-2'>
                            <span>{senderNickname}</span>
                        </Link>
                    </div>
                </div>
                {
                    window.location.pathname === '/sinapsa/personalProfile' &&
                    <div className="col-auto">
                        <div className="s-link s-cursor" onClick={handleReroute}>
                            <span className="mx-2 s-link-to-notice">Prikaži oglas</span>
                            <img src={process.env.PUBLIC_URL + '/images/magnifying-glass.png'}
                                 className="s-query-smallIcon mx-2" alt="Magnifying glass icon"/>
                        </div>
                    </div>
                }
            </div>
            <div className="row p-2">
                <div className="col">
                    <p>{message}</p>
                </div>
            </div>
            <div className="row justify-content-end">
                {
                    displayGradeButton &&
                    <div className="col-auto my-1">
                        <GradeQueryButton queryId={id}>Ocijeni</GradeQueryButton>
                    </div>
                }
                {
                    displayDeclineButton &&
                    <div className="col-auto my-1">
                        <button className="btn btn btn-danger" onClick={decline}>Odbij</button>
                    </div>
                }
            </div>
        </div>
    );
};

export default Query;