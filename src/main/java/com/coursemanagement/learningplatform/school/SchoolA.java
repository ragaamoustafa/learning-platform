package com.coursemanagement.learningplatform.school;

import com.coursemanagement.learningplatform.course.CourseRecommender;
import com.coursemanagement.learningplatform.course.entity.Course;
import com.coursemanagement.learningplatform.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
@Primary
@Component
@RequiredArgsConstructor
public class SchoolA implements CourseRecommender {

    private final CourseRepository courseRepository;
    @Override
    public Course recommendCourse(long studyFieldID) {

        List<Course> courses = courseRepository.findAll();
            if (!courses.isEmpty()) {
                int randomIndex = (int) (Math.random() * courses.size());
                return courses.get(randomIndex);
            } else {
                return null;
            }

    }
}
