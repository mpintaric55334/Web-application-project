import 'bootstrap/dist/css/bootstrap.css';
import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {CircularProgress, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";

const DeleteNoticeButton = (props) => {
    const notice = props.notice;
    const navigate = useNavigate();

    const [open, setOpen] = useState(false);
    const [showProgress, setShowProgress] = useState(false);

    const [message, setMessage] = useState("Oglas je uklonjen zbog kršenja pravila!");
    const [error, setError] = useState("");

    const handleClickOpen = () => {
        setError("");
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleDelete = () => {
        setError("");
        setShowProgress(true);

        const data = {
            userId: JSON.parse(sessionStorage.User).id,
            noticeId: notice.id,
            message: message
        }

        fetch('https://sinappsa.onrender.com/sinapsa/moderatorDeleteNotice', {
            method: 'POST',
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify(data)
        })
            .then(response => {
                setShowProgress(false);
                if(response.status !== 200) {
                    response.json()
                        .then(error => {
                            setError(error.msg)
                        })
                } else {
                    setOpen(false);

                    if (window.location.pathname === "/sinapsa/notice")
                        navigate("/sinapsa");
                    else
                        window.location.reload();
                }
            })
    }


    return (
        <div>
            <button className="btn btn-danger" onClick={handleClickOpen}>
                {props.children}
            </button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>
                    {
                        JSON.parse(sessionStorage.User).id === notice.userId ?
                            <label>Jeste li sigurni da želite izbrisati ovaj oglas?</label> :
                            <label>Unesite poruku za vlasnika oglasa:</label>
                    }
                </DialogTitle>
                <DialogContent>
                    {
                        JSON.parse(sessionStorage.User).id !== notice.userId &&
                        <div>
                            <textarea className="p-3" rows="5" cols="50" value={message} onChange={(e) => setMessage(e.target.value)}/>
                        </div>
                    }
                    {
                        error &&
                        <div className="s-form-group s-error">{error}</div>
                    }
                    <div className="container-fluid">
                        <div className="row justify-content-center">
                            <div className="col-auto">
                                {
                                    showProgress &&
                                    <CircularProgress/>
                                }
                            </div>
                        </div>
                    </div>
                </DialogContent>
                <DialogActions>
                    <button className="btn btn-warning" onClick={handleClose}>
                        Otkaži
                    </button>
                    <button className="btn btn-danger" onClick={handleDelete}>
                        Izbriši
                    </button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default DeleteNoticeButton;
