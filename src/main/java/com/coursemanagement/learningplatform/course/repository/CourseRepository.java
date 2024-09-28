package com.coursemanagement.learningplatform.course.repository;

import com.coursemanagement.learningplatform.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByStudyFieldId(long sfId); //retrieve courses by study field
}