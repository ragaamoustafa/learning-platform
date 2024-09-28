package com.coursemanagement.learningplatform.course;

import com.coursemanagement.learningplatform.course.entity.Course;

import java.util.List;

public interface CourseRecommender {
    String recommendCourse(List<Course> courses , long sfId);
}
