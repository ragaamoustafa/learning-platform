package com.coursemanagement.learningplatform.course.repository;

import com.coursemanagement.learningplatform.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByStudyFieldId(long sfId); //retrieve courses by study field
}