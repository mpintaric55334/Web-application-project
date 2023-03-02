import * as React from 'react';

import 'bootstrap/dist/css/bootstrap.css';

import {Avatar, Button, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";

function AvatarDialog(props) {
    const [open, setOpen] = React.useState(false);

    const avatar    = props.avatar;                                             //Carry state from parent
    const setAvatar = props.setAvatar;

    const avatars = ["avatar-1", "avatar-2" , "avatar-3", "avatar-4"]           //Later need to be changed to fetch values from database

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleAvatarPick = (event) => {
        setAvatar(event.currentTarget.name);
        setOpen(false);
    }

    return (
        <div className="s-form-group">
            <label>Avatar</label>
            <Button onClick={handleClickOpen}>
                {
                    <Avatar
                        alt={avatar}
                        src={process.env.PUBLIC_URL + '/images/avatars/' + avatar + '.png'}
                        sx={{width: "12rem", height: "12rem"}}
                    />
                }
            </Button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>
                    <label>Odaberi Avatar</label>
                </DialogTitle>
                <DialogContent>
                    <div className="s-form-group">
                        {avatars.map(avatar => {
                            return (
                                <Button name={avatar} onClick={handleAvatarPick} variant="outlined" sx={{ margin: "6px"}}>
                                    <Avatar
                                        alt={avatar}
                                        src={process.env.PUBLIC_URL + '/images/avatars/' + avatar + '.png'}
                                        sx={{ width: "6rem", height: "6rem"}}
                                    />
                                </Button>
                            );
                        })}
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} sx={{ fontSize: "1rem"}}>Otkaži</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default AvatarDialog;