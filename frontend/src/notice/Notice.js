import 'bootstrap/dist/css/bootstrap.css';
import {React, useState} from 'react';
import {Link} from "react-router-dom";
import {Avatar, CircularProgress} from "@mui/material";
import DeleteNoticeButton from "./DeleteNoticeButton";
import noticeCategory from "../util/NoticeCategory";

function Notice(props) {
  const isDetailedView = props.isDetailedView
  const [error, setError] = useState();
  const {id, userId, userAvatar, userNickname, majorName, courseName, category, title, description, active, isAskingHelp} = props.notice
  const [showQueryDisplay, setShowQueryDisplay] = useState(false);
  const [queryMsg, setQueryMsg] = useState("");
  const [queryDisplayBtnText, setQueryDisplayBtnText] = useState("Postavi upit");
  const bullet = String.fromCodePoint(187)

  const [showProgress, setShowProgress] = useState(false);

  function updateQueryDisplay() {
    queryDisplayBtnText === "Postavi upit" ? setQueryDisplayBtnText("Zatvori upit") : setQueryDisplayBtnText("Postavi upit")
    setShowQueryDisplay(!showQueryDisplay)
  }

  const activation = event => {

    const data = {
      noticeId: id,
      userId: JSON.parse(sessionStorage.getItem("User")).id
    };

    fetch('https://sinappsa.onrender.com/sinapsa/' + event.target.id, {
      method: 'POST',
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(data)
    }).then(response => {
      if (response.status > 299) {
        response.json()
            .then(error => {
              setError(error.msg)
            })
      } else {
        window.location.reload();
      }
    })
  }

  const sendQuery = (e) => {
    e.preventDefault();
    setShowProgress(true);

    const data = {
      noticeId: id,
      senderId: JSON.parse(sessionStorage.getItem("User")).id,
      queryMsg: queryMsg
    };

    fetch('https://sinappsa.onrender.com/sinapsa/createQuery', {
      method: 'POST',
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(data)
    }).then(response => {
      setShowProgress(false);
      if (response.status !== 200) {
        response.json()
          .then(error => {
            setError(error.msg)
          })
      } else {
        setQueryMsg("")
        setShowQueryDisplay(false)
        setQueryDisplayBtnText("Postavi upit")
        window.location.reload();
      }
    })
  }

  function isValid() {
    return queryMsg.length > 0;
  }

  return (
    <div className={"container-fluid rounded border border-dark my-4 py-3 "
                    + (isAskingHelp ? "s-notice-background" : "s-help-provider-notice-background")}>
      <div className="row">
        <div className={"col-auto small rounded border border-dark m-2 p-2 "
                        + (isAskingHelp ? "s-notice-tag" : "s-help-provider-notice-tag")}>{majorName}</div>
        <div className={"col-auto small rounded border border-dark m-2 p-2 "
                        + (isAskingHelp ? "s-notice-tag" : "s-help-provider-notice-tag")}>{courseName}</div>
        <div className={"col-auto small rounded border border-dark m-2 p-2 "
                        + (isAskingHelp ? "s-notice-tag" : "s-help-provider-notice-tag")}>{noticeCategory(category)}</div>
      </div>
      <div className="row">
        <div className="col-auto s-center">
          <Avatar
            alt={userAvatar}
            src={process.env.PUBLIC_URL + '/images/avatars/' + userAvatar + '.png'}
            sx={{width: "3rem", height: "3rem"}}
          />
        </div>
        <div className="col-auto">
          <Link to='/sinapsa/StudentProfile' state={{postNickname: userNickname, userId: userId}}
                className='s-profile-link'>
            <span>{userNickname}</span>
          </Link>
        </div>
      </div>
      <div className="row">
        <div className="col"><h4 className="s-notice-italic">{bullet} {title}</h4></div>
      </div>
      <div className="row">
        <div className={"col rounded border border-dark m-2 text-start "
                        + (isAskingHelp ? "s-notice-textarea" : "s-help-provider-notice-textarea")}>
          {description}
        </div>
      </div>
      <div className="row justify-content-end">

        {
          !isDetailedView &&

          <div className="col-auto my-1">
            <Link to='/sinapsa/notice' state={{noticeId: props.notice.id}}>
              <button type="button" className="btn s-btn-primary">Detalji</button>
            </Link>
          </div>
        }

        {
          sessionStorage.getItem("User") !== null &&
          userNickname === JSON.parse(sessionStorage.User).nickname &&
          active &&

          <div className="col-auto my-1">
            <Link to='/sinapsa/editNotice' state={{notice: props.notice}}>
              <button type="button" className="btn btn-warning">Uredi</button>
            </Link>
          </div>
        }

        {
            sessionStorage.getItem("User") !== null &&
            active &&
            userNickname === JSON.parse(sessionStorage.User).nickname &&

            <div className="col-auto my-1">
              <button className="btn s-activation-button" id="deactivateNotice" onClick={activation}>Deaktiviraj</button>
            </div>
        }

        {
            sessionStorage.getItem("User") !== null &&
            !active &&
            userNickname === JSON.parse(sessionStorage.User).nickname &&

            <div className="col-auto my-1">
              <button className="btn s-activation-button" id="activateNotice" onClick={activation}>Aktiviraj</button>
            </div>
        }

        {
          sessionStorage.getItem("User") !== null &&
          (userNickname === JSON.parse(sessionStorage.User).nickname
            || JSON.parse(sessionStorage.getItem("User")).moderator
          ) &&

          <div className="col-auto my-1">
            <DeleteNoticeButton notice={props.notice}>Izbriši</DeleteNoticeButton>
          </div>
        }

        {
          isDetailedView &&
          sessionStorage.getItem("User") !== null &&
          userNickname !== JSON.parse(sessionStorage.User).nickname &&

          <div className="col-auto my-1">
            <button type="button" className="btn s-btn-primary"
                    onClick={updateQueryDisplay}>{queryDisplayBtnText}</button>
          </div>
        }

      </div>
      {
        showQueryDisplay &&
        <form className='s-notice-query-container' onSubmit={sendQuery}>
          <label htmlFor='queryTextArea' className='s-notice-query-textarea-label'>Unesite upit:</label>
          <textarea id='queryTextArea' name='queryTextArea' className='s-notice-query-textarea' rows='5' cols='100'
                    maxLength={"500"}
                    placeholder='Max. 500 slova' value={queryMsg}
                    onChange={(e) => setQueryMsg(e.target.value)}></textarea>
          <div className='s-notice-query-form-group-btn'>
            {
                showProgress &&
                <CircularProgress/>
            }
            <button type="submit" className="btn s-btn-primary s-notice-query-send-btn" disabled={!isValid()}>
              Pošalji upit
            </button>
          </div>
        </form>
      }
    </div>
  );
}

export default Notice;
