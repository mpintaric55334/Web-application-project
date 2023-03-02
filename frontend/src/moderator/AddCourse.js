import 'bootstrap/dist/css/bootstrap.css';
import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";


const AddCourse = () => {
    const navigate = useNavigate();
    const user = sessionStorage.User === undefined ? {} : JSON.parse(sessionStorage.User);

    const [majors, setMajors] = useState([]);
    const [error, setError] = useState("");

    const [selectedMajorId, setSelectedMajorId] = useState(null);
    const [courseName, setCourseName] = useState("");

    useEffect(() => {
        if(!user.moderator) {
            navigate('/sinapsa');
        }

        fetch('https://sinappsa.onrender.com/sinapsa/majors', {
            method: 'GET',
            headers: {"Content-Type": "application/json"}
        }).then(response => {
            if (response.status !== 200) {
                response.json()
                    .then(error => {
                        setError(error.msg)
                    })
            } else {
                response.json()
                    .then(data => {
                        setMajors(data);
                    })
            }
        })
    }, [])

    useEffect(() => setSelectedMajorId(majors[0]?.id), [majors]);

    const handleSubmit = e => {
        e.preventDefault()
        setError("")
        const data = {
            userId: user.id,
            majorId: selectedMajorId,
            courseName: courseName
        };

        fetch('https://sinappsa.onrender.com/sinapsa/addCourse', {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        }).then(response => {
            if (response.status >= 300) {
                response.json()
                    .then(error => {
                        setError(error.msg)
                    })
            } else {
                navigate("/sinapsa");
            }
        })
    }

    return (
        <div>
            <form className='container' onSubmit={handleSubmit}>

                <div className="row justify-content-center my-3">
                    <div className="col-auto">
                        <h2>Dodavanje kolegija</h2>
                    </div>
                </div>

                <div className="row justify-content-center align-items-center m-5">
                    <div className="col-auto m-3">
                        <label htmlFor="major">Smjer:</label>
                        <select name="major" defaultValue={selectedMajorId} value={selectedMajorId || 0}
                                onChange={e => setSelectedMajorId(e.target.value)}>
                            {
                                majors.map(major => <option key={major.id} value={major.id}>{major.majorName}</option>)
                            }
                        </select>
                    </div>
                    <div className="col-auto m-3">
                        <label>Ime kolegija:</label>
                        <input type="text" required className="s-form-control" id="title" placeholder="Unesite ime"
                               value={courseName} onChange={(e) => setCourseName(e.target.value)}
                        />
                    </div>
                </div>

                {error && <div className="s-form-group s-error">{error}</div>}

                <div className='row justify-content-center'>
                    <div className="col-auto">
                        <button type="submit" className="s-btn s-btn-primary">Spremi</button>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default AddCourse;
