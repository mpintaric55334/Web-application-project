import 'bootstrap/dist/css/bootstrap.css';
import './Student.css';
import React from 'react';
import {Link} from "react-router-dom";
import {Avatar} from "@mui/material";

function Student(props) {
    const {id, avatar, studentNickname, rank, numberOfAcceptedNotices, averageGrade, rankingPoints} = props.student;

    return (
        <div className="container-fluid border border-dark my-2">
            <div className="row align-items-center">
                <div className="col-auto me-auto p-1">
                    <span className="m-2">{rank}</span>
                </div>
                <div className="col-auto p-1">
                    <Avatar
                        alt={avatar}
                        src={process.env.PUBLIC_URL + '/images/avatars/' + avatar + '.png'}
                        sx={{ width: "3rem", height: "3rem"}}
                    />
                </div>
                <div className="col-auto me-auto p-1">
                    <Link to='/sinapsa/StudentProfile' state={{userId: id, postNickname: studentNickname}} className='s-profile-link'><span className="m-2">{studentNickname}</span></Link>
                </div>
                <div className="col-auto p-1">
                    <span className="m-2">{numberOfAcceptedNotices}</span>
                    <img src={process.env.PUBLIC_URL + '/images/acceptedNoticesIcon.png'} className="s-smallIcon" alt="Notice Icon"/>
                </div>
                <div className="col-auto p-1">
                    <span className="m-2">{averageGrade.toFixed(1)}</span>
                    <img src={process.env.PUBLIC_URL + '/images/gradeStar.png'} className="s-smallIcon" alt="Star Icon"/>
                </div>
                <div className="col-auto p-1">
                    <span className="m-2">{rankingPoints}</span>
                    <img src={process.env.PUBLIC_URL + '/images/trophy.png'} className="s-smallIcon" alt="Trophy Icon"/>
                </div>
            </div>
        </div>
    );
}

export default Student;