import {React, useEffect, useState} from 'react';
import noticeCategory from "../util/NoticeCategory";
import {logDOM} from "@testing-library/react";

export default function NoticeFilter(props) {
  const setDisplayNotices = props.setDisplayNotices;

  const [majors, setMajors] = useState(props.majors);
  const [courses, setCourses] = useState(props.courses);
  const [categories, setCategories] = useState(props.categories);

  const [selectedMajorId, setSelectedMajorId] = useState(majors[0].id);
  const [selectedCourseId, setSelectedCourseId] = useState(courses[0].id);
  const [selectedCategory, setSelectedCategory] = useState(categories[0].noticeCategory);


  const handleSubmit = e => {
    e.preventDefault()
    props.applyFilter(selectedMajorId, selectedCourseId, selectedCategory)
  }


  return (
    <form className="s-notice-filter-container" onSubmit={handleSubmit}>
      <div className="s-filter-partition">
        <div className="s-filter-group">
          <label htmlFor="major">Smjer:</label>
          <select className="s-select" name="major" onChange={e => setSelectedMajorId(e.target.value)}>
            {
              majors.map(major => <option key={major.id} value={major.id}>{major.majorName}</option>)
            }
          </select>
        </div>
        <div className="s-filter-group">
          <label htmlFor="course">Kolegij:</label>
          <select className="s-select" name="course" onChange={e => setSelectedCourseId(e.target.value)}>
            {
              courses.map(course => <option key={course.id} value={course.id}>{course.courseName}</option>)
            }
          </select>
        </div>
        <div className="s-filter-group">
          <label htmlFor="category">Kategorija:</label>
          <select className="s-select" name="category" onChange={e => setSelectedCategory(e.target.value)}>
            {
              categories.map((category, index) =>
                <option key={index} value={category.noticeCategory}>{noticeCategory(category.noticeCategory)}</option>)
            }
          </select>
        </div>
        <div className="s-filter-group">
          <button type="submit" className="s-filter-btn s-btn-primary">Primijeni filter</button>
        </div>
        <div className="s-filter-group">
          <label htmlFor="displayNotices">Vrsta oglasa:</label>
          <select className="s-select" name="displayNotices" defaultValue="BOTH"
                  onChange={e => setDisplayNotices(e.target.value)}>
            <option value="BOTH">Svi oglasi</option>
            <option value="SEEKING_HELP">Traženje pomoći</option>
            <option value="PROVIDING_HELP">Pružanje pomoći</option>
          </select>
        </div>
      </div>
    </form>
  )
}
