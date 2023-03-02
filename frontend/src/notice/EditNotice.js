import 'bootstrap/dist/css/bootstrap.css';
import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import noticeCategory from "../util/NoticeCategory";
import {CircularProgress} from "@mui/material";


const EditNotice = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const verifyLocationState = () => {
    return location.state !== null;
  };

  const [showProgress, setShowProgress] = useState(false);

  const [majors, setMajors] = useState([]);
  const [courses, setCourses] = useState([]);
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState("");

  const [selectedMajorId, setSelectedMajorId] = useState(null);
  const [selectedCourseId, setSelectedCourseId] = useState(null);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [isAskingHelp, setIsAskingHelp] = useState(true);
  const [title, setTitle] = useState(verifyLocationState() ? location.state.notice.title : "");
  const [description, setDescription] = useState(verifyLocationState() ? location.state.notice.description : "");

  const initMajor = () => {
    if (!location.state.notice.majorName) {
      setSelectedMajorId(majors?.[0]?.id);
      return;
    }

    for (let major of majors) {
      if (location.state.notice.majorName === major.majorName) {
        setSelectedMajorId(major.id);
        break;
      }
    }
  }

  const initCourse = () => {
    if (!location.state.notice.courseName) {
      setSelectedCourseId(courses?.[0]?.id);
      return;
    }

    for (let course of courses) {
      if (location.state.notice.courseName === course.courseName) {
        setSelectedCourseId(course.id);
        break;
      }
    }
  }

  const initCategory = () => {
    if (!location.state.notice.category) {
      setSelectedCategory(categories?.[0]?.noticeCategory);
      return;
    }

    for (let category of categories) {
      if (location.state.notice.category === category.noticeCategory) {
        setSelectedCategory(category.noticeCategory);
        break;
      }
    }
  }

  useEffect(() => {
    if (!verifyLocationState()) {
      navigate("/sinapsa");
    }

    if (sessionStorage.User === undefined) {
      navigate("/sinapsa/login");
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
            setCategories(data)
          })
      }
    })
  }, [])

  useEffect(() => {
    fetch("https://sinappsa.onrender.com/sinapsa/specified-courses", {
      method: 'POST',
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify({majorId: selectedMajorId})
    }).then(response => {
      if (response.status !== 200) {
        response.json()
          .then(error => {
            setError(error.msg)
          })
      } else {
        response.json()
          .then(data => {
            setCourses(data);
          })
      }
    })
  }, [selectedMajorId])

  useEffect(() => initMajor(), [majors]);
  useEffect(() => initCourse(), [courses]);
  useEffect(() => initCategory(), [categories]);

  const handleSubmit = e => {
    e.preventDefault()
    setError("")
    setShowProgress(true);
    const data = {
      title: title,
      description: description,
      isAskingHelp: isAskingHelp,
      majorId: selectedMajorId,
      courseId: selectedCourseId,
      category: selectedCategory,
      userId: JSON.parse(sessionStorage.User).id,
      noticeId: verifyLocationState() ? location.state.notice.id : null
    };

    fetch('https://sinappsa.onrender.com/sinapsa/editNotice', {
      method: 'POST',
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(data)
    }).then(response => {
      setShowProgress(false)
      if (response.status !== 200) {
        response.json()
          .then(error => {
            setError(error.msg)
          })
      } else {
        response.json()
          .then(notice => {
            navigate('/sinapsa');
            //Returns notice.id, might not need it
          })
      }
    })
  }

  return (
    <div>
      <form className='container' onSubmit={handleSubmit}>

        {
          location.state.newNotice
              ?
              <div className="row justify-content-center my-3">
                <div className="col-auto">
                  <h2>Stvori oglas</h2>
                </div>
              </div>
              :
              <div className="row justify-content-center my-3">
                <div className="col-auto">
                  <h2>Uredi oglas</h2>
                </div>
              </div>
        }

        <div className="row">
          <div className="col-auto">
            <label htmlFor="major">Smjer:</label>
            <select name="major" defaultValue={selectedMajorId} value={selectedMajorId || 0}
                    onChange={e => setSelectedMajorId(e.target.value)}>
              {
                majors.map(major => <option key={major.id} value={major.id}>{major.majorName}</option>)
              }
            </select>
          </div>
          <div className="col-auto">
            <label htmlFor="course">Kolegij:</label>
            <select name="course" defaultValue={0} value={selectedCourseId || 0}
                    onChange={e => setSelectedCourseId(e.target.value)}>
              {
                courses.map(course => <option key={course.id} value={course.id}>{course.courseName}</option>)
              }
            </select>
          </div>
          <div className="col-auto">
            <label htmlFor="category">Kategorija:</label>
            <select name="category" defaultValue={0} value={selectedCategory || ''}
                    onChange={e => setSelectedCategory(e.target.value)}>
              {
                categories.map((category, index) =>
                  <option key={index}
                          value={category.noticeCategory}>{noticeCategory(category.noticeCategory)}</option>)
              }
            </select>
          </div>
          {
            location.state.newNotice &&
            <div className="col-auto">
              <label htmlFor="isAskingHelp">Vrsta oglasa:</label>
              <select name="isAskingHelp" defaultValue="1"
                    onChange={e => e.target.value === "0" ? setIsAskingHelp(false) : setIsAskingHelp(true)}>
                <option value="1">Traženje pomoći</option>
                <option value="0">Pružanje pomoći</option>
              </select>
            </div>
          }
        </div>

        <div className="row">
          <label>Naslov:</label>
        </div>

        <div className="row">
          <input type="text" required className="s-form-control" id="title" placeholder="Unesite naslov"
                 value={title || ''} onChange={(e) => setTitle(e.target.value)}
          />
        </div>

        <div className="row">
          <label>Opis oglasa:</label>
        </div>

        <div className="row">
                    <textarea className="p-3" required id="description" placeholder="Unesite opis"
                              rows="5" cols="50"
                              value={description || ''} onChange={(e) => setDescription(e.target.value)}
                    />
        </div>

        {error && <div className="s-form-group s-error">{error}</div>}

        <div className='row'>

          <div className="col-auto">
            {
                showProgress &&
                <CircularProgress/>
            }
          </div>

          <div className="col-auto">
            <button type="submit" className="s-btn s-btn-primary">Spremi</button>
          </div>
        </div>
      </form>
    </div>
  );
}

export default EditNotice;
