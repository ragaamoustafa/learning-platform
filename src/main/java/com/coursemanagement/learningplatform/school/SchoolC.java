package com.coursemanagement.learningplatform.school;

import com.coursemanagement.learningplatform.course.CourseRecommender;
import com.coursemanagement.learningplatform.course.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component ("filterCoursesByStudyField")
@RequiredArgsConstructor
public class SchoolC implements CourseRecommender {

    @Override
    public String recommendCourse(List<Course> courses , long sfId) {
        if (!courses.isEmpty()) {
            Course randomCourse = courses.get((int) (Math.random() * courses.size()));
            return randomCourse.getName(); // Return the random course name
        } else {
            return null;
        }
    }
    }


