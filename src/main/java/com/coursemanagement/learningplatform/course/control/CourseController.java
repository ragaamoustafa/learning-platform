package com.coursemanagement.learningplatform.course.control;

import com.coursemanagement.learningplatform.course.entity.Course;
import com.coursemanagement.learningplatform.course.entity.CourseRequest;
import com.coursemanagement.learningplatform.studyfield.entity.StudyField;
import com.coursemanagement.learningplatform.studyfield.repository.StudyFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CourseController {
    private final StudyFieldRepository studyFieldRepository;

    public Course mapToCourseEntity(CourseRequest courseRequest) {
        Course course = new Course();
        course.setName(courseRequest.getName());
        course.setDescription(courseRequest.getDescription());
        course.setCredit(courseRequest.getCredit());
        Optional<StudyField> studyFieldOptional = studyFieldRepository.findById(courseRequest.getStudyFieldId());
        if (studyFieldOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid study field id");
        }
        course.setStudyField(studyFieldOptional.get());
        return course;
    }

    public Course mapToCourseEntity(long id, CourseRequest courseRequest) {
        Course course = mapToCourseEntity(courseRequest);
        course.setId(id);
        return course;
    }
}
