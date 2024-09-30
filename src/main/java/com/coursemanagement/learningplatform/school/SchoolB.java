package com.coursemanagement.learningplatform.school;

import com.coursemanagement.learningplatform.course.CourseRecommender;
import com.coursemanagement.learningplatform.course.entity.Course;
import com.coursemanagement.learningplatform.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component ("filterCoursesByStudyField")
@RequiredArgsConstructor
public class SchoolB implements CourseRecommender {

    private final CourseRepository courseRepository;
    @Override
    public Course recommendCourse(long sfId) {
        // Retrieve courses by the selected study field
        List<Course> courses = courseRepository.findAllByStudyFieldId(sfId);
        if (!courses.isEmpty()) {
            int randomIndex = (int) (Math.random() * courses.size());
            return courses.get(randomIndex);
        } else {
            return null;
        }
    }
    }


