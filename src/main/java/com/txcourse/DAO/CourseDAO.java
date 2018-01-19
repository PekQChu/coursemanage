package com.txcourse.DAO;

import java.util.List;

import com.txcourse.model.Course;

public interface CourseDAO {
	boolean save(Course course);
	boolean delete(Course course);
	boolean deleteById(String id);
	List<Course> findAll();
	Course findCourseById(String id);
}
