import 'bootstrap/dist/css/bootstrap.css';
import React, {useState} from 'react';
import {Rating, CircularProgress, Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";

const GradeQueryButton = (props) => {
    const userId = JSON.parse(sessionStorage.User).id;
    const queryId = props.queryId;

    const [open, setOpen] = useState(false);
    const [showProgress, setShowProgress] = useState(false);

    const [grade, setGrade] = useState(1);

    const [error, setError] = useState("");

    const handleClickOpen = () => {
        setError("");
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleGrade = () => {
        setError("");
        setShowProgress(true);

        const data = {
            queryId: queryId,
            appUserId: userId,
            grade: grade
        }

        fetch('https://sinappsa.onrender.com/sinapsa/gradeUser', {
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
                    window.location.reload();
                }
            })
    }


    return (
        <div>
            <button className="btn s-notice-query-send-btn" onClick={handleClickOpen}>
                {props.children}
            </button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>
                    <label>Ocijeni studenta pomagača</label>
                </DialogTitle>
                <DialogContent>
                    <div className="container-fluid">
                        <div className="row">
                            <div className="col">
                                <p>Ocijenite svog studenta pomagača:</p>
                            </div>
                        </div>
                        <div className="row justify-content-center">
                            <div className="col-auto">
                                <Rating
                                    name="user-grade"
                                    size="large"
                                    value={grade}
                                    onChange={(event, newValue) => setGrade(newValue)}
                                />
                            </div>
                        </div>

                    </div>

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
                    <button className="btn s-notice-query-send-btn" onClick={handleGrade}>
                        Ocijeni
                    </button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default GradeQueryButton;
