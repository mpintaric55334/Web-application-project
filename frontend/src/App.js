import 'bootstrap/dist/css/bootstrap.css';
import { useState, useEffect, createContext } from 'react'
import Header from './home_page/Header';
import { BrowserRouter, Route, Navigate,Routes } from 'react-router-dom';
import Home from './home_page/Home';
import Login from './login/Login';
import Register from './register/Register';
import Rankings from './ranking/Rankings';
import NoticeDetail from './notice/NoticeDetail';
import PersonalProfile from './profile/PersonalProfile';
import EditProfile from './profile/EditProfile';
import VerifyMail from './register/VerifyMail';
import { userContext } from './Contexts/UserContext';
import StudentProfile from './profile/StudentProfile';
import EditNotice from "./notice/EditNotice";
import AddCourse from "./moderator/AddCourse";

function App() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [nickname, setNickname] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [avatar, setAvatar] = useState('');
    const [userId, setUserId] = useState('');
    const [isLoggedIn, setIsLoggedIn] = useState(false)

    function onLogin() {
      setIsLoggedIn(true);
    }

    function onLogout() {
      setIsLoggedIn(false)
    }

    function setUser(data) {
      if(data === null){
        setFirstName(null);
        setLastName(null);
        setNickname(null);
        setUserId(null);
        setEmail(null);
        setPassword(null);
        setAvatar(null);
        return
      } 
      setFirstName(data.firstName);
      setLastName(data.lastName);
      setNickname(data.nickname);
      setUserId(data.id);
      setEmail(data.email);
      setPassword(data.password);
      setAvatar(data.avatar);
    }

    /*function dummy(){
      const data = {
        firstName: "Fran", 
        lastName: "zu", 
        nickname: "zule", 
        email: "email",
        avatar: "avatar-4",
        password: "password"
      };
      console.log(firstName)
      sessionStorage.setItem("User",JSON.stringify(data))
    }*/

    useEffect(() => {    
      if(sessionStorage.getItem("User") === null){  
        setIsLoggedIn(false);
        //dummy();
      } else {
        setIsLoggedIn(true);
        setUser(JSON.parse(sessionStorage.getItem("User")))
      }
    }, [])

    return (
    <BrowserRouter>
      <Header isLoggedIn={isLoggedIn}/>
        <div className="App">
          <userContext.Provider value={{firstName, lastName, nickname, userId, email, avatar, setUser, setIsLoggedIn, onLogout}}>
            <Routes>
              <Route path='/' element={<Navigate to='/sinapsa'/>}/>
              <Route path='/sinapsa' element={<Home isLoggedIn={isLoggedIn}/>}/>
              <Route path='/sinapsa/registration' element={<Register/>}/>
              <Route path='/sinapsa/login' element={<Login onLogin={onLogin} setUser={setUser}/>}/>
              <Route path='/sinapsa/notice' element={<NoticeDetail/>}/>
              <Route path='/sinapsa/rankings' element={<Rankings/>}/>            
              <Route path='/sinapsa/personalProfile' element={<PersonalProfile/>}/>
              <Route path='/sinapsa/editProfile' element={<EditProfile onLogout={onLogout} setUser={setUser}/>}/>
              <Route path='/sinapsa/StudentProfile' element={<StudentProfile/>}/>
              <Route path='/sinapsa/verify' element={<VerifyMail/>}/>
              <Route path='/sinapsa/editNotice' element={<EditNotice/>}/>
              <Route path='/sinapsa/addCourse' element={<AddCourse/>}/>
            </Routes>
          </userContext.Provider>
        </div>
    </BrowserRouter>
    );
}

export default App;
