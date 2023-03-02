import 'bootstrap/dist/css/bootstrap.css';
import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './index.css';
import './home_page/Header.css';
import './login/Login.css';
import './profile/Profile.css';
import './notice/Notice.css';
import './notice/NoticeFilter.css';
import './query/Querry.css';
import './ranking/Student.css';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

