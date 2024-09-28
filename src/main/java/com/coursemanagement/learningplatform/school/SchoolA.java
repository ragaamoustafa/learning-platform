package com.coursemanagement.learningplatform.school;

import com.coursemanagement.learningplatform.course.CourseRecommender;
import com.coursemanagement.learningplatform.course.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
@Primary
@Component
@RequiredArgsConstructor
public class SchoolA implements CourseRecommender {

    @Override
    public String recommendCourse(List<Course> courses , long sfId) {

            if (!courses.isEmpty()) {
                int randomIndex = (int) (Math.random() * courses.size());
                return courses.get(randomIndex).getName();
            } else {
                return null;
            }

    }
}
