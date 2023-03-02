import { useEffect, useState } from 'react'
import Student from './Student';

export default function Rankings() {
    const [error, setError] = useState('');
    const [rankings, setRankings] = useState([])

    useEffect(() => {
        fetch('https://sinappsa.onrender.com/sinapsa/rankings', {
            method: 'GET',
            headers: { "Content-Type": "application/json"}
        }).then(response => {
            if (response.status !== 200){
                response.json()
                .then(error=> {
                    setError(error.msg)
                })
            } else {
                response.json()
                    .then(students => {
                        setRankings(students)
                    })
            }
        })
    }, [])

  return (
    <div className="container">
        <div className="row">
            <h2 className="text-center">Studenti pomagači</h2>
        </div>
        {
            error ? <div className="s-form-group s-error">{error}</div> 
                  : rankings.map((student) => ( <Student key={student.id} student={student}/> ))
        }
    </div>
  );
}
