package com.coursemanagement.learningplatform.course;

import com.coursemanagement.learningplatform.course.entity.Course;

public interface CourseRecommender {
    Course recommendCourse(long sfId);
}
