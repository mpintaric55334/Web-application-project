import 'bootstrap/dist/css/bootstrap.css';
import {useEffect, useState} from 'react';
import Notice from "../notice/Notice";
import NoticeFilter from "../notice/NoticeFilter";
import {useNavigate} from "react-router-dom";

function Home(props) {
  const user = sessionStorage.User === undefined ? {} : JSON.parse(sessionStorage.User);
  const navigate = useNavigate();
  const [error, setError] = useState("");
  //to enable display for testing, set values to true
  const [majorsReady, setMajorsReady] = useState(false)
  const [coursesReady, setCoursesReady] = useState(false)
  const [categoriesReady, setCategoriesReady] = useState(false)

  const [majors, setMajors] = useState([]);
  const [courses, setCourses] = useState([]);
  const [categories, setCategories] = useState([]);

  const [selectedMajorId, setSelectedMajorId] = useState(null);
  const [selectedCourseId, setSelectedCourseId] = useState(null);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [notices, setNotices] = useState([]);
  const [noNoticesMsg, setNoNoticesMsg] = useState("");

  const [displayNotices, setDisplayNotices] = useState("BOTH");

  const displayFilter = (notice) => {
    if (displayNotices === "BOTH")
      return true;

    if (displayNotices === "SEEKING_HELP")
      return notice.isAskingHelp

    if (displayNotices === "PROVIDING_HELP")
      return !notice.isAskingHelp
  }

  function applyFilter(majorId, courseId, category) {
    if(category === "Sve kategorije") {
      category = null;
    }
    if(majorId === "Svi smjerovi") {
      majorId = null;
    }
    if(courseId === "Svi predmeti") {
      courseId = null;
    }

    setSelectedMajorId(majorId)
    setSelectedCourseId(courseId)
    setSelectedCategory(category)
  }

  function addCourseEvent(){
    navigate('/sinapsa/addCourse')
  }

  function createNoticeEvent(){
    if (sessionStorage.User === undefined) 
      navigate("/sinapsa/login")
    
    navigate('/sinapsa/editNotice', { state: {notice: {}, newNotice: true} })
  }

  function fetchNotices() {
    const data = {
      majorId: selectedMajorId,
      courseId: selectedCourseId,
      category: selectedCategory
    }

    fetch('https://sinappsa.onrender.com/sinapsa/homeNotices', {
      method: 'POST',
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(data)
    })
      .then(response => {
        if (response.status !== 200) {
          response.json()
            .then(error => {
              setError(error.msg);
              setNotices([]);
            })
        } else {
          response.json()
            .then(notices => {
              setNotices(notices);
              if(notices.length === 0)
                setNoNoticesMsg("Trenutno nema raspoloživih oglasa")
              else
                setNoNoticesMsg("")
            })
        }
      })
  }

  function fetchMajors() {
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
            data.splice(0, 0, {id:null, majorName:"Svi smjerovi"})
            setMajors(data)
            setMajorsReady(true)
          })
      }
    })
  }

  function fetchCategories() {
    fetch('https://sinappsa.onrender.com/sinapsa/categories', {
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
            data.splice(0, 0, {noticeCategory:null})
            setCategories(data)
            setCategoriesReady(true)
          })
      }
    })
  }

  function fetchCourses() {
    fetch('https://sinappsa.onrender.com/sinapsa/courses', {
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
            data.splice(0, 0, {id:null, courseName: "Svi predmeti"})
            setCourses(data)
            setCoursesReady(true)
          })
      }
    })
  }

  useEffect(() => {
    fetchMajors()
    fetchCourses()
    fetchCategories()
  }, [])

  useEffect(() => {
    fetchNotices()
  }, [selectedMajorId, selectedCourseId, selectedCategory])

  return (
    <div className="container">
      <div className="row justify-content-center my-3">
        <div className="col-auto">
          <h2>Oglasi</h2>
        </div>
      </div>

      <div className="row justify-content-end align-items-center">
        {
            user?.moderator &&
            <div className="col-auto">
                <button className="btn btn-warning" onClick={addCourseEvent} name="addCourse-btn">Dodaj kolegij</button>
            </div>
        }

        {
            <div className="col-auto">
                <button className="s-btn s-btn-primary" onClick={createNoticeEvent} name="createNotice-btn">Stvori oglas</button>
            </div>
        }
      </div>

      <div className="row my-3">
        <div className="col">
          {
              majorsReady && coursesReady && categoriesReady &&
              <NoticeFilter majors={majors}
                            courses={courses}
                            categories={categories}
                            applyFilter={applyFilter}
                            setDisplayNotices={setDisplayNotices}
              />
          }
        </div>
      </div>

      {
        notices.length > 0 ?
        (notices
          .filter(notice => notice.active)
          .filter(displayFilter)
          .map(notice => (
            <div key={notice.id} className="row">
              <Notice key={notice.id} notice={notice}/>
            </div>
          )))
        : (<div className='s-no-notices-container'>
            <h3 className="s-no-notices-h3">{noNoticesMsg}</h3>
          </div>)
      }
    </div>
  );
}

export default Home;
