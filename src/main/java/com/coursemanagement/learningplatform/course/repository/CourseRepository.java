package com.coursemanagement.learningplatform.course.repository;

import com.coursemanagement.learningplatform.course.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository  {
    List<Course> findAllByStudyFieldId(long sfId); //retrieve courses by study field

    List<Course> findAll();

    Course viewCourse(long courseId);
    void addCourse(Course course);
    void updateCourse(Course course);
    void deleteCourse(long id);
}