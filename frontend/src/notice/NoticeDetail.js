import React from 'react';
import Notice from './Notice';
import Query from '../query/Query';
import {useLocation, useNavigate} from 'react-router-dom';
import {useState, useEffect} from 'react';

const NoticeDetail = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const noticeId = location.state?.noticeId;
    const user = sessionStorage.User === undefined ? null : JSON.parse(sessionStorage.User);

    const [notice, setNotice] = useState({});
    const [queries, setQueries] = useState([]);

    const fetchNotice = () => {
        fetch('https://sinappsa.onrender.com/sinapsa/getNotice', {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({id: noticeId})
        })
            .then(response => {
                if (response.status !== 200) {
                    response.json()
                        .then(error => {
                            console.log(error.msg);
                        })
                } else {
                    response.json()
                        .then(notice => {
                            setNotice(notice);
                        })
                }
        })
    };

    const fetchQueries = () => {
        const data = {
            noticeId: noticeId,
            appUserId: user?.id
        }

        fetch('https://sinappsa.onrender.com/sinapsa/getNoticeQueries', {
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
    };

    useEffect(() => {
        if (noticeId === undefined) {
            navigate('/sinapsa');
        }
        fetchNotice();
        fetchQueries()
    }, []);

    return (
    <div className="container">
        <div className="row">
            <h2 className="text-center s-notice-h2">Pregled oglasa</h2>
        </div>
        <div className="row">
            <Notice isDetailedView={true} notice={notice}/>
        </div>
        {
            queries.map(query => <Query key={query.id} query={query}/>)
        }
    </div>
    )
}

export default NoticeDetail;
