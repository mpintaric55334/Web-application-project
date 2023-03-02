import React from 'react'
import {Avatar} from "@mui/material";

export default function Profile(props) {
    const { firstName, lastName, nickname, email, avatar } = props.user;

    return (
        <div>
            {
                firstName &&
                <div className='s-profile-container'>
                    <div className='s-profile-group'>
                        <Avatar
                            alt={avatar}
                            src={process.env.PUBLIC_URL + '/images/avatars/' + avatar + '.png'}
                            sx={{ width: "12rem", height: "12rem"}}
                        />
                    </div>
                    <div className='s-profile-group'>
                        <span className='s-profile-desc'>Ime: </span> {firstName}
                    </div>
                    <div className='s-profile-group'>
                        <span className='s-profile-desc'>Prezime: </span> {lastName}
                    </div>
                    <div className='s-profile-group'>
                        <span className='s-profile-desc'>Korisničko ime: </span> {nickname}
                    </div>
                    <div className='s-profile-group'>
                        <span className='s-profile-desc'>E-mail: </span> {email}
                    </div>
                </div>
            }
        </div>
    )
}
